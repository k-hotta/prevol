package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.PrintWriter;

public class KNNScriptWriter extends RScriptWriter {
	
	private int k = 1;
	
	public void setK(final int k) {
		this.k = k;
	}
	
	@Override
	public void write(final RScriptWriterSettings settings,
			final CSVData csvData) throws Exception {
		setK(settings.getK());
		super.write(settings, csvData);
	}
	
	protected void writeHead(final PrintWriter pw, final String trainingFile,
			final String evaluationFile, final int attributesCount) {
		pw.println("library(class)");
		pw.println("training_set <- read.csv(\""
				+ convertFileName(trainingFile) + "\")");
		pw.println("for (i in 1:" + attributesCount + ") {");
		pw.println("\ttraining_set <- training_set[,-" + (attributesCount + 1)
				+ "]");
		pw.println("}");
		pw.println();
		pw.println("before_instant_names <- paste(\"B\",1:" + attributesCount
				+ ",sep=\"\")");
		pw.println("instant_names <- c(before_instant_names, \"SMALL_CHANGE\")");
		pw.println("names(training_set) <- instant_names");
		pw.println();
		pw.println("attach(training_set)");
		pw.println();
	}

	@Override
	protected void writeCreateModel(PrintWriter pw, int attributesCount,
			CSVData csvData) {
		// do nothing!!
	}

	@Override
	protected void loadEvaluationSet(final PrintWriter pw,
			final String evaluationFile, final int attributesCount) {
		pw.println("evaluation_set <- read.csv(\""
				+ convertFileName(evaluationFile) + "\")");
		pw.println("evaluation_vectors <- evaluation_set");
		pw.println("for (i in 1:" + attributesCount + ") {");
		pw.println("\tevaluation_vectors <- evaluation_vectors[,-"
				+ (attributesCount + 1) + "]");
		pw.println("}");
		pw.println("names(evaluation_vectors) <- instant_names");
		pw.println();
	}
	
	@Override
	protected void writePredict(final PrintWriter pw, final int attributesCount) {
		pw.println("k <- " + k);
		pw.println("predicted_vectors <- knn(training_set,evaluation_vectors,SMALL_CHANGE,k)");
		pw.println();
	}
	
	@Override
	protected void writeOutput(final PrintWriter pw, final String correctFile,
			final String predictedFile, final String diffFile) {
		pw.println("write.csv(evaluation_vectors, \""
				+ convertFileName(correctFile) + "\")");
		pw.println("write.csv(predicted_vectors, \""
				+ convertFileName(predictedFile) + "\")");
		pw.println("table(predicted_vectors, evaluation_vectors$SMALL_CHANGE)");
		pw.println();
	}
	
}
