package jp.ac.osaka_u.ist.sdl.prevol.data.vectorwriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * CSVWriter の実行時設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorWriterSettings implements DefaultVectorWriterSettingValues {

	/**
	 * CSVWriterのモード
	 */
	private final VectorWriterMode mode;

	/**
	 * 入力データベースファイル
	 */
	private final String dbPath;

	/**
	 * 出力先csvファイル
	 */
	private final String csvPath;

	/**
	 * 開始リビジョン
	 */
	private final long startRevision;

	/**
	 * 終了リビジョン
	 */
	private final long endRevision;

	/**
	 * VectorLink テーブルから要素を抽出するためのクエリ
	 */
	private final String query;

	/**
	 * 無視するノードの種類を保持するリスト (各ノードタイプに割り当てられた整数)
	 */
	private final List<Integer> ignoreList;

	/**
	 * 出力レベル
	 */
	private final MessagePrinterMode printMode;

	/**
	 * デフォルトのクエリかどうか
	 */
	private final boolean defaultQuery;

	private VectorWriterSettings(final VectorWriterMode mode, final String dbPath,
			final String csvPath, final String query, final long startRevision,
			final long endRevision, List<Integer> ignoreList,
			MessagePrinterMode printMode, final boolean defaultQuery) {
		this.mode = mode;
		this.dbPath = dbPath;
		this.csvPath = csvPath;
		this.query = query;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.ignoreList = ignoreList;
		this.printMode = printMode;
		this.defaultQuery = defaultQuery;
	}

	final VectorWriterMode getMode() {
		return mode;
	}

	final String getDbPath() {
		return dbPath;
	}

	final String getCsvPath() {
		return csvPath;
	}

	final String getQuery() {
		return query;
	}

	final long getStartRevision() {
		return startRevision;
	}

	final long getEndRevision() {
		return endRevision;
	}

	final List<Integer> getIgnoreList() {
		return Collections.unmodifiableList(ignoreList);
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	final boolean isDefaultQuery() {
		return defaultQuery;
	}

	public static VectorWriterSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final VectorWriterMode mode = (cmd.hasOption("E")) ? VectorWriterMode.EVALUATION
				: VectorWriterMode.TRAINING;

		final String dbPath = cmd.getOptionValue("d");
		final String csvPath = cmd.getOptionValue("o");

		final long startRevision = (cmd.hasOption("s")) ? Long.parseLong(cmd
				.getOptionValue("s")) : DEFAULT_START_REVISION;
		final long endRevision = (cmd.hasOption("e")) ? Long.parseLong(cmd
				.getOptionValue("e")) : DEFAULT_END_REVISION;

		final String query = (cmd.hasOption("q")) ? cmd.getOptionValue("q")
				: DEFAULT_QUERY;

		MessagePrinterMode printMode = DEFAULT_PRINT_MODE;
		if (cmd.hasOption("v")) {
			final String value = cmd.getOptionValue("v");
			if (value.equalsIgnoreCase("no")) {
				printMode = MessagePrinterMode.NONE;
			} else if (value.equals("yes")) {
				printMode = MessagePrinterMode.LITTLE;
			} else if (value.equals("strong")) {
				printMode = MessagePrinterMode.VERBOSE;
			}
		}

		final List<Integer> ignoreList = new ArrayList<Integer>();
		if (cmd.hasOption("ig")) {
			final String[] ignoreTargets = cmd.getOptionValue("ig").split(",");
			for (final String ignoreTarget : ignoreTargets) {
				ignoreList.add(Integer.parseInt(ignoreTarget));
			}
		} else {
			for (final int defaultIgnoreTarget : DEFAULT_IGNORE_LIST) {
				ignoreList.add(defaultIgnoreTarget);
			}
		}

		final boolean defaultQuery = (!cmd.hasOption("q"));

		return new VectorWriterSettings(mode, dbPath, csvPath, query,
				startRevision, endRevision, ignoreList, printMode, defaultQuery);
	}

	private static Options defineOptions() {
		final Options options = new Options();

		{
			final Option E = new Option("E", "EVALUATION", false,
					"EVALUATION MODE");
			E.setRequired(false);
			options.addOption(E);
		}

		{
			final Option d = new Option("d", "db", true, "database");
			d.setArgs(1);
			d.setRequired(true);
			options.addOption(d);
		}

		{
			final Option o = new Option("o", "output", true, "output file");
			o.setArgs(1);
			o.setRequired(true);
			options.addOption(o);
		}

		{
			final Option q = new Option("q", "query", true,
					"query for selection");
			q.setArgs(1);
			q.setRequired(false);
			options.addOption(q);
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
			final Option ig = new Option("ig", "ignore", true,
					"ignored elements");
			ig.setArgs(1);
			ig.setRequired(false);
			options.addOption(ig);
		}

		return options;
	}

}
