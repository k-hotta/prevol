package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;

/**
 * RevisionData を登録するクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionDataRegisterer extends
		AbstractElementRegisterer<RevisionData> {

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into REVISION values (?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, RevisionData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getRevisionNum());
	}

}
