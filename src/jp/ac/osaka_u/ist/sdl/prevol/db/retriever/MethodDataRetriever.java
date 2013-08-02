package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;

public class MethodDataRetriever extends AbstractElementRetriever<MethodData> {

	@Override
	protected MethodData createElement(ResultSet rs) throws SQLException {
		int column = 0;

		final long id = rs.getLong(++column);
		final long startRevisionId = rs.getLong(++column);
		final long endRevisionId = rs.getLong(++column);
		final long fileId = rs.getLong(++column);
		final String name = rs.getString(++column);
		final int startLine = rs.getInt(++column);
		final int endLine = rs.getInt(++column);
		final String crd = rs.getString(++column);
		final long vectorId = rs.getLong(++column);

		return new MethodData(id, startRevisionId, endRevisionId, fileId, name,
				startLine, endLine, vectorId, crd);
	}

	@Override
	protected String getTableName() {
		return "METHOD";
	}

	protected String getStartRevisionIdColumnName() {
		return "START_REVISION_ID";
	}

	protected String getEndRevisionIdColumnName() {
		return "END_REVIISON_ID";
	}

	/**
	 * 指定されたリビジョンに存在するメソッドをすべて取得
	 * 
	 * @param revId
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<MethodData> retrieveInSpecifiedRevision(final long revId)
			throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getStartRevisionIdColumnName() + " <= " + revId + " AND "
				+ getEndRevisionIdColumnName() + " >=" + revId;

		return retrieve(query);
	}

}
