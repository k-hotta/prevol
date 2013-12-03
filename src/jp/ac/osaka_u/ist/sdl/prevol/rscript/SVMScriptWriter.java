package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.PrintWriter;

public class SVMScriptWriter extends RScriptWriter {

	protected void writeHead(final PrintWriter pw, final String trainingFile,
			final String evaluationFile, final int attributesCount) {
		pw.println("library(kernlab)");
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
		pw.println("svm <- ksvm(SMALL_CHANGE ~., data=training_set, type=\"C-bsvc\")");
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
		pw.println("predicted_vectors <- predict(svm, evaluation_vectors)");
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
