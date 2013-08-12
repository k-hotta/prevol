package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * Evaluator の設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
class EvaluatorSettings {

	/**
	 * 予測結果ファイル (50個くらい)が置かれているディレクトリ
	 */
	private final String predictedResultDir;

	/**
	 * 正解データが保存されているファイル
	 */
	private final String correctDataFilePath;

	/**
	 * 評価結果保存先ファイル
	 */
	private final String outputFilePath;

	/**
	 * 出力レベル
	 */
	private final MessagePrinterMode printMode;

	private EvaluatorSettings(final String predictedResultDir,
			final String correctDataFilePath, final String outputFilePath,
			final MessagePrinterMode printMode) {
		this.predictedResultDir = predictedResultDir;
		this.correctDataFilePath = correctDataFilePath;
		this.outputFilePath = outputFilePath;
		this.printMode = printMode;
	}

	final String getPredictedResultDir() {
		return predictedResultDir;
	}

	final String getCorrectDataFilePath() {
		return correctDataFilePath;
	}

	final String getOutputFilePath() {
		return outputFilePath;
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	static EvaluatorSettings parseArgs(final String[] args) throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String predictedResultDir = cmd.getOptionValue("p");
		final String correctDataFilePath = cmd.getOptionValue("c");
		final String outputFilePath = cmd.getOptionValue("o");

		MessagePrinterMode printMode = MessagePrinterMode.LITTLE;
		if (cmd.hasOption("v")) {
			final String value = cmd.getOptionValue("v");
			if (value.equalsIgnoreCase("no")) {
				printMode = MessagePrinterMode.NONE;
			} else if (value.equals("yes")) {
				printMode = MessagePrinterMode.LITTLE;
			} else {
				printMode = MessagePrinterMode.VERBOSE;
			}
		}

		return new EvaluatorSettings(predictedResultDir, correctDataFilePath,
				outputFilePath, printMode);
	}

	private static Options defineOptions() {
		final Options options = new Options();

		{
			final Option o = new Option("o", "output", true, "output file");
			o.setArgs(1);
			o.setRequired(true);
			options.addOption(o);
		}

		{
			final Option p = new Option("p", "predicted", true,
					"predicted files");
			p.setArgs(1);
			p.setRequired(true);
			options.addOption(p);
		}

		{
			final Option c = new Option("c", "correct", true, "correct data");
			c.setArgs(1);
			c.setRequired(true);
			options.addOption(c);
		}

		{
			final Option v = new Option("v", "verbose", true, "verbose output");
			v.setArgs(1);
			v.setRequired(false);
			options.addOption(v);
		}

		return options;
	}

}
