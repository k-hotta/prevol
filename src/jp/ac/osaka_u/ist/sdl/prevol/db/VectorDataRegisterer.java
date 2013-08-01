package jp.ac.osaka_u.ist.sdl.prevol.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;

/**
 * VectorData を登録するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorDataRegisterer extends AbstractElementRegisterer<VectorData> {

	private final static int numberOfColumns = 84;

	private String preparedStatementQueue = null;

	@Override
	protected String createPreparedStatementQueue() {
		if (preparedStatementQueue == null) {
			final StringBuilder builder = new StringBuilder();
			builder.append("insert into VECTOR values (");
			for (int i = 0; i < numberOfColumns; i++) {
				builder.append("?,");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
			preparedStatementQueue = builder.toString();
		}
		return preparedStatementQueue;
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, VectorData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setInt(++column, element.getAnnotationTypeDeclarationCount());
		pstmt.setInt(++column,
				element.getAnnotationTypeMemberDeclarationCount());
		pstmt.setInt(++column, element.getAnonymousClassDeclarationCount());
		pstmt.setInt(++column, element.getArrayAccessCount());
		pstmt.setInt(++column, element.getArrayCreationCount());
		pstmt.setInt(++column, element.getArrayInitializerCount());
		pstmt.setInt(++column, element.getArrayTypeCount());
		pstmt.setInt(++column, element.getAssertStatementCount());
		pstmt.setInt(++column, element.getAssignmentCount());
		pstmt.setInt(++column, element.getBlockCount());
		pstmt.setInt(++column, element.getBlockCommentCount());
		pstmt.setInt(++column, element.getBooleanLiteralCount());
		pstmt.setInt(++column, element.getBreakStatementCount());
		pstmt.setInt(++column, element.getCastExpressionCount());
		pstmt.setInt(++column, element.getCatchClauseCount());
		pstmt.setInt(++column, element.getCharacterLiteralCount());
		pstmt.setInt(++column, element.getClassInstanceCreationCount());
		pstmt.setInt(++column, element.getCompilationUnitCount());
		pstmt.setInt(++column, element.getConditionalExpressionCount());
		pstmt.setInt(++column, element.getConstructorInvocationCount());
		pstmt.setInt(++column, element.getContinueStatementCount());
		pstmt.setInt(++column, element.getDoStatementCount());
		pstmt.setInt(++column, element.getEmptyStatementCount());
		pstmt.setInt(++column, element.getEnhancedForStatementCount());
		pstmt.setInt(++column, element.getEnumConstantDeclarationCount());
		pstmt.setInt(++column, element.getEnumDeclarationCount());
		pstmt.setInt(++column, element.getExpressionStatementCount());
		pstmt.setInt(++column, element.getFieldAccessCount());
		pstmt.setInt(++column, element.getFieldDeclarationCount());
		pstmt.setInt(++column, element.getForStatementCount());
		pstmt.setInt(++column, element.getIfStatementCount());
		pstmt.setInt(++column, element.getImportDeclarationCount());
		pstmt.setInt(++column, element.getInfixExpressionCount());
		pstmt.setInt(++column, element.getInitializerCount());
		pstmt.setInt(++column, element.getInstanceofExpressionCount());
		pstmt.setInt(++column, element.getJavadocCount());
		pstmt.setInt(++column, element.getLabeledStatementCount());
		pstmt.setInt(++column, element.getLineCommentCount());
		pstmt.setInt(++column, element.getMarkerAnnotationCount());
		pstmt.setInt(++column, element.getMemberRefCount());
		pstmt.setInt(++column, element.getMemberValuePairCount());
		pstmt.setInt(++column, element.getMethodDeclarationCount());
		pstmt.setInt(++column, element.getMethodInvocationCount());
		pstmt.setInt(++column, element.getMethodRefCount());
		pstmt.setInt(++column, element.getMethodRefParameterCount());
		pstmt.setInt(++column, element.getModifierCount());
		pstmt.setInt(++column, element.getNormalAnnotationCount());
		pstmt.setInt(++column, element.getNullLiteralCount());
		pstmt.setInt(++column, element.getNumberLiteralCount());
		pstmt.setInt(++column, element.getPackageDeclarationCount());
		pstmt.setInt(++column, element.getParameterizedTypeCount());
		pstmt.setInt(++column, element.getParenthesizedExpressionCount());
		pstmt.setInt(++column, element.getPostfixExpressionCount());
		pstmt.setInt(++column, element.getPrefixExpressionCount());
		pstmt.setInt(++column, element.getPrimitiveTypeCount());
		pstmt.setInt(++column, element.getQualifiedNameCount());
		pstmt.setInt(++column, element.getQualifiedTypeCount());
		pstmt.setInt(++column, element.getReturnStatementCount());
		pstmt.setInt(++column, element.getSimpleNameCount());
		pstmt.setInt(++column, element.getSimpleTypeCount());
		pstmt.setInt(++column, element.getSingleMemberAnnotationCount());
		pstmt.setInt(++column, element.getSingleVariableDeclarationCount());
		pstmt.setInt(++column, element.getStringLiteralCount());
		pstmt.setInt(++column, element.getSuperConstructorInvocationCount());
		pstmt.setInt(++column, element.getSuperFieldAccessCount());
		pstmt.setInt(++column, element.getSuperMethodInvocationCount());
		pstmt.setInt(++column, element.getSwitchCaseCount());
		pstmt.setInt(++column, element.getSwitchStatementCount());
		pstmt.setInt(++column, element.getSynchronizedStatementCount());
		pstmt.setInt(++column, element.getTagElementCount());
		pstmt.setInt(++column, element.getTextElementCount());
		pstmt.setInt(++column, element.getThisExpressionCount());
		pstmt.setInt(++column, element.getThrowStatementCount());
		pstmt.setInt(++column, element.getTryStatementCount());
		pstmt.setInt(++column, element.getTypeDeclarationCount());
		pstmt.setInt(++column, element.getTypeDeclarationStatementCount());
		pstmt.setInt(++column, element.getTypeLiteralCount());
		pstmt.setInt(++column, element.getTypeParameterCount());
		pstmt.setInt(++column, element.getVariableDeclarationExpressionCount());
		pstmt.setInt(++column, element.getVariableDeclarationFragmentCount());
		pstmt.setInt(++column, element.getVariableDeclarationStatementCount());
		pstmt.setInt(++column, element.getWhileStatementCount());
		pstmt.setInt(++column, element.getWildcardTypeCount());
	}

}
