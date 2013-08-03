package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * VectorPairData を登録するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorPairDataRegisterer extends
		AbstractElementRegisterer<VectorPairData> {

	public VectorPairDataRegisterer(DBConnection connection) {
		super(connection);
	}

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into VECTOR_LINK values (?,?,?,?,?,?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, VectorPairData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getBeforeRevisionId());
		pstmt.setLong(++column, element.getAfterRevisionId());
		pstmt.setLong(++column, element.getBeforeMethodId());
		pstmt.setLong(++column, element.getAfterMethodId());
		pstmt.setLong(++column, element.getBeforeVectorId());
		pstmt.setLong(++column, element.getAfterVectorId());
	}

}
