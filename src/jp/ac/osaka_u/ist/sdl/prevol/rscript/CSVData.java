package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.util.Map;

/**
 * CSVファイルから読み込んできた情報を保持するクラス <br>
 * 全部のデータを保持しているわけではなく，必要なものだけ．
 * 
 * @author k-hotta
 *
 */
public class CSVData {

	private final Map<Integer, Integer> columnValues;
	
	public CSVData(final Map<Integer, Integer> columnValues) {
		this.columnValues = columnValues;
	}
	
	public int getNumberOfColumns() {
		return columnValues.size();
	}
	
	public int getColumnValue(final int column) {
		return columnValues.get(column);
	}
	
}
