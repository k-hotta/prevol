package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;

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

	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getRevIdColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

}
