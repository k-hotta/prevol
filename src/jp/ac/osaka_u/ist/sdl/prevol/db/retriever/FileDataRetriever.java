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

	@Override
	protected String getRevIdColumnName() {
		return "START_REVISION_ID";
	}

	protected String getAfterRevIdColumnName() {
		return "END_REVISION_ID";
	}

	/**
	 * ファイルテーブルは開始リビジョンと終了リビジョンを持つため，単純なリビジョンIDの指定ではダメ
	 */
	@Override
	public SortedSet<FileData> retrieveInSpecifiedRevision(final long revId)
			throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getRevIdColumnName() + " <= " + revId + " AND "
				+ getAfterRevIdColumnName() + " >=" + revId;

		return retrieve(query);
	}

}
