package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * VectorLinker の実行時設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
class VectorLinkerSettings implements DefaultVectorLinkerSettingValues {

	/**
	 * 入出力先データベースファイル
	 */
	private final String dbPath;
	/**
	 * スレッド数
	 */
	private final int threads;

	/**
	 * 開始リビジョン
	 */
	private final long startRevision;

	/**
	 * 終了リビジョン
	 */
	private final long endRevision;

	/**
	 * CRD類似度の下限値
	 */
	private final double similarityThreshold;

	/**
	 * 変化の無いメソッド対を無視するかどうか
	 */
	private final boolean ignoreUnchangedMethodPairs;

	/**
	 * CRDのrefactor-lookup機能を使うかどうか
	 */
	private final boolean useRefactorLookup;

	/**
	 * メッセージ出力のレベル
	 */
	private final MessagePrinterMode printMode;

	private VectorLinkerSettings(final String dbPath, final int threads,
			final long startRevision, final long endRevision,
			final double similarityThreshold,
			final boolean ignoreUnchangedMethodPairs,
			final boolean useRefactorLookup, final MessagePrinterMode printMode) {
		this.dbPath = dbPath;
		this.threads = threads;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.similarityThreshold = similarityThreshold;
		this.ignoreUnchangedMethodPairs = ignoreUnchangedMethodPairs;
		this.useRefactorLookup = useRefactorLookup;
		this.printMode = printMode;
	}

	final String getDbPath() {
		return dbPath;
	}

	final int getThreads() {
		return threads;
	}

	final long getStartRevision() {
		return startRevision;
	}

	final long getEndRevision() {
		return endRevision;
	}

	final double getSimilarityThreshold() {
		return similarityThreshold;
	}

	final boolean isIgnoreUnchangedMethodPairs() {
		return ignoreUnchangedMethodPairs;
	}

	final boolean isUseRefactorLookup() {
		return useRefactorLookup;
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	/**
	 * 引数をパースして設定を読み取る
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static VectorLinkerSettings parseArgs(final String[] args) throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String dbPath = cmd.getOptionValue("d");

		final int threads = (cmd.hasOption("th")) ? Integer.parseInt(cmd
				.getOptionValue("th")) : DEFAULT_THREADS;

		final long startRevision = (cmd.hasOption("s")) ? Long.parseLong(cmd
				.getOptionValue("s")) : DEFAULT_START_REVISION;
		final long endRevision = (cmd.hasOption("e")) ? Long.parseLong(cmd
				.getOptionValue("e")) : DEFAULT_END_REVISION;

		final double similarityThreshold = (cmd.hasOption("b")) ? Double
				.parseDouble(cmd.getOptionValue("b"))
				: DEFAULT_SIMILARITY_THRESHOLD;

		boolean ignoreUnchangedMethodPairs = DEFAULT_IGNORE_UNCHANGED_METHOD_PAIRS;
		if (cmd.hasOption("iu")) {
			if (cmd.getOptionValue("iu").equalsIgnoreCase("no")) {
				ignoreUnchangedMethodPairs = false;
			} else {
				ignoreUnchangedMethodPairs = true;
			}
		}

		boolean useRefactorLookup = DEFALUT_USE_REFACTOR_LOOKUP;
		if (cmd.hasOption("rl")) {
			if (cmd.getOptionValue("rl").equalsIgnoreCase("no")) {
				useRefactorLookup = false;
			} else {
				useRefactorLookup = true;
			}
		}

		MessagePrinterMode mode = DEFAULT_PRINT_MODE;
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

		return new VectorLinkerSettings(dbPath, threads, startRevision,
				endRevision, similarityThreshold, ignoreUnchangedMethodPairs,
				useRefactorLookup, mode);
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
			final Option th = new Option("th", "threads", true,
					"the number of maximum threads");
			th.setArgs(1);
			th.setRequired(false);
			options.addOption(th);
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

		{
			final Option b = new Option("b", "bound", true, "lower limit");
			b.setArgs(1);
			b.setRequired(false);
			options.addOption(b);
		}

		{
			final Option iu = new Option("iu", "ignore", true,
					"ignore unchanged methods");
			iu.setArgs(1);
			iu.setRequired(false);
			options.addOption(iu);
		}

		{
			final Option rl = new Option("rl", "refactor lookup", true,
					"use refactor lookup");
			rl.setArgs(1);
			rl.setRequired(false);
			options.addOption(rl);
		}

		return options;
	}

}
