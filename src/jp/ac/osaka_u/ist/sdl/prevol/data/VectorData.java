package jp.ac.osaka_u.ist.sdl.prevol.data;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * メソッドのベクトルデータを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorData {

	/**
	 * ベクトルデータ <br>
	 * キー：　ASTNodeの各子クラスに割り当てられた整数 <br>
	 * 値：　そのノードの出現数
	 */
	private final Map<Integer, Integer> vector;

	public VectorData(final Map<Integer, Integer> vector) {
		this.vector = new HashMap<Integer, Integer>();
		this.vector.putAll(vector);
	}

	/**
	 * ベクトルデータを一括して取得
	 * 
	 * @return
	 */
	public final Map<Integer, Integer> getVector() {
		return Collections.unmodifiableMap(vector);
	}

	/**
	 * 指定された種類のノードの出現数を取得
	 * 
	 * @param nodeType
	 */
	public final int getSpecifiedNodesCount(final int nodeType) {
		return vector.get(nodeType);
	}

	/**
	 * AnnotationTypeDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getAnnotationTypeDeclarationCount() {
		return vector.get(ANNOTATION_TYPE_DECLARATION);
	}

	/**
	 * AnnotationTypeMemberDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getAnnotationTypeMemberDeclarationCount() {
		return vector.get(ANNOTATION_TYPE_MEMBER_DECLARATION);
	}

	/**
	 * AnonymousClassDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getAnonymousClassDeclarationCount() {
		return vector.get(ANONYMOUS_CLASS_DECLARATION);
	}

	/**
	 * ArrayAccess の数を取得
	 * 
	 * @return
	 */
	public final int getArrayAccessCount() {
		return vector.get(ARRAY_ACCESS);
	}

	/**
	 * ArrayCreation の数を取得
	 * 
	 * @return
	 */
	public final int getArrayCreationCount() {
		return vector.get(ARRAY_CREATION);
	}

	/**
	 * ArrayInitializer の数を取得
	 * 
	 * @return
	 */
	public final int getArrayInitializerCount() {
		return vector.get(ARRAY_INITIALIZER);
	}

	/**
	 * ArrayType の数を取得
	 * 
	 * @return
	 */
	public final int getArrayTypeCount() {
		return vector.get(ARRAY_TYPE);
	}

	/**
	 * AssertStatement の数を取得
	 * 
	 * @return
	 */
	public final int getAssertStatementCount() {
		return vector.get(ASSERT_STATEMENT);
	}

	/**
	 * Assignment の数を取得
	 * 
	 * @return
	 */
	public final int getAssignmentCount() {
		return vector.get(ASSIGNMENT);
	}

	/**
	 * Block の数を取得
	 * 
	 * @return
	 */
	public final int getBlockCount() {
		return vector.get(BLOCK);
	}

	/**
	 * BlockComment の数を取得
	 * 
	 * @return
	 */
	public final int getBlockCommentCount() {
		return vector.get(BLOCK_COMMENT);
	}

	/**
	 * BooleanLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getBooleanLiteralCount() {
		return vector.get(BOOLEAN_LITERAL);
	}

	/**
	 * BreakStatement の数を取得
	 * 
	 * @return
	 */
	public final int getBreakStatementCount() {
		return vector.get(BREAK_STATEMENT);
	}

	/**
	 * CastExpression の数を取得
	 * 
	 * @return
	 */
	public final int getCastExpressionCount() {
		return vector.get(CAST_EXPRESSION);
	}

	/**
	 * CatchClause の数を取得
	 * 
	 * @return
	 */
	public final int getCatchClauseCount() {
		return vector.get(CATCH_CLAUSE);
	}

	/**
	 * CharacterLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getCharacterLiteralCount() {
		return vector.get(CHARACTER_LITERAL);
	}

	/**
	 * ClassInstanceCreation の数を取得
	 * 
	 * @return
	 */
	public final int getClassInstanceCreationCount() {
		return vector.get(CLASS_INSTANCE_CREATION);
	}

	/**
	 * CompilationUnit の数を取得
	 * 
	 * @return
	 */
	public final int getCompilationUnitCount() {
		return vector.get(COMPILATION_UNIT);
	}

	/**
	 * ConditionalExpression の数を取得
	 * 
	 * @return
	 */
	public final int getConditionalExpressionCount() {
		return vector.get(CONDITIONAL_EXPRESSION);
	}

	/**
	 * ConstructorInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getConstructorInvocationCount() {
		return vector.get(CONSTRUCTOR_INVOCATION);
	}

	/**
	 * ContinueStatement の数を取得
	 * 
	 * @return
	 */
	public final int getContinueStatementCount() {
		return vector.get(CONTINUE_STATEMENT);
	}

	/**
	 * DoStatement の数を取得
	 * 
	 * @return
	 */
	public final int getDoStatementCount() {
		return vector.get(DO_STATEMENT);
	}

	/**
	 * EmptyStatement の数を取得
	 * 
	 * @return
	 */
	public final int getEmptyStatementCount() {
		return vector.get(EMPTY_STATEMENT);
	}

	/**
	 * EnhancedForStatement の数を取得
	 * 
	 * @return
	 */
	public final int getEnhancedForStatementCount() {
		return vector.get(ENHANCED_FOR_STATEMENT);
	}

	/**
	 * EnumConstantDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getEnumConstantDeclarationCount() {
		return vector.get(ENUM_CONSTANT_DECLARATION);
	}

	/**
	 * EnumDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getEnumDeclarationCount() {
		return vector.get(ENUM_DECLARATION);
	}

	/**
	 * ExpressionStatement の数を取得
	 * 
	 * @return
	 */
	public final int getExpressionStatementCount() {
		return vector.get(EXPRESSION_STATEMENT);
	}

	/**
	 * FieldAccess の数を取得
	 * 
	 * @return
	 */
	public final int getFieldAccessCount() {
		return vector.get(FIELD_ACCESS);
	}

	/**
	 * FieldDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getFieldDeclarationCount() {
		return vector.get(FIELD_DECLARATION);
	}

	/**
	 * ForStatement の数を取得
	 * 
	 * @return
	 */
	public final int getForStatementCount() {
		return vector.get(FOR_STATEMENT);
	}

	/**
	 * IfStatement の数を取得
	 * 
	 * @return
	 */
	public final int getIfStatementCount() {
		return vector.get(IF_STATEMENT);
	}

	/**
	 * ImportDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getImportDeclarationCount() {
		return vector.get(IMPORT_DECLARATION);
	}

	/**
	 * InfixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getInfixExpressionCount() {
		return vector.get(INFIX_EXPRESSION);
	}

	/**
	 * Initializer の数を取得
	 * 
	 * @return
	 */
	public final int getInitializerCount() {
		return vector.get(INITIALIZER);
	}

	/**
	 * InstanceofExpression の数を取得
	 * 
	 * @return
	 */
	public final int getInstanceofExpressionCount() {
		return vector.get(INSTANCEOF_EXPRESSION);
	}

	/**
	 * Javadoc の数を取得
	 * 
	 * @return
	 */
	public final int getJavadocCount() {
		return vector.get(JAVADOC);
	}

	/**
	 * LabeledStatement の数を取得
	 * 
	 * @return
	 */
	public final int getLabeledStatementCount() {
		return vector.get(LABELED_STATEMENT);
	}

	/**
	 * LineComment の数を取得
	 * 
	 * @return
	 */
	public final int getLineCommentCount() {
		return vector.get(LINE_COMMENT);
	}

	/**
	 * MarkerAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getMarkerAnnotationCount() {
		return vector.get(MARKER_ANNOTATION);
	}

	/**
	 * MemberRef の数を取得
	 * 
	 * @return
	 */
	public final int getMemberRefCount() {
		return vector.get(MEMBER_REF);
	}

	/**
	 * MemberValuePair の数を取得
	 * 
	 * @return
	 */
	public final int getMemberValuePairCount() {
		return vector.get(MEMBER_VALUE_PAIR);
	}

	/**
	 * MethodDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getMethodDeclarationCount() {
		return vector.get(METHOD_DECLARATION);
	}

	/**
	 * MethodInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getMethodInvocationCount() {
		return vector.get(METHOD_INVOCATION);
	}

	/**
	 * MethodRef の数を取得
	 * 
	 * @return
	 */
	public final int getMethodRefCount() {
		return vector.get(METHOD_REF);
	}

	/**
	 * MethodRefParameter の数を取得
	 * 
	 * @return
	 */
	public final int getMethodRefParameterCount() {
		return vector.get(METHOD_REF_PARAMETER);
	}

	/**
	 * Modifier の数を取得
	 * 
	 * @return
	 */
	public final int getModifierCount() {
		return vector.get(MODIFIER);
	}

	/**
	 * NormalAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getNormalAnnotationCount() {
		return vector.get(NORMAL_ANNOTATION);
	}

	/**
	 * NullLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getNullLiteralCount() {
		return vector.get(NULL_LITERAL);
	}

	/**
	 * NumberLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getNumberLiteralCount() {
		return vector.get(NUMBER_LITERAL);
	}

	/**
	 * PackageDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getPackageDeclarationCount() {
		return vector.get(PACKAGE_DECLARATION);
	}

	/**
	 * ParameterizedType の数を取得
	 * 
	 * @return
	 */
	public final int getParameterizedTypeCount() {
		return vector.get(PARAMETERIZED_TYPE);
	}

	/**
	 * ParenthesizedExpression の数を取得
	 * 
	 * @return
	 */
	public final int getParenthesizedExpressionCount() {
		return vector.get(PARENTHESIZED_EXPRESSION);
	}

	/**
	 * PostfixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getPostfixExpressionCount() {
		return vector.get(POSTFIX_EXPRESSION);
	}

	/**
	 * PrefixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getPrefixExpressionCount() {
		return vector.get(PREFIX_EXPRESSION);
	}

	/**
	 * PrimitiveType の数を取得
	 * 
	 * @return
	 */
	public final int getPrimitiveTypeCount() {
		return vector.get(PRIMITIVE_TYPE);
	}

	/**
	 * QualifiedName の数を取得
	 * 
	 * @return
	 */
	public final int getQualifiedNameCount() {
		return vector.get(QUALIFIED_NAME);
	}

	/**
	 * QualifiedType の数を取得
	 * 
	 * @return
	 */
	public final int getQualifiedTypeCount() {
		return vector.get(QUALIFIED_TYPE);
	}

	/**
	 * ReturnStatement の数を取得
	 * 
	 * @return
	 */
	public final int getReturnStatementCount() {
		return vector.get(RETURN_STATEMENT);
	}

	/**
	 * SimpleName の数を取得
	 * 
	 * @return
	 */
	public final int getSimpleNameCount() {
		return vector.get(SIMPLE_NAME);
	}

	/**
	 * SimpleType の数を取得
	 * 
	 * @return
	 */
	public final int getSimpleTypeCount() {
		return vector.get(SIMPLE_TYPE);
	}

	/**
	 * SingleMemberAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getSingleMemberAnnotationCount() {
		return vector.get(SINGLE_MEMBER_ANNOTATION);
	}

	/**
	 * SingleVariableDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getSingleVariableDeclarationCount() {
		return vector.get(SINGLE_VARIABLE_DECLARATION);
	}

	/**
	 * StringLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getStringLiteralCount() {
		return vector.get(STRING_LITERAL);
	}

	/**
	 * SuperConstructorInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getSuperConstructorInvocationCount() {
		return vector.get(SUPER_CONSTRUCTOR_INVOCATION);
	}

	/**
	 * SuperFieldAccess の数を取得
	 * 
	 * @return
	 */
	public final int getSuperFieldAccessCount() {
		return vector.get(SUPER_FIELD_ACCESS);
	}

	/**
	 * SuperMethodInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getSuperMethodInvocationCount() {
		return vector.get(SUPER_METHOD_INVOCATION);
	}

	/**
	 * SwitchCase の数を取得
	 * 
	 * @return
	 */
	public final int getSwitchCaseCount() {
		return vector.get(SWITCH_CASE);
	}

	/**
	 * SwitchStatement の数を取得
	 * 
	 * @return
	 */
	public final int getSwitchStatementCount() {
		return vector.get(SWITCH_STATEMENT);
	}

	/**
	 * SynchronizedStatement の数を取得
	 * 
	 * @return
	 */
	public final int getSynchronizedStatementCount() {
		return vector.get(SYNCHRONIZED_STATEMENT);
	}

	/**
	 * TagElement の数を取得
	 * 
	 * @return
	 */
	public final int getTagElementCount() {
		return vector.get(TAG_ELEMENT);
	}

	/**
	 * TextElement の数を取得
	 * 
	 * @return
	 */
	public final int getTextElementCount() {
		return vector.get(TEXT_ELEMENT);
	}

	/**
	 * ThisExpression の数を取得
	 * 
	 * @return
	 */
	public final int getThisExpressionCount() {
		return vector.get(THIS_EXPRESSION);
	}

	/**
	 * ThrowStatement の数を取得
	 * 
	 * @return
	 */
	public final int getThrowStatementCount() {
		return vector.get(THROW_STATEMENT);
	}

	/**
	 * TryStatement の数を取得
	 * 
	 * @return
	 */
	public final int getTryStatementCount() {
		return vector.get(TRY_STATEMENT);
	}

	/**
	 * TypeDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getTypeDeclarationCount() {
		return vector.get(TYPE_DECLARATION);
	}

	/**
	 * TypeDeclarationStatement の数を取得
	 * 
	 * @return
	 */
	public final int getTypeDeclarationStatementCount() {
		return vector.get(TYPE_DECLARATION_STATEMENT);
	}

	/**
	 * TypeLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getTypeLiteralCount() {
		return vector.get(TYPE_LITERAL);
	}

	/**
	 * TypeParameter の数を取得
	 * 
	 * @return
	 */
	public final int getTypeParameterCount() {
		return vector.get(TYPE_PARAMETER);
	}

	/**
	 * VariableDeclarationExpression の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationExpressionCount() {
		return vector.get(VARIABLE_DECLARATION_EXPRESSION);
	}

	/**
	 * VariableDeclarationFragment の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationFragmentCount() {
		return vector.get(VARIABLE_DECLARATION_FRAGMENT);
	}

	/**
	 * VariableDeclarationStatement の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationStatementCount() {
		return vector.get(VARIABLE_DECLARATION_STATEMENT);
	}

	/**
	 * WhileStatement の数を取得
	 * 
	 * @return
	 */
	public final int getWhileStatementCount() {
		return vector.get(WHILE_STATEMENT);
	}

	/**
	 * WildcardType の数を取得
	 * 
	 * @return
	 */
	public final int getWildcardTypeCount() {
		return vector.get(WILDCARD_TYPE);
	}

}
