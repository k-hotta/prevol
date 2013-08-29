package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.PrintWriter;

public class GAMScriptWriter extends RScriptWriter {

	@Override
	protected void writeMain(PrintWriter pw, RScriptWriterSettings settings,
			int attributesCount, final CSVData csvData) {
		pw.println("library(gam)");
		for (int i = 1; i <= attributesCount; i++) {
			pw.println("seq_a" + i + " <- gam(A" + i + "~1, data=training_set)");
		}
		pw.println();

		final StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= attributesCount; i++) {
			builder.append("\"B" + i + "\"=~1+B" + i);
			if (csvData.getColumnValue(i) >= 4) {
				builder.append("+s(B" + i + ")");
			}
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);

		pw.println("eq.refine <- function(seq) {");
		pw.println("\tif (AIC(seq) == \"-Inf\") {");
		pw.println("\t\treturn(seq)");
		pw.println("\t} else {");
		pw.println("\t\treturn(step.gam(seq,scope=list(" + builder.toString()
				+ ")))");
		pw.println("\t}");
		pw.println("}");
		pw.println();

		for (int i = 1; i <= attributesCount; i++) {
			pw.println("eq_a" + i + " <- eq.refine(seq_a" + i + ")");
		}
		pw.println();
	}

}
