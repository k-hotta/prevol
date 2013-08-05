package jp.ac.osaka_u.ist.sdl.prevol.data.csvwriter;

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
public class CSVWriterSettings implements DefaultCSVWritterSettingValues {

	/**
	 * 入力データベースファイル
	 */
	private final String dbPath;

	/**
	 * 出力先csvファイル
	 */
	private final String csvPath;

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

	private CSVWriterSettings(final String dbPath, final String csvPath,
			final String query, List<Integer> ignoreList,
			MessagePrinterMode printMode) {
		this.dbPath = dbPath;
		this.csvPath = csvPath;
		this.query = query;
		this.ignoreList = ignoreList;
		this.printMode = printMode;
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

	final List<Integer> getIgnoreList() {
		return Collections.unmodifiableList(ignoreList);
	}

	final MessagePrinterMode getPrintMode() {
		return printMode;
	}

	public static CSVWriterSettings parseArgs(final String[] args)
			throws Exception {
		final Options options = defineOptions();

		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);

		final String dbPath = cmd.getOptionValue("d");
		final String csvPath = cmd.getOptionValue("o");

		final String query = (cmd.hasOption("q")) ? cmd.getOptionValue("q")
				: DEFAULT_QUERY;

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

		return new CSVWriterSettings(dbPath, csvPath, query, ignoreList, mode);
	}

	private static Options defineOptions() {
		final Options options = new Options();

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