package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

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
 * VectorWriter の実行時設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorWriterSettings implements DefaultVectorWriterSettingValues {

	/**
	 * VectorWriterのモード
	 */
	private final VectorWriterMode mode;

	/**
	 * 出力ファイル形式
	 */
	private final OutputFileFormat format;

	/**
	 * 入力データベースファイル
	 */
	private final String dbPath;

	/**
	 * 出力先ファイル
	 */
	private final String outputFilePath;

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
	 * ARFFモード時 <br>
	 * 
	 * @RELATION の後ろに続く文字列
	 */
	private final String relationName;

	/**
	 * 出力レベル
	 */
	private final MessagePrinterMode printMode;

	/**
	 * デフォルトのクエリかどうか
	 */
	private final boolean defaultQuery;

	/**
	 * トラッキングモードかどうか
	 */
	private final boolean isTracking;

	/**
	 * 最低修正回数(Trackingするモードのときだけ使用)
	 */
	private final int minimumChangeCount;

	private VectorWriterSettings(final VectorWriterMode mode,
			final OutputFileFormat format, final String dbPath,
			final String outputFilePath, final String query,
			final long startRevision, final long endRevision,
			List<Integer> ignoreList, final String relationName,
			MessagePrinterMode printMode, final boolean defaultQuery,
			final boolean isTracking, final int minimumChangeCount) {
		this.mode = mode;
		this.format = format;
		this.dbPath = dbPath;
		this.outputFilePath = outputFilePath;
		this.query = query;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.ignoreList = ignoreList;
		this.relationName = relationName;
		this.printMode = printMode;
		this.defaultQuery = defaultQuery;
		this.isTracking = isTracking;
		this.minimumChangeCount = minimumChangeCount;
	}

	final VectorWriterMode getMode() {
		return mode;
	}

	final OutputFileFormat getOutputFileFormat() {
		return format;
	}

	final String getDbPath() {
		return dbPath;
	}

	final String getOutputFilePath() {
		return outputFilePath;
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

	final String getRelationName() {
		return relationName;
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	final boolean isDefaultQuery() {
		return defaultQuery;
	}

	final boolean isTracking() {
		return isTracking;
	}

	final int getMinimumChangeCount() {
		return minimumChangeCount;
	}

	public static VectorWriterSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		VectorWriterMode mode = VectorWriterMode.TRAINING;
		if (cmd.hasOption("E")) {
			mode = VectorWriterMode.EVALUATION;
		} else if (cmd.hasOption("S")) {
			mode = VectorWriterMode.SINGLE_COLUMN_TRAINING;
		} else if (cmd.hasOption("SE")) {
			mode = VectorWriterMode.SINGLE_COLUMN_EVALUATION;
		}

		final String dbPath = cmd.getOptionValue("d");
		final String outputFilePath = cmd.getOptionValue("o");

		OutputFileFormat format = null;
		if (outputFilePath.endsWith(".csv")) {
			format = OutputFileFormat.CSV;
		} else if (outputFilePath.endsWith(".arff")) {
			format = OutputFileFormat.ARFF;
		}

		if (format == null) {
			throw new IllegalArgumentException(
					"the output file path must end with \".csv\" or \".arff\"");
		}

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

		final String relationName = (cmd.hasOption("rl")) ? cmd
				.getOptionValue("rl") : null;
		if (format == OutputFileFormat.ARFF && relationName == null) {
			throw new IllegalArgumentException(
					"the name of relation must be specified with \"-rl\"");
		}

		final boolean defaultQuery = (!cmd.hasOption("q"));

		final boolean isTracking = cmd.hasOption("G");

		final int minimumChangeCounnt = (cmd.hasOption("cc")) ? Integer
				.parseInt(cmd.getOptionValue("cc"))
				: DEFAULT_MINIMUM_CHANGE_COUNT;

		return new VectorWriterSettings(mode, format, dbPath, outputFilePath,
				query, startRevision, endRevision, ignoreList, relationName,
				printMode, defaultQuery, isTracking, minimumChangeCounnt);
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
			final Option S = new Option("S", "SINGLE", false,
					"SINGLE COLUMN MODE");
			S.setRequired(false);
			options.addOption(S);
		}

		{
			final Option SE = new Option("SE", "SINGLE_EVALUATION", false,
					"SINGLE COLUMN MODE FOR EVALUATION SET");
			SE.setRequired(false);
			options.addOption(SE);
		}

		{
			final Option G = new Option("G", "GENEALOGY", false,
					"USING GENEALOGIES");
			G.setRequired(false);
			options.addOption(G);
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

		{
			final Option rl = new Option("rl", "relation", true,
					"relation name");
			rl.setArgs(1);
			rl.setRequired(false);
			options.addOption(rl);
		}

		{
			final Option cc = new Option("cc", "changecount", true,
					"the minimum number of change count");
			cc.setArgs(1);
			cc.setRequired(false);
			options.addOption(cc);
		}

		return options;
	}

}
