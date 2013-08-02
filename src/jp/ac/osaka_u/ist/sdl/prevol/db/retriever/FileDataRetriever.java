package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;

/**
 * ファイルを復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class FileDataRetriever extends AbstractElementRetriever<FileData> {

	@Override
	protected FileData createElement(ResultSet rs) throws SQLException {
		int column = 0;

		final long id = rs.getLong(++column);
		final long startRevisionId = rs.getLong(++column);
		final long endRevisionId = rs.getLong(++column);
		final String path = rs.getString(++column);

		return new FileData(id, startRevisionId, endRevisionId, path);
	}

	@Override
	protected String getTableName() {
		return "FILE";
	}

	protected String getStartRevisionIdColumnName() {
		return "START_REVISION_ID";
	}

	protected String getEndRevisionIdColumnName() {
		return "END_REVISION_ID";
	}

	/**
	 * 指定されたリビジョンに存在するファイルをすべて取得
	 * 
	 * @param revId
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<FileData> retrieveInSpecifiedRevision(final long revId)
			throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getStartRevisionIdColumnName() + " <= " + revId + " AND "
				+ getEndRevisionIdColumnName() + " >=" + revId;

		return retrieve(query);
	}

}
