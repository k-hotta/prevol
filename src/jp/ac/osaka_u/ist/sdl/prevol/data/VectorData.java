package jp.ac.osaka_u.ist.sdl.prevol.data;

import static jp.ac.osaka_u.ist.sdl.prevol.utils.Constants.LINE_SEPARATOR;
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * メソッドのベクトルデータを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorData extends AbstractElement {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * ベクトルデータ <br>
	 * キー：　ASTNodeの各子クラスに割り当てられた整数 <br>
	 * 値：　そのノードの出現数
	 */
	private final Map<Integer, Integer> vector;

	public VectorData(final Map<Integer, Integer> vector) {
		super(count.getAndIncrement());
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
		if (vector.containsKey(ANNOTATION_TYPE_DECLARATION)) {
			return vector.get(ANNOTATION_TYPE_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * AnnotationTypeMemberDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getAnnotationTypeMemberDeclarationCount() {
		if (vector.containsKey(ANNOTATION_TYPE_MEMBER_DECLARATION)) {
			return vector.get(ANNOTATION_TYPE_MEMBER_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * AnonymousClassDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getAnonymousClassDeclarationCount() {
		if (vector.containsKey(ANONYMOUS_CLASS_DECLARATION)) {
			return vector.get(ANONYMOUS_CLASS_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * ArrayAccess の数を取得
	 * 
	 * @return
	 */
	public final int getArrayAccessCount() {
		if (vector.containsKey(ARRAY_ACCESS)) {
			return vector.get(ARRAY_ACCESS);
		} else {
			return 0;
		}
	}

	/**
	 * ArrayCreation の数を取得
	 * 
	 * @return
	 */
	public final int getArrayCreationCount() {
		if (vector.containsKey(ARRAY_CREATION)) {
			return vector.get(ARRAY_CREATION);
		} else {
			return 0;
		}
	}

	/**
	 * ArrayInitializer の数を取得
	 * 
	 * @return
	 */
	public final int getArrayInitializerCount() {
		if (vector.containsKey(ARRAY_INITIALIZER)) {
			return vector.get(ARRAY_INITIALIZER);
		} else {
			return 0;
		}
	}

	/**
	 * ArrayType の数を取得
	 * 
	 * @return
	 */
	public final int getArrayTypeCount() {
		if (vector.containsKey(ARRAY_TYPE)) {
			return vector.get(ARRAY_TYPE);
		} else {
			return 0;
		}
	}

	/**
	 * AssertStatement の数を取得
	 * 
	 * @return
	 */
	public final int getAssertStatementCount() {
		if (vector.containsKey(ASSERT_STATEMENT)) {
			return vector.get(ASSERT_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * Assignment の数を取得
	 * 
	 * @return
	 */
	public final int getAssignmentCount() {
		if (vector.containsKey(ASSIGNMENT)) {
			return vector.get(ASSIGNMENT);
		} else {
			return 0;
		}
	}

	/**
	 * Block の数を取得
	 * 
	 * @return
	 */
	public final int getBlockCount() {
		if (vector.containsKey(BLOCK)) {
			return vector.get(BLOCK);
		} else {
			return 0;
		}
	}

	/**
	 * BlockComment の数を取得
	 * 
	 * @return
	 */
	public final int getBlockCommentCount() {
		if (vector.containsKey(BLOCK_COMMENT)) {
			return vector.get(BLOCK_COMMENT);
		} else {
			return 0;
		}
	}

	/**
	 * BooleanLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getBooleanLiteralCount() {
		if (vector.containsKey(BOOLEAN_LITERAL)) {
			return vector.get(BOOLEAN_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * BreakStatement の数を取得
	 * 
	 * @return
	 */
	public final int getBreakStatementCount() {
		if (vector.containsKey(BREAK_STATEMENT)) {
			return vector.get(BREAK_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * CastExpression の数を取得
	 * 
	 * @return
	 */
	public final int getCastExpressionCount() {
		if (vector.containsKey(CAST_EXPRESSION)) {
			return vector.get(CAST_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * CatchClause の数を取得
	 * 
	 * @return
	 */
	public final int getCatchClauseCount() {
		if (vector.containsKey(CATCH_CLAUSE)) {
			return vector.get(CATCH_CLAUSE);
		} else {
			return 0;
		}
	}

	/**
	 * CharacterLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getCharacterLiteralCount() {
		if (vector.containsKey(CHARACTER_LITERAL)) {
			return vector.get(CHARACTER_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * ClassInstanceCreation の数を取得
	 * 
	 * @return
	 */
	public final int getClassInstanceCreationCount() {
		if (vector.containsKey(CLASS_INSTANCE_CREATION)) {
			return vector.get(CLASS_INSTANCE_CREATION);
		} else {
			return 0;
		}
	}

	/**
	 * CompilationUnit の数を取得
	 * 
	 * @return
	 */
	public final int getCompilationUnitCount() {
		if (vector.containsKey(COMPILATION_UNIT)) {
			return vector.get(COMPILATION_UNIT);
		} else {
			return 0;
		}
	}

	/**
	 * ConditionalExpression の数を取得
	 * 
	 * @return
	 */
	public final int getConditionalExpressionCount() {
		if (vector.containsKey(CONDITIONAL_EXPRESSION)) {
			return vector.get(CONDITIONAL_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * ConstructorInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getConstructorInvocationCount() {
		if (vector.containsKey(CONSTRUCTOR_INVOCATION)) {
			return vector.get(CONSTRUCTOR_INVOCATION);
		} else {
			return 0;
		}
	}

	/**
	 * ContinueStatement の数を取得
	 * 
	 * @return
	 */
	public final int getContinueStatementCount() {
		if (vector.containsKey(CONTINUE_STATEMENT)) {
			return vector.get(CONTINUE_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * DoStatement の数を取得
	 * 
	 * @return
	 */
	public final int getDoStatementCount() {
		if (vector.containsKey(DO_STATEMENT)) {
			return vector.get(DO_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * EmptyStatement の数を取得
	 * 
	 * @return
	 */
	public final int getEmptyStatementCount() {
		if (vector.containsKey(EMPTY_STATEMENT)) {
			return vector.get(EMPTY_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * EnhancedForStatement の数を取得
	 * 
	 * @return
	 */
	public final int getEnhancedForStatementCount() {
		if (vector.containsKey(ENHANCED_FOR_STATEMENT)) {
			return vector.get(ENHANCED_FOR_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * EnumConstantDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getEnumConstantDeclarationCount() {
		if (vector.containsKey(ENUM_CONSTANT_DECLARATION)) {
			return vector.get(ENUM_CONSTANT_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * EnumDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getEnumDeclarationCount() {
		if (vector.containsKey(ENUM_DECLARATION)) {
			return vector.get(ENUM_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * ExpressionStatement の数を取得
	 * 
	 * @return
	 */
	public final int getExpressionStatementCount() {
		if (vector.containsKey(EXPRESSION_STATEMENT)) {
			return vector.get(EXPRESSION_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * FieldAccess の数を取得
	 * 
	 * @return
	 */
	public final int getFieldAccessCount() {
		if (vector.containsKey(FIELD_ACCESS)) {
			return vector.get(FIELD_ACCESS);
		} else {
			return 0;
		}
	}

	/**
	 * FieldDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getFieldDeclarationCount() {
		if (vector.containsKey(FIELD_DECLARATION)) {
			return vector.get(FIELD_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * ForStatement の数を取得
	 * 
	 * @return
	 */
	public final int getForStatementCount() {
		if (vector.containsKey(FOR_STATEMENT)) {
			return vector.get(FOR_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * IfStatement の数を取得
	 * 
	 * @return
	 */
	public final int getIfStatementCount() {
		if (vector.containsKey(IF_STATEMENT)) {
			return vector.get(IF_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * ImportDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getImportDeclarationCount() {
		if (vector.containsKey(IMPORT_DECLARATION)) {
			return vector.get(IMPORT_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * InfixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getInfixExpressionCount() {
		if (vector.containsKey(INFIX_EXPRESSION)) {
			return vector.get(INFIX_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * Initializer の数を取得
	 * 
	 * @return
	 */
	public final int getInitializerCount() {
		if (vector.containsKey(INITIALIZER)) {
			return vector.get(INITIALIZER);
		} else {
			return 0;
		}
	}

	/**
	 * InstanceofExpression の数を取得
	 * 
	 * @return
	 */
	public final int getInstanceofExpressionCount() {
		if (vector.containsKey(INSTANCEOF_EXPRESSION)) {
			return vector.get(INSTANCEOF_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * Javadoc の数を取得
	 * 
	 * @return
	 */
	public final int getJavadocCount() {
		if (vector.containsKey(JAVADOC)) {
			return vector.get(JAVADOC);
		} else {
			return 0;
		}
	}

	/**
	 * LabeledStatement の数を取得
	 * 
	 * @return
	 */
	public final int getLabeledStatementCount() {
		if (vector.containsKey(LABELED_STATEMENT)) {
			return vector.get(LABELED_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * LineComment の数を取得
	 * 
	 * @return
	 */
	public final int getLineCommentCount() {
		if (vector.containsKey(LINE_COMMENT)) {
			return vector.get(LINE_COMMENT);
		} else {
			return 0;
		}
	}

	/**
	 * MarkerAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getMarkerAnnotationCount() {
		if (vector.containsKey(MARKER_ANNOTATION)) {
			return vector.get(MARKER_ANNOTATION);
		} else {
			return 0;
		}
	}

	/**
	 * MemberRef の数を取得
	 * 
	 * @return
	 */
	public final int getMemberRefCount() {
		if (vector.containsKey(MEMBER_REF)) {
			return vector.get(MEMBER_REF);
		} else {
			return 0;
		}
	}

	/**
	 * MemberValuePair の数を取得
	 * 
	 * @return
	 */
	public final int getMemberValuePairCount() {
		if (vector.containsKey(MEMBER_VALUE_PAIR)) {
			return vector.get(MEMBER_VALUE_PAIR);
		} else {
			return 0;
		}
	}

	/**
	 * MethodDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getMethodDeclarationCount() {
		if (vector.containsKey(METHOD_DECLARATION)) {
			return vector.get(METHOD_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * MethodInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getMethodInvocationCount() {
		if (vector.containsKey(METHOD_INVOCATION)) {
			return vector.get(METHOD_INVOCATION);
		} else {
			return 0;
		}
	}

	/**
	 * MethodRef の数を取得
	 * 
	 * @return
	 */
	public final int getMethodRefCount() {
		if (vector.containsKey(METHOD_REF)) {
			return vector.get(METHOD_REF);
		} else {
			return 0;
		}
	}

	/**
	 * MethodRefParameter の数を取得
	 * 
	 * @return
	 */
	public final int getMethodRefParameterCount() {
		if (vector.containsKey(METHOD_REF_PARAMETER)) {
			return vector.get(METHOD_REF_PARAMETER);
		} else {
			return 0;
		}
	}

	/**
	 * Modifier の数を取得
	 * 
	 * @return
	 */
	public final int getModifierCount() {
		if (vector.containsKey(MODIFIER)) {
			return vector.get(MODIFIER);
		} else {
			return 0;
		}
	}

	/**
	 * NormalAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getNormalAnnotationCount() {
		if (vector.containsKey(NORMAL_ANNOTATION)) {
			return vector.get(NORMAL_ANNOTATION);
		} else {
			return 0;
		}
	}

	/**
	 * NullLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getNullLiteralCount() {
		if (vector.containsKey(NULL_LITERAL)) {
			return vector.get(NULL_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * NumberLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getNumberLiteralCount() {
		if (vector.containsKey(NUMBER_LITERAL)) {
			return vector.get(NUMBER_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * PackageDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getPackageDeclarationCount() {
		if (vector.containsKey(PACKAGE_DECLARATION)) {
			return vector.get(PACKAGE_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * ParameterizedType の数を取得
	 * 
	 * @return
	 */
	public final int getParameterizedTypeCount() {
		if (vector.containsKey(PARAMETERIZED_TYPE)) {
			return vector.get(PARAMETERIZED_TYPE);
		} else {
			return 0;
		}
	}

	/**
	 * ParenthesizedExpression の数を取得
	 * 
	 * @return
	 */
	public final int getParenthesizedExpressionCount() {
		if (vector.containsKey(PARENTHESIZED_EXPRESSION)) {
			return vector.get(PARENTHESIZED_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * PostfixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getPostfixExpressionCount() {
		if (vector.containsKey(POSTFIX_EXPRESSION)) {
			return vector.get(POSTFIX_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * PrefixExpression の数を取得
	 * 
	 * @return
	 */
	public final int getPrefixExpressionCount() {
		if (vector.containsKey(PREFIX_EXPRESSION)) {
			return vector.get(PREFIX_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * PrimitiveType の数を取得
	 * 
	 * @return
	 */
	public final int getPrimitiveTypeCount() {
		if (vector.containsKey(PRIMITIVE_TYPE)) {
			return vector.get(PRIMITIVE_TYPE);
		} else {
			return 0;
		}
	}

	/**
	 * QualifiedName の数を取得
	 * 
	 * @return
	 */
	public final int getQualifiedNameCount() {
		if (vector.containsKey(QUALIFIED_NAME)) {
			return vector.get(QUALIFIED_NAME);
		} else {
			return 0;
		}
	}

	/**
	 * QualifiedType の数を取得
	 * 
	 * @return
	 */
	public final int getQualifiedTypeCount() {
		if (vector.containsKey(QUALIFIED_TYPE)) {
			return vector.get(QUALIFIED_TYPE);
		} else {
			return 0;
		}
	}

	/**
	 * ReturnStatement の数を取得
	 * 
	 * @return
	 */
	public final int getReturnStatementCount() {
		if (vector.containsKey(RETURN_STATEMENT)) {
			return vector.get(RETURN_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * SimpleName の数を取得
	 * 
	 * @return
	 */
	public final int getSimpleNameCount() {
		if (vector.containsKey(SIMPLE_NAME)) {
			return vector.get(SIMPLE_NAME);
		} else {
			return 0;
		}
	}

	/**
	 * SimpleType の数を取得
	 * 
	 * @return
	 */
	public final int getSimpleTypeCount() {
		if (vector.containsKey(SIMPLE_TYPE)) {
			return vector.get(SIMPLE_TYPE);
		} else {
			return 0;
		}
	}

	/**
	 * SingleMemberAnnotation の数を取得
	 * 
	 * @return
	 */
	public final int getSingleMemberAnnotationCount() {
		if (vector.containsKey(SINGLE_MEMBER_ANNOTATION)) {
			return vector.get(SINGLE_MEMBER_ANNOTATION);
		} else {
			return 0;
		}
	}

	/**
	 * SingleVariableDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getSingleVariableDeclarationCount() {
		if (vector.containsKey(SINGLE_VARIABLE_DECLARATION)) {
			return vector.get(SINGLE_VARIABLE_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * StringLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getStringLiteralCount() {
		if (vector.containsKey(STRING_LITERAL)) {
			return vector.get(STRING_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * SuperConstructorInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getSuperConstructorInvocationCount() {
		if (vector.containsKey(SUPER_CONSTRUCTOR_INVOCATION)) {
			return vector.get(SUPER_CONSTRUCTOR_INVOCATION);
		} else {
			return 0;
		}
	}

	/**
	 * SuperFieldAccess の数を取得
	 * 
	 * @return
	 */
	public final int getSuperFieldAccessCount() {
		if (vector.containsKey(SUPER_FIELD_ACCESS)) {
			return vector.get(SUPER_FIELD_ACCESS);
		} else {
			return 0;
		}
	}

	/**
	 * SuperMethodInvocation の数を取得
	 * 
	 * @return
	 */
	public final int getSuperMethodInvocationCount() {
		if (vector.containsKey(SUPER_METHOD_INVOCATION)) {
			return vector.get(SUPER_METHOD_INVOCATION);
		} else {
			return 0;
		}
	}

	/**
	 * SwitchCase の数を取得
	 * 
	 * @return
	 */
	public final int getSwitchCaseCount() {
		if (vector.containsKey(SWITCH_CASE)) {
			return vector.get(SWITCH_CASE);
		} else {
			return 0;
		}
	}

	/**
	 * SwitchStatement の数を取得
	 * 
	 * @return
	 */
	public final int getSwitchStatementCount() {
		if (vector.containsKey(SWITCH_STATEMENT)) {
			return vector.get(SWITCH_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * SynchronizedStatement の数を取得
	 * 
	 * @return
	 */
	public final int getSynchronizedStatementCount() {
		if (vector.containsKey(SYNCHRONIZED_STATEMENT)) {
			return vector.get(SYNCHRONIZED_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * TagElement の数を取得
	 * 
	 * @return
	 */
	public final int getTagElementCount() {
		if (vector.containsKey(TAG_ELEMENT)) {
			return vector.get(TAG_ELEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * TextElement の数を取得
	 * 
	 * @return
	 */
	public final int getTextElementCount() {
		if (vector.containsKey(TEXT_ELEMENT)) {
			return vector.get(TEXT_ELEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * ThisExpression の数を取得
	 * 
	 * @return
	 */
	public final int getThisExpressionCount() {
		if (vector.containsKey(THIS_EXPRESSION)) {
			return vector.get(THIS_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * ThrowStatement の数を取得
	 * 
	 * @return
	 */
	public final int getThrowStatementCount() {
		if (vector.containsKey(THROW_STATEMENT)) {
			return vector.get(THROW_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * TryStatement の数を取得
	 * 
	 * @return
	 */
	public final int getTryStatementCount() {
		if (vector.containsKey(TRY_STATEMENT)) {
			return vector.get(TRY_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * TypeDeclaration の数を取得
	 * 
	 * @return
	 */
	public final int getTypeDeclarationCount() {
		if (vector.containsKey(TYPE_DECLARATION)) {
			return vector.get(TYPE_DECLARATION);
		} else {
			return 0;
		}
	}

	/**
	 * TypeDeclarationStatement の数を取得
	 * 
	 * @return
	 */
	public final int getTypeDeclarationStatementCount() {
		if (vector.containsKey(TYPE_DECLARATION_STATEMENT)) {
			return vector.get(TYPE_DECLARATION_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * TypeLiteral の数を取得
	 * 
	 * @return
	 */
	public final int getTypeLiteralCount() {
		if (vector.containsKey(TYPE_LITERAL)) {
			return vector.get(TYPE_LITERAL);
		} else {
			return 0;
		}
	}

	/**
	 * TypeParameter の数を取得
	 * 
	 * @return
	 */
	public final int getTypeParameterCount() {
		if (vector.containsKey(TYPE_PARAMETER)) {
			return vector.get(TYPE_PARAMETER);
		} else {
			return 0;
		}
	}

	/**
	 * VariableDeclarationExpression の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationExpressionCount() {
		if (vector.containsKey(VARIABLE_DECLARATION_EXPRESSION)) {
			return vector.get(VARIABLE_DECLARATION_EXPRESSION);
		} else {
			return 0;
		}
	}

	/**
	 * VariableDeclarationFragment の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationFragmentCount() {
		if (vector.containsKey(VARIABLE_DECLARATION_FRAGMENT)) {
			return vector.get(VARIABLE_DECLARATION_FRAGMENT);
		} else {
			return 0;
		}
	}

	/**
	 * VariableDeclarationStatement の数を取得
	 * 
	 * @return
	 */
	public final int getVariableDeclarationStatementCount() {
		if (vector.containsKey(VARIABLE_DECLARATION_STATEMENT)) {
			return vector.get(VARIABLE_DECLARATION_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * WhileStatement の数を取得
	 * 
	 * @return
	 */
	public final int getWhileStatementCount() {
		if (vector.containsKey(WHILE_STATEMENT)) {
			return vector.get(WHILE_STATEMENT);
		} else {
			return 0;
		}
	}

	/**
	 * WildcardType の数を取得
	 * 
	 * @return
	 */
	public final int getWildcardTypeCount() {
		if (vector.containsKey(WILDCARD_TYPE)) {
			return vector.get(WILDCARD_TYPE);
		} else {
			return 0;
		}
	}

	public final String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append("ANNOTATION_TYPE_DECLARATION\t:\t"
				+ this.getAnnotationTypeDeclarationCount() + LINE_SEPARATOR);
		builder.append("ANNOTATION_TYPE_MEMBER_DECLARATION\t:\t"
				+ this.getAnnotationTypeMemberDeclarationCount()
				+ LINE_SEPARATOR);
		builder.append("ANONYMOUS_CLASS_DECLARATION\t:\t"
				+ this.getAnonymousClassDeclarationCount() + LINE_SEPARATOR);
		builder.append("ARRAY_ACCESS\t:\t" + this.getArrayAccessCount()
				+ LINE_SEPARATOR);
		builder.append("ARRAY_CREATION\t:\t" + this.getArrayCreationCount()
				+ LINE_SEPARATOR);
		builder.append("ARRAY_INITIALIZER\t:\t"
				+ this.getArrayInitializerCount() + LINE_SEPARATOR);
		builder.append("ARRAY_TYPE\t:\t" + this.getArrayTypeCount()
				+ LINE_SEPARATOR);
		builder.append("ASSERT_STATEMENT\t:\t" + this.getAssertStatementCount()
				+ LINE_SEPARATOR);
		builder.append("ASSIGNMENT\t:\t" + this.getAssignmentCount()
				+ LINE_SEPARATOR);
		builder.append("BLOCK\t:\t" + this.getBlockCount() + LINE_SEPARATOR);
		builder.append("BLOCK_COMMENT\t:\t" + this.getBlockCommentCount()
				+ LINE_SEPARATOR);
		builder.append("BOOLEAN_LITERAL\t:\t" + this.getBooleanLiteralCount()
				+ LINE_SEPARATOR);
		builder.append("BREAK_STATEMENT\t:\t" + this.getBreakStatementCount()
				+ LINE_SEPARATOR);
		builder.append("CAST_EXPRESSION\t:\t" + this.getCastExpressionCount()
				+ LINE_SEPARATOR);
		builder.append("CATCH_CLAUSE\t:\t" + this.getCatchClauseCount()
				+ LINE_SEPARATOR);
		builder.append("CHARACTER_LITERAL\t:\t"
				+ this.getCharacterLiteralCount() + LINE_SEPARATOR);
		builder.append("CLASS_INSTANCE_CREATION\t:\t"
				+ this.getClassInstanceCreationCount() + LINE_SEPARATOR);
		builder.append("COMPILATION_UNIT\t:\t" + this.getCompilationUnitCount()
				+ LINE_SEPARATOR);
		builder.append("CONDITIONAL_EXPRESSION\t:\t"
				+ this.getConditionalExpressionCount() + LINE_SEPARATOR);
		builder.append("CONSTRUCTOR_INVOCATION\t:\t"
				+ this.getConstructorInvocationCount() + LINE_SEPARATOR);
		builder.append("CONTINUE_STATEMENT\t:\t"
				+ this.getContinueStatementCount() + LINE_SEPARATOR);
		builder.append("DO_STATEMENT\t:\t" + this.getDoStatementCount()
				+ LINE_SEPARATOR);
		builder.append("EMPTY_STATEMENT\t:\t" + this.getEmptyStatementCount()
				+ LINE_SEPARATOR);
		builder.append("ENHANCED_FOR_STATEMENT\t:\t"
				+ this.getEnhancedForStatementCount() + LINE_SEPARATOR);
		builder.append("ENUM_CONSTANT_DECLARATION\t:\t"
				+ this.getEnumConstantDeclarationCount() + LINE_SEPARATOR);
		builder.append("ENUM_DECLARATION\t:\t" + this.getEnumDeclarationCount()
				+ LINE_SEPARATOR);
		builder.append("EXPRESSION_STATEMENT\t:\t"
				+ this.getExpressionStatementCount() + LINE_SEPARATOR);
		builder.append("FIELD_ACCESS\t:\t" + this.getFieldAccessCount()
				+ LINE_SEPARATOR);
		builder.append("FOR_STATEMENT\t:\t" + this.getForStatementCount()
				+ LINE_SEPARATOR);
		builder.append("IF_STATEMENT\t:\t" + this.getIfStatementCount()
				+ LINE_SEPARATOR);
		builder.append("IMPORT_DECLARATION\t:\t"
				+ this.getImportDeclarationCount() + LINE_SEPARATOR);
		builder.append("INFIX_EXPRESSION\t:\t" + this.getInfixExpressionCount()
				+ LINE_SEPARATOR);
		builder.append("INITIALIZER\t:\t" + this.getInitializerCount()
				+ LINE_SEPARATOR);
		builder.append("INSTANCEOF_EXPRESSION\t:\t"
				+ this.getInstanceofExpressionCount() + LINE_SEPARATOR);
		builder.append("JAVADOC\t:\t" + this.getJavadocCount() + LINE_SEPARATOR);
		builder.append("LABELED_STATEMENT\t:\t"
				+ this.getLabeledStatementCount() + LINE_SEPARATOR);
		builder.append("LINE_COMMENT\t:\t" + this.getLineCommentCount()
				+ LINE_SEPARATOR);
		builder.append("MARKER_ANNOTATION\t:\t"
				+ this.getMarkerAnnotationCount() + LINE_SEPARATOR);
		builder.append("MEMBER_REF\t:\t" + this.getMemberRefCount()
				+ LINE_SEPARATOR);
		builder.append("MEMBER_VALUE_PAIR\t:\t"
				+ this.getMemberValuePairCount() + LINE_SEPARATOR);
		builder.append("METHOD_DECLARATION\t:\t"
				+ this.getMethodDeclarationCount() + LINE_SEPARATOR);
		builder.append("METHOD_INVOCATION\t:\t"
				+ this.getMethodInvocationCount() + LINE_SEPARATOR);
		builder.append("METHOD_REF\t:\t" + this.getMethodRefCount()
				+ LINE_SEPARATOR);
		builder.append("METHOD_REF_PARAMETER\t:\t"
				+ this.getMethodRefParameterCount() + LINE_SEPARATOR);
		builder.append("MODIFIER\t:\t" + this.getModifierCount()
				+ LINE_SEPARATOR);
		builder.append("NORMAL_ANNOTATION\t:\t"
				+ this.getNormalAnnotationCount() + LINE_SEPARATOR);
		builder.append("NULL_LITERAL\t:\t" + this.getNullLiteralCount()
				+ LINE_SEPARATOR);
		builder.append("NUMBER_LITERAL\t:\t" + this.getNumberLiteralCount()
				+ LINE_SEPARATOR);
		builder.append("PACKAGE_DECLARATION\t:\t"
				+ this.getPackageDeclarationCount() + LINE_SEPARATOR);
		builder.append("PARAMETERIZED_TYPE\t:\t"
				+ this.getParameterizedTypeCount() + LINE_SEPARATOR);
		builder.append("PARENTHESIZED_EXPRESSION\t:\t"
				+ this.getParenthesizedExpressionCount() + LINE_SEPARATOR);
		builder.append("POSTFIX_EXPRESSION\t:\t"
				+ this.getPostfixExpressionCount() + LINE_SEPARATOR);
		builder.append("PRFIX_EXPRESSION\t:\t"
				+ this.getPrefixExpressionCount() + LINE_SEPARATOR);
		builder.append("PRIMITIVE_TYPE\t:\t" + this.getPrimitiveTypeCount()
				+ LINE_SEPARATOR);
		builder.append("QUALIFIED_NAME\t:\t" + this.getQualifiedNameCount()
				+ LINE_SEPARATOR);
		builder.append("QUALIFIED_TYPE\t:\t" + this.getQualifiedTypeCount()
				+ LINE_SEPARATOR);
		builder.append("RETURN_STATEMENT\t:\t" + this.getReturnStatementCount()
				+ LINE_SEPARATOR);
		builder.append("SIMPLE_NAME\t:\t" + this.getSimpleNameCount()
				+ LINE_SEPARATOR);
		builder.append("SIMPLE_TYPE\t:\t" + this.getSimpleTypeCount()
				+ LINE_SEPARATOR);
		builder.append("SINGLE_MEMBER_ANNOTATION\t:\t"
				+ this.getSingleMemberAnnotationCount() + LINE_SEPARATOR);
		builder.append("SINGLE_VARIABLE_DECLARATION\t:\t"
				+ this.getSingleVariableDeclarationCount() + LINE_SEPARATOR);
		builder.append("STRING_LITERAL\t:\t" + this.getStringLiteralCount()
				+ LINE_SEPARATOR);
		builder.append("SUPER_CONSTRUCTOR_INVOCATION\t:\t"
				+ this.getSuperConstructorInvocationCount() + LINE_SEPARATOR);
		builder.append("SUPER_FIELD_ACCESS\t:\t"
				+ this.getSuperFieldAccessCount() + LINE_SEPARATOR);
		builder.append("SUPER_METHOD_INVOCATION\t:\t"
				+ this.getSuperMethodInvocationCount() + LINE_SEPARATOR);
		builder.append("SWITCH_CASE\t:\t" + this.getSwitchCaseCount()
				+ LINE_SEPARATOR);
		builder.append("SWITCH_STATEMENT\t:\t" + this.getSwitchStatementCount()
				+ LINE_SEPARATOR);
		builder.append("SYNCHRONIZED_STATEMENT\t:\t"
				+ this.getSynchronizedStatementCount() + LINE_SEPARATOR);
		builder.append("TAG_ELEMENT\t:\t" + this.getTagElementCount()
				+ LINE_SEPARATOR);
		builder.append("TEXT_ELEMENT\t:\t" + this.getTextElementCount()
				+ LINE_SEPARATOR);
		builder.append("THIS_EXPRESSION\t:\t" + this.getThisExpressionCount()
				+ LINE_SEPARATOR);
		builder.append("THROW_STATEMENT\t:\t" + this.getThrowStatementCount()
				+ LINE_SEPARATOR);
		builder.append("TRY_STATEMENT\t:\t" + this.getTryStatementCount()
				+ LINE_SEPARATOR);
		builder.append("TYPE_DECLARATION\t:\t" + this.getTypeDeclarationCount()
				+ LINE_SEPARATOR);
		builder.append("TYPE_DECLARATION_STATEMENTA\t:\t"
				+ this.getTypeDeclarationStatementCount() + LINE_SEPARATOR);
		builder.append("TYPE_LITERAL\t:\t" + this.getTypeLiteralCount()
				+ LINE_SEPARATOR);
		builder.append("TYPE_PARAMETER\t:\t" + this.getTypeParameterCount()
				+ LINE_SEPARATOR);
		builder.append("VARIABLE_DECLARATION_EXPRESSION\t:\t"
				+ this.getVariableDeclarationExpressionCount() + LINE_SEPARATOR);
		builder.append("VARIABLE_DECLARATION_FRAGMENT\t:\t"
				+ this.getVariableDeclarationFragmentCount() + LINE_SEPARATOR);
		builder.append("VARIABLE_DECLARATION_STATEMENT\t:\t"
				+ this.getVariableDeclarationStatementCount() + LINE_SEPARATOR);
		builder.append("WHILE_STATEMENT\t:\t" + this.getWhileStatementCount()
				+ LINE_SEPARATOR);
		builder.append("WILDCARD_TYPE\t:\t" + this.getWildcardTypeCount()
				+ LINE_SEPARATOR);

		return builder.toString();
	}

}
