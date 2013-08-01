package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * MethodAnalyzer の実行時設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
class MethodAnalyzerSettings implements DefaultMethodAnalyzerSettingValues {

	/**
	 * リポジトリのパス
	 */
	private final String repositoryPath;

	/**
	 * 出力先データベースファイル
	 */
	private final String dbPath;

	/**
	 * 追加パス <br>
	 * リポジトリの一部だけ対象にしたいときに指定する
	 */
	private final String additionalPath;

	/**
	 * 対象言語
	 */
	private final Language language;

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

	private MethodAnalyzerSettings(final String repositoryPath,
			final String dbPath, final String additionalPath,
			final Language language, final int threads, final String userName,
			final String passwd) {
		this.repositoryPath = repositoryPath;
		this.dbPath = dbPath;
		this.additionalPath = additionalPath;
		this.language = language;
		this.threads = threads;
		this.userName = userName;
		this.passwd = passwd;
	}

	final String getRepositoryPath() {
		return repositoryPath;
	}

	final String getDbPath() {
		return dbPath;
	}

	final String getAdditionalPath() {
		return additionalPath;
	}

	final Language getLanguage() {
		return language;
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

	/**
	 * 引数をパースして設定を読み取る
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static MethodAnalyzerSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String repositoryPath = cmd.getOptionValue("r");

		final String dbPath = cmd.getOptionValue("d");

		final String additionalPath = cmd.hasOption("a") ? cmd
				.getOptionValue("a") : DEFAULT_ADDITIONAL_PATH;

		Language language = null;
		if (cmd.hasOption("l")) {
			final String specifiedLanguageStr = cmd.getOptionValue("l");
			language = Language.getCorrespondingLanguage(specifiedLanguageStr);
			if (language == null) {
				throw new IllegalArgumentException(
						"cannot find the corresponding language that matches "
								+ specifiedLanguageStr);
			}
		} else {
			language = DEFAULT_LANGUAGE;
		}

		final int threads = (cmd.hasOption("th")) ? Integer.parseInt(cmd
				.getOptionValue("th")) : DEFAULT_THREADS;

		final String userName = (cmd.hasOption("u")) ? cmd.getOptionValue("u")
				: DEFAULT_USERNAME;
		final String passwd = (cmd.hasOption("p")) ? cmd.getOptionValue("p")
				: DEFAULT_PASSWD;

		return new MethodAnalyzerSettings(repositoryPath, dbPath,
				additionalPath, language, threads, userName, passwd);

	}

	/**
	 * オプションを定義
	 * 
	 * @return
	 */
	private static Options defineOptions() {
		final Options options = new Options();

		{
			final Option r = new Option("r", "repository", true, "repository");
			r.setArgs(1);
			r.setRequired(true);
			options.addOption(r);
		}

		{
			final Option d = new Option("d", "db", true, "database");
			d.setArgs(1);
			d.setRequired(true);
			options.addOption(d);
		}

		{
			final Option a = new Option("a", "additional", true,
					"additional path");
			a.setArgs(1);
			a.setRequired(false);
			options.addOption(a);
		}

		{
			final Option l = new Option("l", "language", true, "language");
			l.setArgs(1);
			l.setRequired(false);
			options.addOption(l);
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
			p.setRequired(true);
			options.addOption(p);
		}

		return options;
	}
}
