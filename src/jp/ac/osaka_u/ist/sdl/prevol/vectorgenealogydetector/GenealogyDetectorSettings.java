package jp.ac.osaka_u.ist.sdl.prevol.vectorgenealogydetector;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

/**
 * GenealogyDetector の実行時設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
class GenealogyDetectorSettings {

	/**
	 * 入出力先データベースファイル
	 */
	private final String dbPath;

	/**
	 * 開始リビジョン
	 */
	private final long startRevision;

	/**
	 * 終了リビジョン
	 */
	private final long endRevision;

	/**
	 * メッセージ出力のレベル
	 */
	private final MessagePrinterMode printMode;

	private GenealogyDetectorSettings(final String dbPath,
			final long startRevision, final long endRevision,
			final MessagePrinterMode printMode) {
		this.dbPath = dbPath;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.printMode = printMode;
	}

	final String getDbPath() {
		return dbPath;
	}

	final long getStartRevision() {
		return startRevision;
	}

	final long getEndRevision() {
		return endRevision;
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	static GenealogyDetectorSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String dbPath = cmd.getOptionValue("d");

		final long startRevision = (cmd.hasOption("s")) ? Long.parseLong(cmd
				.getOptionValue("s")) : 1;
		final long endRevision = (cmd.hasOption("e")) ? Long.parseLong(cmd
				.getOptionValue("e")) : Long.MAX_VALUE;

		MessagePrinterMode mode = MessagePrinterMode.LITTLE;
		if (cmd.hasOption("v")) {
			final String value = cmd.getOptionValue("v");
			if (value.equalsIgnoreCase("no")) {
				mode = MessagePrinterMode.NONE;
			} else if (value.equals("yes")) {
				mode = MessagePrinterMode.LITTLE;
			} else if (value.equals("strong")) {
				mode = MessagePrinterMode.VERBOSE;
			}
		}

		return new GenealogyDetectorSettings(dbPath, startRevision,
				endRevision, mode);
	}

	/**
	 * オプションを定義
	 * 
	 * @return
	 */
	private static Options defineOptions() {
		final Options options = new Options();

		{
			final Option d = new Option("d", "db", true, "database");
			d.setArgs(1);
			d.setRequired(true);
			options.addOption(d);
		}

		{
			final Option s = new Option("s", "start", true, "start revision");
			s.setArgs(1);
			s.setRequired(false);
			options.addOption(s);
		}

		{
			final Option e = new Option("e", "end", true, "end revision");
			e.setArgs(1);
			e.setRequired(false);
			options.addOption(e);
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
