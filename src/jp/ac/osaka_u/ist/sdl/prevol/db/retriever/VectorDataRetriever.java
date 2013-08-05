package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import static org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_MEMBER_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.ANONYMOUS_CLASS_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.ARRAY_ACCESS;
import static org.eclipse.jdt.core.dom.ASTNode.ARRAY_CREATION;
import static org.eclipse.jdt.core.dom.ASTNode.ARRAY_INITIALIZER;
import static org.eclipse.jdt.core.dom.ASTNode.ARRAY_TYPE;
import static org.eclipse.jdt.core.dom.ASTNode.ASSERT_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ASSIGNMENT;
import static org.eclipse.jdt.core.dom.ASTNode.BLOCK;
import static org.eclipse.jdt.core.dom.ASTNode.BLOCK_COMMENT;
import static org.eclipse.jdt.core.dom.ASTNode.BOOLEAN_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.BREAK_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.CAST_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.CATCH_CLAUSE;
import static org.eclipse.jdt.core.dom.ASTNode.CHARACTER_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.CLASS_INSTANCE_CREATION;
import static org.eclipse.jdt.core.dom.ASTNode.COMPILATION_UNIT;
import static org.eclipse.jdt.core.dom.ASTNode.CONDITIONAL_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.CONSTRUCTOR_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.CONTINUE_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.EMPTY_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ENUM_CONSTANT_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.ENUM_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.EXPRESSION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.FIELD_ACCESS;
import static org.eclipse.jdt.core.dom.ASTNode.FIELD_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.IF_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.IMPORT_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.INFIX_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.INITIALIZER;
import static org.eclipse.jdt.core.dom.ASTNode.INSTANCEOF_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.JAVADOC;
import static org.eclipse.jdt.core.dom.ASTNode.LABELED_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.LINE_COMMENT;
import static org.eclipse.jdt.core.dom.ASTNode.MARKER_ANNOTATION;
import static org.eclipse.jdt.core.dom.ASTNode.MEMBER_REF;
import static org.eclipse.jdt.core.dom.ASTNode.MEMBER_VALUE_PAIR;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_REF;
import static org.eclipse.jdt.core.dom.ASTNode.METHOD_REF_PARAMETER;
import static org.eclipse.jdt.core.dom.ASTNode.MODIFIER;
import static org.eclipse.jdt.core.dom.ASTNode.NORMAL_ANNOTATION;
import static org.eclipse.jdt.core.dom.ASTNode.NULL_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.NUMBER_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.PACKAGE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.PARAMETERIZED_TYPE;
import static org.eclipse.jdt.core.dom.ASTNode.PARENTHESIZED_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.POSTFIX_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.PREFIX_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.PRIMITIVE_TYPE;
import static org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_NAME;
import static org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_TYPE;
import static org.eclipse.jdt.core.dom.ASTNode.RETURN_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.SIMPLE_NAME;
import static org.eclipse.jdt.core.dom.ASTNode.SIMPLE_TYPE;
import static org.eclipse.jdt.core.dom.ASTNode.SINGLE_MEMBER_ANNOTATION;
import static org.eclipse.jdt.core.dom.ASTNode.SINGLE_VARIABLE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.STRING_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.SUPER_CONSTRUCTOR_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.SUPER_FIELD_ACCESS;
import static org.eclipse.jdt.core.dom.ASTNode.SUPER_METHOD_INVOCATION;
import static org.eclipse.jdt.core.dom.ASTNode.SWITCH_CASE;
import static org.eclipse.jdt.core.dom.ASTNode.SWITCH_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.SYNCHRONIZED_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TAG_ELEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TEXT_ELEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.THIS_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.THROW_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TRY_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_LITERAL;
import static org.eclipse.jdt.core.dom.ASTNode.TYPE_PARAMETER;
import static org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_EXPRESSION;
import static org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_FRAGMENT;
import static org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.WILDCARD_TYPE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * ベクトルデータを復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorDataRetriever extends AbstractElementRetriever<VectorData> {

	public VectorDataRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected VectorData createElement(ResultSet rs) throws SQLException {
		int column = 0;
		
		final long id = rs.getLong(++column);
		final int annotationTypeDeclaration = rs.getInt(++column);
		final int annotationTypeMemberDeclaration = rs.getInt(++column);
		final int anonymousClassDeclaration = rs.getInt(++column);
		final int arrayAccess = rs.getInt(++column);
		final int arrayCreation = rs.getInt(++column);
		final int arrayInitializer = rs.getInt(++column);
		final int arrayType = rs.getInt(++column);
		final int assertStatement = rs.getInt(++column);
		final int assignment = rs.getInt(++column);
		final int block = rs.getInt(++column);
		final int blockComment = rs.getInt(++column);
		final int booleanLiteral = rs.getInt(++column);
		final int breakStatement = rs.getInt(++column);
		final int castExpression = rs.getInt(++column);
		final int catchClause = rs.getInt(++column);
		final int characterLiteral = rs.getInt(++column);
		final int classInstanceCreation = rs.getInt(++column);
		final int compilationUnit = rs.getInt(++column);
		final int conditionalExpression = rs.getInt(++column);
		final int constructorInvocation = rs.getInt(++column);
		final int continueStatement = rs.getInt(++column);
		final int doStatement = rs.getInt(++column);
		final int emptyStatement = rs.getInt(++column);
		final int enhancedForStatement = rs.getInt(++column);
		final int enumConstantDeclaration = rs.getInt(++column);
		final int enumDeclaration = rs.getInt(++column);
		final int expressionStatement = rs.getInt(++column);
		final int fieldAccess = rs.getInt(++column);
		final int fieldDeclaration = rs.getInt(++column);
		final int forStatement = rs.getInt(++column);
		final int ifStatement = rs.getInt(++column);
		final int importDeclaration = rs.getInt(++column);
		final int infixExpression = rs.getInt(++column);
		final int initializer = rs.getInt(++column);
		final int instanceofExpression = rs.getInt(++column);
		final int javadoc = rs.getInt(++column);
		final int labeledStatement = rs.getInt(++column);
		final int lineComment = rs.getInt(++column);
		final int markerAnnotation = rs.getInt(++column);
		final int memberRef = rs.getInt(++column);
		final int memberValuePair = rs.getInt(++column);
		final int methodDeclaration = rs.getInt(++column);
		final int methodInvocation = rs.getInt(++column);
		final int methodRef = rs.getInt(++column);
		final int methodRefParameter = rs.getInt(++column);
		final int modifier = rs.getInt(++column);
		final int normalAnnotation = rs.getInt(++column);
		final int nullLiteral = rs.getInt(++column);
		final int numberLiteral = rs.getInt(++column);
		final int packageDeclaration = rs.getInt(++column);
		final int parameterizedType = rs.getInt(++column);
		final int parenthesizedExpression = rs.getInt(++column);
		final int postfixExpression = rs.getInt(++column);
		final int prefixExpression = rs.getInt(++column);
		final int primitiveType = rs.getInt(++column);
		final int qualifiedName = rs.getInt(++column);
		final int qualifiedType = rs.getInt(++column);
		final int returnStatement = rs.getInt(++column);
		final int simpleName = rs.getInt(++column);
		final int simpleType = rs.getInt(++column);
		final int singleMemberAnnotation = rs.getInt(++column);
		final int singleVariableDeclaration = rs.getInt(++column);
		final int stringLiteral = rs.getInt(++column);
		final int superConstructorInvocation = rs.getInt(++column);
		final int superFieldAccess = rs.getInt(++column);
		final int superMethodInvocation = rs.getInt(++column);
		final int switchCase = rs.getInt(++column);
		final int switchStatement = rs.getInt(++column);
		final int synchronizedStatement = rs.getInt(++column);
		final int tagElement = rs.getInt(++column);
		final int textElement = rs.getInt(++column);
		final int thisExpression = rs.getInt(++column);
		final int throwStatement = rs.getInt(++column);
		final int tryStatement = rs.getInt(++column);
		final int typeDeclaration = rs.getInt(++column);
		final int typeDeclarationStatement = rs.getInt(++column);
		final int typeLiteral = rs.getInt(++column);
		final int typeParameter = rs.getInt(++column);
		final int variableDeclarationExpression = rs.getInt(++column);
		final int variableDeclarationFragment = rs.getInt(++column);
		final int variableDeclarationStatement = rs.getInt(++column);
		final int whileStatement = rs.getInt(++column);
		final int wildcardType = rs.getInt(++column);
		
		final Map<Integer, Integer> vector = new HashMap<Integer, Integer>();
		vector.put(ANNOTATION_TYPE_DECLARATION, annotationTypeDeclaration);
		vector.put(ANNOTATION_TYPE_MEMBER_DECLARATION, annotationTypeMemberDeclaration);
		vector.put(ANONYMOUS_CLASS_DECLARATION, anonymousClassDeclaration);
		vector.put(ARRAY_ACCESS, arrayAccess);
		vector.put(ARRAY_CREATION, arrayCreation);
		vector.put(ARRAY_INITIALIZER, arrayInitializer);
		vector.put(ARRAY_TYPE, arrayType);
		vector.put(ASSERT_STATEMENT, assertStatement);
		vector.put(ASSIGNMENT, assignment);
		vector.put(BLOCK, block);
		vector.put(BLOCK_COMMENT, blockComment);
		vector.put(BOOLEAN_LITERAL, booleanLiteral);
		vector.put(BREAK_STATEMENT, breakStatement);
		vector.put(CAST_EXPRESSION, castExpression);
		vector.put(CATCH_CLAUSE, catchClause);
		vector.put(CHARACTER_LITERAL, characterLiteral);
		vector.put(CLASS_INSTANCE_CREATION, classInstanceCreation);
		vector.put(COMPILATION_UNIT, compilationUnit);
		vector.put(CONDITIONAL_EXPRESSION, conditionalExpression);
		vector.put(CONSTRUCTOR_INVOCATION, constructorInvocation);
		vector.put(CONTINUE_STATEMENT, continueStatement);
		vector.put(DO_STATEMENT, doStatement);
		vector.put(EMPTY_STATEMENT, emptyStatement);
		vector.put(ENHANCED_FOR_STATEMENT, enhancedForStatement);
		vector.put(ENUM_CONSTANT_DECLARATION, enumConstantDeclaration);
		vector.put(ENUM_DECLARATION, enumDeclaration);
		vector.put(EXPRESSION_STATEMENT, expressionStatement);
		vector.put(FIELD_ACCESS, fieldAccess);
		vector.put(FIELD_DECLARATION, fieldDeclaration);
		vector.put(FOR_STATEMENT, forStatement);
		vector.put(IF_STATEMENT, ifStatement);
		vector.put(IMPORT_DECLARATION, importDeclaration);
		vector.put(INFIX_EXPRESSION, infixExpression);
		vector.put(INITIALIZER, initializer);
		vector.put(INSTANCEOF_EXPRESSION, instanceofExpression);
		vector.put(JAVADOC, javadoc);
		vector.put(LABELED_STATEMENT, labeledStatement);
		vector.put(LINE_COMMENT, lineComment);
		vector.put(MARKER_ANNOTATION, markerAnnotation);
		vector.put(MEMBER_REF, memberRef);
		vector.put(MEMBER_VALUE_PAIR, memberValuePair);
		vector.put(METHOD_DECLARATION, methodDeclaration);
		vector.put(METHOD_INVOCATION, methodInvocation);
		vector.put(METHOD_REF, methodRef);
		vector.put(METHOD_REF_PARAMETER, methodRefParameter);
		vector.put(MODIFIER, modifier);
		vector.put(NORMAL_ANNOTATION, normalAnnotation);
		vector.put(NULL_LITERAL, nullLiteral);
		vector.put(NUMBER_LITERAL, numberLiteral);
		vector.put(PACKAGE_DECLARATION, packageDeclaration);
		vector.put(PARAMETERIZED_TYPE, parameterizedType);
		vector.put(PARENTHESIZED_EXPRESSION, parenthesizedExpression);
		vector.put(POSTFIX_EXPRESSION, postfixExpression);
		vector.put(PREFIX_EXPRESSION, prefixExpression);
		vector.put(PRIMITIVE_TYPE, primitiveType);
		vector.put(QUALIFIED_NAME, qualifiedName);
		vector.put(QUALIFIED_TYPE, qualifiedType);
		vector.put(RETURN_STATEMENT, returnStatement);
		vector.put(SIMPLE_NAME, simpleName);
		vector.put(SIMPLE_TYPE, simpleType);
		vector.put(SINGLE_MEMBER_ANNOTATION, singleMemberAnnotation);
		vector.put(SINGLE_VARIABLE_DECLARATION, singleVariableDeclaration);
		vector.put(STRING_LITERAL, stringLiteral);
		vector.put(SUPER_CONSTRUCTOR_INVOCATION, superConstructorInvocation);
		vector.put(SUPER_FIELD_ACCESS, superFieldAccess);
		vector.put(SUPER_METHOD_INVOCATION, superMethodInvocation);
		vector.put(SWITCH_CASE, switchCase);
		vector.put(SWITCH_STATEMENT, switchStatement);
		vector.put(SYNCHRONIZED_STATEMENT, synchronizedStatement);
		vector.put(TAG_ELEMENT, tagElement);
		vector.put(TEXT_ELEMENT, textElement);
		vector.put(THIS_EXPRESSION, thisExpression);
		vector.put(THROW_STATEMENT, throwStatement);
		vector.put(TRY_STATEMENT, tryStatement);
		vector.put(TYPE_DECLARATION, typeDeclaration);
		vector.put(TYPE_DECLARATION_STATEMENT, typeDeclarationStatement);
		vector.put(TYPE_LITERAL, typeLiteral);
		vector.put(TYPE_PARAMETER, typeParameter);
		vector.put(VARIABLE_DECLARATION_EXPRESSION, variableDeclarationExpression);
		vector.put(VARIABLE_DECLARATION_FRAGMENT, variableDeclarationFragment);
		vector.put(VARIABLE_DECLARATION_STATEMENT, variableDeclarationStatement);
		vector.put(WHILE_STATEMENT, whileStatement);
		vector.put(WILDCARD_TYPE, wildcardType);
		
		return new VectorData(id, vector);
	}

	@Override
	protected String getTableName() {
		return "VECTOR";
	}

	@Override
	protected String getIdColumnName() {
		return "VECTOR_ID";
	}

}
