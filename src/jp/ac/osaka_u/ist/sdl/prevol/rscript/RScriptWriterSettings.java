package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class RScriptWriterSettings {

	private final String trainingFile;

	private final String evaluationFile;

	private final String correctFile;

	private final String predictedFile;

	private final String diffFile;

	private final String outputFile;

	private final RScriptWriterMode mode;

	private final int maxChange;

	private final int minChange;

	private RScriptWriterSettings(final String trainingFile,
			final String evaluationFile, final String correctFile,
			final String predictedFile, final String diffFile,
			final String outputFile, final RScriptWriterMode mode,
			final int maxChange, final int minChange) {
		this.trainingFile = trainingFile;
		this.evaluationFile = evaluationFile;
		this.correctFile = correctFile;
		this.predictedFile = predictedFile;
		this.diffFile = diffFile;
		this.outputFile = outputFile;
		this.mode = mode;
		this.maxChange = maxChange;
		this.minChange = minChange;
	}

	public String getTrainingFile() {
		return trainingFile;
	}

	public String getEvaluationFile() {
		return evaluationFile;
	}

	public String getCorrectFile() {
		return correctFile;
	}

	public String getPredictedFile() {
		return predictedFile;
	}

	public String getDiffFile() {
		return diffFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public RScriptWriterMode getMode() {
		return mode;
	}

	public int getMaxChange() {
		return maxChange;
	}

	public int getMinChange() {
		return minChange;
	}

	public static RScriptWriterSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String trainingFile = cmd.getOptionValue("t");
		final String evaluationFile = cmd.getOptionValue("e");
		final String correctFile = cmd.getOptionValue("c");
		final String predictedFile = cmd.getOptionValue("p");
		final String diffFile = cmd.getOptionValue("d");
		final String outputFile = cmd.getOptionValue("o");

		RScriptWriterMode mode = RScriptWriterMode.LM;
		if (cmd.hasOption("G")) {
			mode = RScriptWriterMode.GAM;
		} else if (cmd.hasOption("S")) {
			mode = RScriptWriterMode.SVM;
		} else if (cmd.hasOption("R")) {
			mode = RScriptWriterMode.LMP;
		}

		final int maxChange = (cmd.hasOption("max")) ? Integer.parseInt(cmd
				.getOptionValue("max")) : 1;
		final int minChange = (cmd.hasOption("min")) ? Integer.parseInt(cmd
				.getOptionValue("min")) : 1;

		if (minChange > maxChange) {
			throw new IllegalArgumentException(
					"min shouldn't be greater than max");
		}

		return new RScriptWriterSettings(trainingFile, evaluationFile,
				correctFile, predictedFile, diffFile, outputFile, mode,
				maxChange, minChange);
	}

	private static Options defineOptions() {
		final Options options = new Options();

		{
			final Option d = new Option("d", "diff", true, "diff file");
			d.setArgs(1);
			d.setRequired(true);
			options.addOption(d);
		}

		{
			final Option c = new Option("c", "correct", true, "diff file");
			c.setArgs(1);
			c.setRequired(true);
			options.addOption(c);
		}

		{
			final Option p = new Option("p", "predicted", true,
					"predicted file");
			p.setArgs(1);
			p.setRequired(true);
			options.addOption(p);
		}

		{
			final Option t = new Option("t", "training", true, "training file");
			t.setArgs(1);
			t.setRequired(true);
			options.addOption(t);
		}

		{
			final Option e = new Option("e", "evaluation", true,
					"evaluation file");
			e.setArgs(1);
			e.setRequired(true);
			options.addOption(e);
		}

		{
			final Option o = new Option("o", "output", true, "output file");
			o.setArgs(1);
			o.setRequired(true);
			options.addOption(o);
		}

		{
			final Option max = new Option("max", "max", true, "max change");
			max.setArgs(1);
			max.setRequired(false);
			options.addOption(max);
		}

		{
			final Option min = new Option("min", "min", true, "min change");
			min.setArgs(1);
			min.setRequired(false);
			options.addOption(min);
		}

		{
			final Option G = new Option("G", "GAM", false, "USE GAM");
			G.setRequired(false);
			options.addOption(G);
		}
		
		{
			final Option S = new Option("S", "SVM", false, "USE SVM");
			S.setRequired(false);
			options.addOption(S);
		}
		
		{
			final Option R = new Option("R", "LMR", false, "USE LM POISON");
			R.setRequired(false);
			options.addOption(R);
		}

		return options;
	}

}
