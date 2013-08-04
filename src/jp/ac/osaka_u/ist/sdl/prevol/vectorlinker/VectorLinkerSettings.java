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
	 * リポジトリのパス
	 */
	private final String repositoryPath;

	/**
	 * 追加パス <br>
	 * リポジトリの一部だけ対象にしたいときに指定する
	 */
	private final String additionalPath;

	/**
	 * スレッド数
	 */
	private final int threads;

	/**
	 * リポジトリにアクセスするためのユーザ名
	 */
	private final String userName;

	/**
	 * リポジトリにアクセスするためのパスワード
	 */
	private final String passwd;

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
	 * メッセージ出力のレベル
	 */
	private final MessagePrinterMode printMode;

	private VectorLinkerSettings(final String dbPath,
			final String repositoryPath, final String additionalPath,
			final int threads, final long startRevision,
			final long endRevision, final String userName, final String passwd,
			final double similarityThreshold, final MessagePrinterMode printMode) {
		this.dbPath = dbPath;
		this.repositoryPath = repositoryPath;
		this.additionalPath = additionalPath;
		this.threads = threads;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.userName = userName;
		this.passwd = passwd;
		this.similarityThreshold = similarityThreshold;
		this.printMode = printMode;
	}

	final String getDbPath() {
		return dbPath;
	}

	final String getRepositoryPath() {
		return repositoryPath;
	}

	final String getAdditionalPath() {
		return additionalPath;
	}

	final int getThreads() {
		return threads;
	}

	final String getUserName() {
		return userName;
	}

	final String getPasswd() {
		return passwd;
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

		final String repositoryPath = cmd.getOptionValue("r");
		final String additionalPath = cmd.hasOption("a") ? cmd
				.getOptionValue("a") : DEFAULT_ADDITIONAL_PATH;

		final int threads = (cmd.hasOption("th")) ? Integer.parseInt(cmd
				.getOptionValue("th")) : DEFAULT_THREADS;

		final String userName = (cmd.hasOption("u")) ? cmd.getOptionValue("u")
				: DEFAULT_USERNAME;
		final String passwd = (cmd.hasOption("p")) ? cmd.getOptionValue("p")
				: DEFAULT_PASSWD;

		final long startRevision = (cmd.hasOption("s")) ? Long.parseLong(cmd
				.getOptionValue("s")) : DEFAULT_START_REVISION;
		final long endRevision = (cmd.hasOption("e")) ? Long.parseLong(cmd
				.getOptionValue("e")) : DEFAULT_END_REVISION;
		final double similarityThreshold = (cmd.hasOption("b")) ? Double
				.parseDouble(cmd.getOptionValue("b"))
				: DEFAULT_SIMILARITY_THRESHOLD;

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

		return new VectorLinkerSettings(dbPath, repositoryPath, additionalPath,
				threads, startRevision, endRevision, userName, passwd,
				similarityThreshold, mode);
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
			final Option r = new Option("r", "repository", true, "repository");
			r.setArgs(1);
			r.setRequired(true);
			options.addOption(r);
		}

		{
			final Option a = new Option("a", "additional", true,
					"additional path");
			a.setArgs(1);
			a.setRequired(false);
			options.addOption(a);
		}

		{
			final Option th = new Option("th", "threads", true,
					"the number of maximum threads");
			th.setArgs(1);
			th.setRequired(false);
			options.addOption(th);
		}

		{
			final Option u = new Option("u", "user", true, "user name");
			u.setArgs(1);
			u.setRequired(false);
			options.addOption(u);
		}

		{
			final Option p = new Option("p", "password", true, "password");
			p.setArgs(1);
			p.setRequired(false);
			options.addOption(p);
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

		return options;
	}

}
