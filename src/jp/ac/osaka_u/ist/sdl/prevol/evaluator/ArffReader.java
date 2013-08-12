package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * .arff 形式のファイルを読み込み，データを取得するクラス
 * 
 * @author k-hotta
 * 
 */
public class ArffReader {

	/**
	 * 読み込み対象のファイルパス
	 */
	private String filePath;

	/**
	 * ATTRIBUTE を保持するためのマップ
	 */
	private final Map<Integer, String> attributesMap;

	/**
	 * 各行のデータを保持するためのマップ <br>
	 * キーは行番号，値はその行に格納されている値の列 (マップ)
	 */
	private final Map<Integer, Map<Integer, Integer>> rows;

	public ArffReader(final String filePath) {
		this.filePath = filePath;
		this.attributesMap = new TreeMap<Integer, String>();
		this.rows = new TreeMap<Integer, Map<Integer, Integer>>();
	}
	
	public final Map<Integer, String> getAttributes() {
		return Collections.unmodifiableMap(attributesMap);
	}
	
	public final Map<Integer, Map<Integer, Integer>> getRows() {
		return Collections.unmodifiableMap(rows);
	}

	public void load() throws Exception {
		final BufferedReader br = new BufferedReader(new FileReader(new File(
				filePath)));

		ArffReaderMode mode = ArffReaderMode.OTHER;
		int attributeCount = 0;
		int lineCount = 0;

		String line;

		while ((line = br.readLine()) != null) {
			// @ATTRIBUTE ****
			if (line.startsWith("@ATTRIBUTE")) {
				final String[] splitLine = line.split(" ");
				attributesMap.put(attributeCount++, splitLine[1]);
			}

			// @DATA
			else if (line.startsWith("@DATA")) {
				mode = ArffReaderMode.DATA;
			}

			// @DATA 以降の行
			// フォーマット違反している行は無視
			else if (mode == ArffReaderMode.DATA) {
				try {

					final String[] splitLine = line.split(",");
					final Map<Integer, Integer> thisRow = new TreeMap<Integer, Integer>();
					int elementCount = 0;

					for (final String element : splitLine) {
						final int value = Integer.parseInt(element);
						thisRow.put(elementCount++, value);
					}

					rows.put(lineCount++, thisRow);

				} catch (Exception e) {
					// do nothing
				}
			}
		}

		br.close();
	}

	private enum ArffReaderMode {
		DATA, OTHER;
	}

}
