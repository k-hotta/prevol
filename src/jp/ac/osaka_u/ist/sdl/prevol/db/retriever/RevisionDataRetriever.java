package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * リビジョン情報を復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionDataRetriever extends
		AbstractElementRetriever<RevisionData> {

	public RevisionDataRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected RevisionData createElement(ResultSet rs) throws SQLException {
		int column = 0;
		final long id = rs.getLong(++column);
		final long revisionNum = rs.getLong(++column);

		return new RevisionData(id, revisionNum);
	}

	@Override
	protected String getTableName() {
		return "REVISION";
	}

	@Override
	protected String getIdColumnName() {
		return "REVISION_ID";
	}

}
