package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.PrintWriter;

public class LMPoisonScriptWriter extends RScriptWriter {

	@Override
	protected void writeCreateModel(PrintWriter pw, int attributesCount,
			final CSVData csvData) {
		for (int i = 1; i <= attributesCount; i++) {
			pw.println("seq_a" + i + " <- glm(A" + i + "~1, data=training_set, family=poison)");
		}
		pw.println();

		final StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= attributesCount; i++) {
			builder.append("B" + i + "+");
		}
		builder.deleteCharAt(builder.length() - 1);

		pw.println("eq.refine <- function(seq) {");
		pw.println("\tif (AIC(seq) == \"-Inf\") {");
		pw.println("\t\treturn(seq)");
		pw.println("\t} else {");
		pw.println("\t\treturn(step(seq,scope=list(upper=~"
				+ builder.toString() + ", lower=~1)))");
		pw.println("\t}");
		pw.println("}");

		for (int i = 1; i <= attributesCount; i++) {
			pw.println("eq_a" + i + " <- eq.refine(seq_a" + i + ")");
		}
		pw.println();
	}

}
