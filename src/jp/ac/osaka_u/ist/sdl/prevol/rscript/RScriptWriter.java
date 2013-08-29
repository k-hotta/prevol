package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public abstract class RScriptWriter {

	public static void main(String[] args) {
		try {
			final RScriptWriterSettings settings = RScriptWriterSettings
					.parseArgs(args);

			final CSVData csvData = CSVReader.read(settings.getTrainingFile());
			final RScriptWriter writer = settings.getMode().getWriterInstance();

			writer.write(settings, csvData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(final RScriptWriterSettings settings,
			final CSVData csvData) throws Exception {
		final PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(settings.getOutputFile()))));

		final int attributesCount = csvData.getNumberOfColumns() / 2;

		writeHead(pw, settings, attributesCount);
		writeMain(pw, settings, attributesCount, csvData);
		writeTail(pw, settings, attributesCount);

		pw.close();
	}

	protected void writeHead(final PrintWriter pw,
			RScriptWriterSettings settings, final int attributesCount) {
		pw.println("training_set <- read.csv(\""
				+ convertFileName(settings.getTrainingFile()) + "\")");
		pw.println("evaluation_set <- read.csv(\""
				+ convertFileName(settings.getEvaluationFile()) + "\")");
		pw.println();
		pw.println("after_original_names <- names(training_set)");
		pw.println("for (i in 1:" + attributesCount + ") {");
		pw.println("\tafter_original_names <- after_original_names[-1]");
		pw.println("}");
		pw.println();
		pw.println("before_instant_names <- paste(\"B\",1:" + attributesCount
				+ ",sep=\"\")");
		pw.println("after_instant_names <- paste(\"A\",1:" + attributesCount
				+ ",sep=\"\")");
		pw.println("instant_names <- c(before_instant_names, after_instant_names)");
		pw.println("names(training_set) <- instant_names");
		pw.println("names(evaluation_set) <- instant_names");
		pw.println();
		pw.println("correct_vectors <- evaluation_set");
		pw.println("for (i in 1:" + attributesCount + ") {");
		pw.println("\tcorrect_vectors <- correct_vectors[,-1]");
		pw.println("}");
		pw.println("evaluation_vectors <- evaluation_set");
		pw.println("for (i in 1:" + attributesCount + ") {");
		pw.println("\tevaluation_vectors <- evaluation_vectors[,-"
				+ (attributesCount + 1) + "]");
		pw.println("}");
		pw.println();
		pw.println("attach(training_set)");
		pw.println();
	}

	protected abstract void writeMain(final PrintWriter pw,
			RScriptWriterSettings settings, final int attributesCount,
			final CSVData csvData);

	protected void writeTail(final PrintWriter pw,
			RScriptWriterSettings settings, final int attributesCount) {
		pw.println("pred_matrix <- matrix(predict(eq_a1, evaluation_vectors), ncol=1)");
		for (int i = 2; i <= attributesCount; i++) {
			pw.println("pred_matrix <- cbind(pred_matrix, predict(eq_a" + i
					+ ", evaluation_vectors))");
		}
		pw.println();
		pw.println("predicted_vectors <- round(data.frame(pred_matrix))");
		pw.println("names(correct_vectors) <- after_original_names");
		pw.println("write.csv(correct_vectors, \""
				+ convertFileName(settings.getCorrectFile()) + "\")");
		pw.println("names(predicted_vectors) <- after_original_names");
		pw.println("write.csv(predicted_vectors, \""
				+ convertFileName(settings.getPredictedFile()) + "\")");
		pw.println();
		pw.println("diff_vectors <- predicted_vectors - correct_vectors");
		pw.println("write.csv(diff_vectors, \""
				+ convertFileName(settings.getDiffFile()) + "\")");
	}

	protected String convertFileName(final String name) {
		return name.replaceAll("\\\\", "\\\\\\\\");
	}

}
