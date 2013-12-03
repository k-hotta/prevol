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

		writeHead(pw, settings.getTrainingFile(), settings.getEvaluationFile(),
				attributesCount);
		writeCreateModel(pw, attributesCount, csvData);
		writeTail(pw, settings.getEvaluationFile(), settings.getCorrectFile(),
				settings.getPredictedFile(), settings.getDiffFile(),
				attributesCount, settings.getMaxChange(),
				settings.getMinChange());

		pw.close();
	}

	protected void writeHead(final PrintWriter pw, final String trainingFile,
			final String evaluationFile, final int attributesCount) {
		pw.println("training_set <- read.csv(\""
				+ convertFileName(trainingFile) + "\")");
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
		pw.println();
		pw.println("attach(training_set)");
		pw.println();
	}

	protected abstract void writeCreateModel(final PrintWriter pw,
			final int attributesCount, final CSVData csvData);

	protected void writeTail(final PrintWriter pw, final String evaluationFile,
			final String correctFile, final String predictedFile,
			final String diffFile, final int attributesCount,
			final int maxChange, final int minChange) {
		// maxChange が 1 のときは特別扱い
		if (maxChange == 1) {
			// 評価用データのロード
			loadEvaluationSet(pw, evaluationFile, attributesCount);

			writePredict(pw, attributesCount);

			writeOutput(pw, correctFile, predictedFile, diffFile);
		} else {
			for (int i = minChange; i <= maxChange; i++) {
				pw.println("# Change " + i);

				// 繰り返し回数ごとに違うファイルに出力するので，そのファイル名を取得
				final String currentCorrectFile = getOutputFileName(
						correctFile, i);
				final String currentPredictedFile = getOutputFileName(
						predictedFile, i);
				final String currentDiffFile = getOutputFileName(diffFile, i);

				// 対応する評価データを読み込むスクリプトを出力
				final String currentEvaluationFile = getOutputFileName(
						evaluationFile, i);
				loadEvaluationSet(pw, currentEvaluationFile, attributesCount);

				for (int j = 1; j <= i; j++) {
					writePredict(pw, attributesCount);
					pw.println("evaluation_vectors <- predicted_vectors");
					pw.println("names(evaluation_vectors) <- before_instant_names");
					pw.println();
				}

				writeOutput(pw, currentCorrectFile, currentPredictedFile,
						currentDiffFile);
			}
		}
	}

	private void loadEvaluationSet(final PrintWriter pw,
			final String evaluationFile, final int attributesCount) {
		pw.println("evaluation_set <- read.csv(\""
				+ convertFileName(evaluationFile) + "\")");
		pw.println("names(evaluation_set) <- instant_names");
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
	}

	private void writePredict(final PrintWriter pw, final int attributesCount) {
		pw.println("pred_matrix <- matrix(predict(eq_a1, evaluation_vectors), ncol=1)");
		for (int i = 2; i <= attributesCount; i++) {
			pw.println("pred_matrix <- cbind(pred_matrix, predict(eq_a" + i
					+ ", evaluation_vectors))");
		}
		pw.println("predicted_vectors <- round(data.frame(pred_matrix))");
		pw.println();
	}

	private void writeOutput(final PrintWriter pw, final String correctFile,
			final String predictedFile, final String diffFile) {
		pw.println("names(correct_vectors) <- after_original_names");
		pw.println("write.csv(correct_vectors, \""
				+ convertFileName(correctFile) + "\")");
		pw.println("names(predicted_vectors) <- after_original_names");
		pw.println("write.csv(predicted_vectors, \""
				+ convertFileName(predictedFile) + "\")");
		pw.println();
		pw.println("diff_vectors <- predicted_vectors - correct_vectors");
		pw.println("write.csv(diff_vectors, \"" + convertFileName(diffFile)
				+ "\")");
		pw.println();
	}

	protected String convertFileName(final String name) {
		return name.replaceAll("\\\\", "\\\\\\\\");
	}

	private final String getOutputFileName(final String originalPath,
			final int changeCount) {
		final int suffixLength = 4;
		final String exceptSuffix = originalPath.substring(0,
				originalPath.length() - suffixLength);
		return exceptSuffix + "-" + changeCount + ".csv";

	}

}
