package jp.ac.osaka_u.ist.sdl.prevol.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;

/**
 * MethodData を登録するクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodDataRegisterer extends AbstractElementRegisterer<MethodData> {

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into METHOD values (?,?,?,?,?,?,?,?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, MethodData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getStartRevisionId());
		pstmt.setLong(++column, element.getEndRevisionId());
		pstmt.setString(++column, element.getOwnerFile());
		pstmt.setInt(++column, element.getStartLine());
		pstmt.setInt(++column, element.getEndLine());
		pstmt.setString(++column, element.getCrd().toString());
		pstmt.setLong(++column, element.getVectorData().getId());
	}

}
