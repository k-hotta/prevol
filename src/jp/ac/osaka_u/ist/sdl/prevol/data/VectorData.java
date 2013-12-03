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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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

	/**
	 * インスタンス復元用コンストラクタ
	 * 
	 * @param id
	 * @param vector
	 */
	public VectorData(final long id, final Map<Integer, Integer> vector) {
		super(id);
		this.vector = new TreeMap<Integer, Integer>();
		this.vector.putAll(vector);
	}

	/**
	 * インスタンス生成用コンストラクタ
	 * 
	 * @param vector
	 */
	public VectorData(final Map<Integer, Integer> vector) {
		this(count.getAndIncrement(), vector);
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
		builder.append("FIELD_DECLARATION\t:\t"
				+ this.getFieldDeclarationCount() + LINE_SEPARATOR);
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

	/**
	 * このベクトルをCSV形式で出力
	 * 
	 * @param ignoreColumns
	 *            無視するノード (ノードタイプに対して定義された整数で指定)
	 * @return
	 */
	public final String toCsvRecord(final Collection<Integer> ignoreColumns) {
		final StringBuilder builder = new StringBuilder();

		if (!ignoreColumns.contains(ANNOTATION_TYPE_DECLARATION)) {
			builder.append(this.getAnnotationTypeDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(ANNOTATION_TYPE_MEMBER_DECLARATION)) {
			builder.append(this.getAnnotationTypeMemberDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(ANONYMOUS_CLASS_DECLARATION)) {
			builder.append(this.getAnonymousClassDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(ARRAY_ACCESS)) {
			builder.append(this.getArrayAccessCount() + ",");
		}

		if (!ignoreColumns.contains(ARRAY_CREATION)) {
			builder.append(this.getArrayCreationCount() + ",");
		}

		if (!ignoreColumns.contains(ARRAY_INITIALIZER)) {
			builder.append(this.getArrayInitializerCount() + ",");
		}

		if (!ignoreColumns.contains(ARRAY_TYPE)) {
			builder.append(this.getArrayTypeCount() + ",");
		}

		if (!ignoreColumns.contains(ASSERT_STATEMENT)) {
			builder.append(this.getAssertStatementCount() + ",");
		}

		if (!ignoreColumns.contains(ASSIGNMENT)) {
			builder.append(this.getAssignmentCount() + ",");
		}

		if (!ignoreColumns.contains(BLOCK)) {
			builder.append(this.getBlockCount() + ",");
		}

		if (!ignoreColumns.contains(BLOCK_COMMENT)) {
			builder.append(this.getBlockCommentCount() + ",");
		}

		if (!ignoreColumns.contains(BOOLEAN_LITERAL)) {
			builder.append(this.getBooleanLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(BREAK_STATEMENT)) {
			builder.append(this.getBreakStatementCount() + ",");
		}

		if (!ignoreColumns.contains(CAST_EXPRESSION)) {
			builder.append(this.getCastExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(CATCH_CLAUSE)) {
			builder.append(this.getCatchClauseCount() + ",");
		}

		if (!ignoreColumns.contains(CHARACTER_LITERAL)) {
			builder.append(this.getCharacterLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(CLASS_INSTANCE_CREATION)) {
			builder.append(this.getClassInstanceCreationCount() + ",");
		}

		if (!ignoreColumns.contains(COMPILATION_UNIT)) {
			builder.append(this.getCompilationUnitCount() + ",");
		}

		if (!ignoreColumns.contains(CONDITIONAL_EXPRESSION)) {
			builder.append(this.getConditionalExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(CONSTRUCTOR_INVOCATION)) {
			builder.append(this.getConstructorInvocationCount() + ",");
		}

		if (!ignoreColumns.contains(CONTINUE_STATEMENT)) {
			builder.append(this.getContinueStatementCount() + ",");
		}

		if (!ignoreColumns.contains(DO_STATEMENT)) {
			builder.append(this.getDoStatementCount() + ",");
		}

		if (!ignoreColumns.contains(EMPTY_STATEMENT)) {
			builder.append(this.getEmptyStatementCount() + ",");
		}

		if (!ignoreColumns.contains(ENHANCED_FOR_STATEMENT)) {
			builder.append(this.getEnhancedForStatementCount() + ",");
		}

		if (!ignoreColumns.contains(ENUM_CONSTANT_DECLARATION)) {
			builder.append(this.getEnumConstantDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(ENUM_DECLARATION)) {
			builder.append(this.getEnumDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(EXPRESSION_STATEMENT)) {
			builder.append(this.getExpressionStatementCount() + ",");
		}

		if (!ignoreColumns.contains(FIELD_ACCESS)) {
			builder.append(this.getFieldAccessCount() + ",");
		}

		if (!ignoreColumns.contains(FIELD_DECLARATION)) {
			builder.append(this.getFieldDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(FOR_STATEMENT)) {
			builder.append(this.getForStatementCount() + ",");
		}

		if (!ignoreColumns.contains(IF_STATEMENT)) {
			builder.append(this.getIfStatementCount() + ",");
		}

		if (!ignoreColumns.contains(IMPORT_DECLARATION)) {
			builder.append(this.getImportDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(INFIX_EXPRESSION)) {
			builder.append(this.getInfixExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(INITIALIZER)) {
			builder.append(this.getInitializerCount() + ",");
		}

		if (!ignoreColumns.contains(INSTANCEOF_EXPRESSION)) {
			builder.append(this.getInstanceofExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(JAVADOC)) {
			builder.append(this.getJavadocCount() + ",");
		}

		if (!ignoreColumns.contains(LABELED_STATEMENT)) {
			builder.append(this.getLabeledStatementCount() + ",");
		}

		if (!ignoreColumns.contains(LINE_COMMENT)) {
			builder.append(this.getLineCommentCount() + ",");
		}

		if (!ignoreColumns.contains(MARKER_ANNOTATION)) {
			builder.append(this.getMarkerAnnotationCount() + ",");
		}

		if (!ignoreColumns.contains(MEMBER_REF)) {
			builder.append(this.getMemberRefCount() + ",");
		}

		if (!ignoreColumns.contains(MEMBER_VALUE_PAIR)) {
			builder.append(this.getMemberValuePairCount() + ",");
		}

		if (!ignoreColumns.contains(METHOD_DECLARATION)) {
			builder.append(this.getMethodDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(METHOD_INVOCATION)) {
			builder.append(this.getMethodInvocationCount() + ",");
		}

		if (!ignoreColumns.contains(METHOD_REF)) {
			builder.append(this.getMethodRefCount() + ",");
		}

		if (!ignoreColumns.contains(METHOD_REF_PARAMETER)) {
			builder.append(this.getMethodRefParameterCount() + ",");
		}

		if (!ignoreColumns.contains(MODIFIER)) {
			builder.append(this.getModifierCount() + ",");
		}

		if (!ignoreColumns.contains(NORMAL_ANNOTATION)) {
			builder.append(this.getNormalAnnotationCount() + ",");
		}

		if (!ignoreColumns.contains(NULL_LITERAL)) {
			builder.append(this.getNullLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(NUMBER_LITERAL)) {
			builder.append(this.getNumberLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(PACKAGE_DECLARATION)) {
			builder.append(this.getPackageDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(PARAMETERIZED_TYPE)) {
			builder.append(this.getParameterizedTypeCount() + ",");
		}

		if (!ignoreColumns.contains(PARENTHESIZED_EXPRESSION)) {
			builder.append(this.getParenthesizedExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(POSTFIX_EXPRESSION)) {
			builder.append(this.getPostfixExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(PREFIX_EXPRESSION)) {
			builder.append(this.getPrefixExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(PRIMITIVE_TYPE)) {
			builder.append(this.getPrimitiveTypeCount() + ",");
		}

		if (!ignoreColumns.contains(QUALIFIED_NAME)) {
			builder.append(this.getQualifiedNameCount() + ",");
		}

		if (!ignoreColumns.contains(QUALIFIED_TYPE)) {
			builder.append(this.getQualifiedTypeCount() + ",");
		}

		if (!ignoreColumns.contains(RETURN_STATEMENT)) {
			builder.append(this.getReturnStatementCount() + ",");
		}

		if (!ignoreColumns.contains(SIMPLE_NAME)) {
			builder.append(this.getSimpleNameCount() + ",");
		}

		if (!ignoreColumns.contains(SIMPLE_TYPE)) {
			builder.append(this.getSimpleTypeCount() + ",");
		}

		if (!ignoreColumns.contains(SINGLE_MEMBER_ANNOTATION)) {
			builder.append(this.getSingleMemberAnnotationCount() + ",");
		}

		if (!ignoreColumns.contains(SINGLE_VARIABLE_DECLARATION)) {
			builder.append(this.getSingleVariableDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(STRING_LITERAL)) {
			builder.append(this.getStringLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(SUPER_CONSTRUCTOR_INVOCATION)) {
			builder.append(this.getSuperConstructorInvocationCount() + ",");
		}

		if (!ignoreColumns.contains(SUPER_FIELD_ACCESS)) {
			builder.append(this.getSuperFieldAccessCount() + ",");
		}

		if (!ignoreColumns.contains(SUPER_METHOD_INVOCATION)) {
			builder.append(this.getSuperMethodInvocationCount() + ",");
		}

		if (!ignoreColumns.contains(SWITCH_CASE)) {
			builder.append(this.getSwitchCaseCount() + ",");
		}

		if (!ignoreColumns.contains(SWITCH_STATEMENT)) {
			builder.append(this.getSwitchStatementCount() + ",");
		}

		if (!ignoreColumns.contains(SYNCHRONIZED_STATEMENT)) {
			builder.append(this.getSynchronizedStatementCount() + ",");
		}

		if (!ignoreColumns.contains(TAG_ELEMENT)) {
			builder.append(this.getTagElementCount() + ",");
		}

		if (!ignoreColumns.contains(TEXT_ELEMENT)) {
			builder.append(this.getTextElementCount() + ",");
		}

		if (!ignoreColumns.contains(THIS_EXPRESSION)) {
			builder.append(this.getThisExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(THROW_STATEMENT)) {
			builder.append(this.getThrowStatementCount() + ",");
		}

		if (!ignoreColumns.contains(TRY_STATEMENT)) {
			builder.append(this.getTryStatementCount() + ",");
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION)) {
			builder.append(this.getTypeDeclarationCount() + ",");
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION_STATEMENT)) {
			builder.append(this.getTypeDeclarationStatementCount() + ",");
		}

		if (!ignoreColumns.contains(TYPE_LITERAL)) {
			builder.append(this.getTypeLiteralCount() + ",");
		}

		if (!ignoreColumns.contains(TYPE_PARAMETER)) {
			builder.append(this.getTypeParameterCount() + ",");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_EXPRESSION)) {
			builder.append(this.getVariableDeclarationExpressionCount() + ",");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_FRAGMENT)) {
			builder.append(this.getVariableDeclarationFragmentCount() + ",");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_STATEMENT)) {
			builder.append(this.getVariableDeclarationStatementCount() + ",");
		}

		if (!ignoreColumns.contains(WHILE_STATEMENT)) {
			builder.append(this.getWhileStatementCount() + ",");
		}

		if (!ignoreColumns.contains(WILDCARD_TYPE)) {
			builder.append(this.getWildcardTypeCount() + ",");
		}

		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}

	public String toCsvRecord() {
		return this.toCsvRecord(new HashSet<Integer>());
	}

	public static String getEvaluationCsvHeader(
			final Collection<Integer> ignoreColumns) {
		return getCsvHeader(ignoreColumns, "BEFORE_");
	}

	public static String getTrainingCsvHeader(
			final Collection<Integer> ignoreColumns) {
		return getCsvHeader(ignoreColumns, "BEFORE_") + ","
				+ getCsvHeader(ignoreColumns, "AFTER_");
	}

	public static String getSingleColumnTrainingCsvHeader(
			final Collection<Integer> ignoreColumns, String afterValueName) {
		return getCsvHeader(ignoreColumns, "BEFORE_") + "," + afterValueName;
	}

	private static String getCsvHeader(final Collection<Integer> ignoreColumns,
			final String prefix) {
		final StringBuilder builder = new StringBuilder();

		if (!ignoreColumns.contains(ANNOTATION_TYPE_DECLARATION)) {
			builder.append(prefix + "ANNOTATION_TYPE_DECLARATION,");
		}

		if (!ignoreColumns.contains(ANNOTATION_TYPE_MEMBER_DECLARATION)) {
			builder.append(prefix + "ANNOTATION_TYPE_MEMBER_DECLARATION,");
		}

		if (!ignoreColumns.contains(ANONYMOUS_CLASS_DECLARATION)) {
			builder.append(prefix + "ANONYMOUS_CLASS_DECLARATION,");
		}

		if (!ignoreColumns.contains(ARRAY_ACCESS)) {
			builder.append(prefix + "ARRAY_ACCESS,");
		}

		if (!ignoreColumns.contains(ARRAY_CREATION)) {
			builder.append(prefix + "ARRAY_CREATION,");
		}

		if (!ignoreColumns.contains(ARRAY_INITIALIZER)) {
			builder.append(prefix + "ARRAY_INITIALIZER,");
		}

		if (!ignoreColumns.contains(ARRAY_TYPE)) {
			builder.append(prefix + "ARRAY_TYPE,");
		}

		if (!ignoreColumns.contains(ASSERT_STATEMENT)) {
			builder.append(prefix + "ASSERT_STATEMENT,");
		}

		if (!ignoreColumns.contains(ASSIGNMENT)) {
			builder.append(prefix + "ASSIGNMENT,");
		}

		if (!ignoreColumns.contains(BLOCK)) {
			builder.append(prefix + "BLOCK,");
		}

		if (!ignoreColumns.contains(BLOCK_COMMENT)) {
			builder.append(prefix + "BLOCK_COMMENT,");
		}

		if (!ignoreColumns.contains(BOOLEAN_LITERAL)) {
			builder.append(prefix + "BOOLEAN_LITERAL,");
		}

		if (!ignoreColumns.contains(BREAK_STATEMENT)) {
			builder.append(prefix + "BREAK_STATEMENT,");
		}

		if (!ignoreColumns.contains(CAST_EXPRESSION)) {
			builder.append(prefix + "CAST_EXPRESSION,");
		}

		if (!ignoreColumns.contains(CATCH_CLAUSE)) {
			builder.append(prefix + "CATCH_CLAUSE,");
		}

		if (!ignoreColumns.contains(CHARACTER_LITERAL)) {
			builder.append(prefix + "CHARACTER_LITERAL,");
		}

		if (!ignoreColumns.contains(CLASS_INSTANCE_CREATION)) {
			builder.append(prefix + "CLASS_INSTANCE_CREATION,");
		}

		if (!ignoreColumns.contains(COMPILATION_UNIT)) {
			builder.append(prefix + "COMPILATION_UNIT,");
		}

		if (!ignoreColumns.contains(CONDITIONAL_EXPRESSION)) {
			builder.append(prefix + "CONDITIONAL_EXPRESSION,");
		}

		if (!ignoreColumns.contains(CONSTRUCTOR_INVOCATION)) {
			builder.append(prefix + "CONSTRUCTOR_INVOCATION,");
		}

		if (!ignoreColumns.contains(CONTINUE_STATEMENT)) {
			builder.append(prefix + "CONTINUE_STATEMENT,");
		}

		if (!ignoreColumns.contains(DO_STATEMENT)) {
			builder.append(prefix + "DO_STATEMENT,");
		}

		if (!ignoreColumns.contains(EMPTY_STATEMENT)) {
			builder.append(prefix + "EMPTY_STATEMENT,");
		}

		if (!ignoreColumns.contains(ENHANCED_FOR_STATEMENT)) {
			builder.append(prefix + "ENHANCED_FOR_STATEMENT,");
		}

		if (!ignoreColumns.contains(ENUM_CONSTANT_DECLARATION)) {
			builder.append(prefix + "ENUM_CONSTANT_DECLARATION,");
		}

		if (!ignoreColumns.contains(ENUM_DECLARATION)) {
			builder.append(prefix + "ENUM_DECLARATION,");
		}

		if (!ignoreColumns.contains(EXPRESSION_STATEMENT)) {
			builder.append(prefix + "EXPRESSION_STATEMENT,");
		}

		if (!ignoreColumns.contains(FIELD_ACCESS)) {
			builder.append(prefix + "FIELD_ACCESS,");
		}

		if (!ignoreColumns.contains(FIELD_DECLARATION)) {
			builder.append(prefix + "FIELD_DECLARATION,");
		}

		if (!ignoreColumns.contains(FOR_STATEMENT)) {
			builder.append(prefix + "FOR_STATEMENT,");
		}

		if (!ignoreColumns.contains(IF_STATEMENT)) {
			builder.append(prefix + "IF_STATEMENT,");
		}

		if (!ignoreColumns.contains(IMPORT_DECLARATION)) {
			builder.append(prefix + "IMPORT_DECLARATION,");
		}

		if (!ignoreColumns.contains(INFIX_EXPRESSION)) {
			builder.append(prefix + "INFIX_EXPRESSION,");
		}

		if (!ignoreColumns.contains(INITIALIZER)) {
			builder.append(prefix + "INITIALIZER,");
		}

		if (!ignoreColumns.contains(INSTANCEOF_EXPRESSION)) {
			builder.append(prefix + "INSTANCEOF_EXPRESSION,");
		}

		if (!ignoreColumns.contains(JAVADOC)) {
			builder.append(prefix + "JAVADOC,");
		}

		if (!ignoreColumns.contains(LABELED_STATEMENT)) {
			builder.append(prefix + "LABELED_STATEMENT,");
		}

		if (!ignoreColumns.contains(LINE_COMMENT)) {
			builder.append(prefix + "LINE_COMMENT,");
		}

		if (!ignoreColumns.contains(MARKER_ANNOTATION)) {
			builder.append(prefix + "MARKER_ANNOTATION,");
		}

		if (!ignoreColumns.contains(MEMBER_REF)) {
			builder.append(prefix + "MEMBER_REF,");
		}

		if (!ignoreColumns.contains(MEMBER_VALUE_PAIR)) {
			builder.append(prefix + "MEMBER_VALUE_PAIR,");
		}

		if (!ignoreColumns.contains(METHOD_DECLARATION)) {
			builder.append(prefix + "METHOD_DECLARATION,");
		}

		if (!ignoreColumns.contains(METHOD_INVOCATION)) {
			builder.append(prefix + "METHOD_INVOCATION,");
		}

		if (!ignoreColumns.contains(METHOD_REF)) {
			builder.append(prefix + "METHOD_REF,");
		}

		if (!ignoreColumns.contains(METHOD_REF_PARAMETER)) {
			builder.append(prefix + "METHOD_REF_PARAMETER,");
		}

		if (!ignoreColumns.contains(MODIFIER)) {
			builder.append(prefix + "MODIFIER,");
		}

		if (!ignoreColumns.contains(NORMAL_ANNOTATION)) {
			builder.append(prefix + "NORMAL_ANNOTATION,");
		}

		if (!ignoreColumns.contains(NULL_LITERAL)) {
			builder.append(prefix + "NULL_LITERAL,");
		}

		if (!ignoreColumns.contains(NUMBER_LITERAL)) {
			builder.append(prefix + "NUMBER_LITERAL,");
		}

		if (!ignoreColumns.contains(PACKAGE_DECLARATION)) {
			builder.append(prefix + "PACKAGE_DECLARATION,");
		}

		if (!ignoreColumns.contains(PARAMETERIZED_TYPE)) {
			builder.append(prefix + "PARAMETERIZED_TYPE,");
		}

		if (!ignoreColumns.contains(PARENTHESIZED_EXPRESSION)) {
			builder.append(prefix + "PARENTHESIZED_EXPRESSION,");
		}

		if (!ignoreColumns.contains(POSTFIX_EXPRESSION)) {
			builder.append(prefix + "POSTFIX_EXPRESSION,");
		}

		if (!ignoreColumns.contains(PREFIX_EXPRESSION)) {
			builder.append(prefix + "PREFIX_EXPRESSION,");
		}

		if (!ignoreColumns.contains(PRIMITIVE_TYPE)) {
			builder.append(prefix + "PRIMITIVE_TYPE,");
		}

		if (!ignoreColumns.contains(QUALIFIED_NAME)) {
			builder.append(prefix + "QUALIFIED_NAME,");
		}

		if (!ignoreColumns.contains(QUALIFIED_TYPE)) {
			builder.append(prefix + "QUALIFIED_TYPE,");
		}

		if (!ignoreColumns.contains(RETURN_STATEMENT)) {
			builder.append(prefix + "RETURN_STATEMENT,");
		}

		if (!ignoreColumns.contains(SIMPLE_NAME)) {
			builder.append(prefix + "SIMPLE_NAME,");
		}

		if (!ignoreColumns.contains(SIMPLE_TYPE)) {
			builder.append(prefix + "SIMPLE_TYPE,");
		}

		if (!ignoreColumns.contains(SINGLE_MEMBER_ANNOTATION)) {
			builder.append(prefix + "SINGLE_MEMBER_ANNOTATION,");
		}

		if (!ignoreColumns.contains(SINGLE_VARIABLE_DECLARATION)) {
			builder.append(prefix + "SINGLE_VARIABLE_DECLARATION,");
		}

		if (!ignoreColumns.contains(STRING_LITERAL)) {
			builder.append(prefix + "STRING_LITERAL,");
		}

		if (!ignoreColumns.contains(SUPER_CONSTRUCTOR_INVOCATION)) {
			builder.append(prefix + "SUPER_CONSTRUCTOR_INVOCATION,");
		}

		if (!ignoreColumns.contains(SUPER_FIELD_ACCESS)) {
			builder.append(prefix + "SUPER_FIELD_ACCESS,");
		}

		if (!ignoreColumns.contains(SUPER_METHOD_INVOCATION)) {
			builder.append(prefix + "SUPER_METHOD_INVOCATION,");
		}

		if (!ignoreColumns.contains(SWITCH_CASE)) {
			builder.append(prefix + "SWITCH_CASE,");
		}

		if (!ignoreColumns.contains(SWITCH_STATEMENT)) {
			builder.append(prefix + "SWITCH_STATEMENT,");
		}

		if (!ignoreColumns.contains(SYNCHRONIZED_STATEMENT)) {
			builder.append(prefix + "SYNCHRONIZED_STATEMENT,");
		}

		if (!ignoreColumns.contains(TAG_ELEMENT)) {
			builder.append(prefix + "TAG_ELEMENT,");
		}

		if (!ignoreColumns.contains(TEXT_ELEMENT)) {
			builder.append(prefix + "TEXT_ELEMENT,");
		}

		if (!ignoreColumns.contains(THIS_EXPRESSION)) {
			builder.append(prefix + "THIS_EXPRESSION,");
		}

		if (!ignoreColumns.contains(THROW_STATEMENT)) {
			builder.append(prefix + "THROW_STATEMENT,");
		}

		if (!ignoreColumns.contains(TRY_STATEMENT)) {
			builder.append(prefix + "TRY_STATEMENT,");
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION)) {
			builder.append(prefix + "TYPE_DECLARATION,");
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION_STATEMENT)) {
			builder.append(prefix + "TYPE_DECLARATION_STATEMENT,");
		}

		if (!ignoreColumns.contains(TYPE_LITERAL)) {
			builder.append(prefix + "TYPE_LITERAL,");
		}

		if (!ignoreColumns.contains(TYPE_PARAMETER)) {
			builder.append(prefix + "TYPE_PARAMETER,");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_EXPRESSION)) {
			builder.append(prefix + "VARIABLE_DECLARATION_EXPRESSION,");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_FRAGMENT)) {
			builder.append(prefix + "VARIABLE_DECLARATION_FRAGMENT,");
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_STATEMENT)) {
			builder.append(prefix + "VARIABLE_DECLARATION_STATEMENT,");
		}

		if (!ignoreColumns.contains(WHILE_STATEMENT)) {
			builder.append(prefix + "WHILE_STATEMENT,");
		}

		if (!ignoreColumns.contains(WILDCARD_TYPE)) {
			builder.append(prefix + "WILDCARD_TYPE,");
		}

		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}

	public static String getEvaluationArffHeader(
			final Collection<Integer> ignoreColumns, final String relationName) {
		return "@RELATION " + relationName
				+ System.getProperty("line.separator")
				+ getArffHeader(ignoreColumns, "BEFORE_")
				+ System.getProperty("line.separator") + "@DATA"
				+ System.getProperty("line.separator");
	}

	public static String getTrainingArffHeader(
			final Collection<Integer> ignoreColumns, final String relationName) {
		return "@RELATION " + relationName
				+ System.getProperty("line.separator")
				+ getArffHeader(ignoreColumns, "BEFORE_")
				+ System.getProperty("line.separator")
				+ getArffHeader(ignoreColumns, "AFTER_")
				+ System.getProperty("line.separator") + "@DATA"
				+ System.getProperty("line.separator");
	}

	public static String getSingleColumnTrainingArffHeader(
			final Collection<Integer> ignoreColumns, final String relationName,
			final String afterValueName) {
		return "@RELATION " + relationName
				+ System.getProperty("line.separator")
				+ getArffHeader(ignoreColumns, "BEFORE_")
				+ System.getProperty("line.separator") + "@ATTRIBUTE "
				+ afterValueName + " NUMERIC"
				+ System.getProperty("line.separator") + "@DATA"
				+ System.getProperty("line.separator");
	}

	private static String getArffHeader(
			final Collection<Integer> ignoreColumns, final String prefix) {
		final StringBuilder builder = new StringBuilder();

		final String lineSeparator = System.getProperty("line.separator");

		if (!ignoreColumns.contains(ANNOTATION_TYPE_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "ANNOTATION_TYPE_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(ANNOTATION_TYPE_MEMBER_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "ANNOTATION_TYPE_MEMBER_DECLARATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ANONYMOUS_CLASS_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "ANONYMOUS_CLASS_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(ARRAY_ACCESS)) {
			builder.append("@ATTRIBUTE " + prefix + "ARRAY_ACCESS NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ARRAY_CREATION)) {
			builder.append("@ATTRIBUTE " + prefix + "ARRAY_CREATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ARRAY_INITIALIZER)) {
			builder.append("@ATTRIBUTE " + prefix + "ARRAY_INITIALIZER NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ARRAY_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix + "ARRAY_TYPE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ASSERT_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "ASSERT_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ASSIGNMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "ASSIGNMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(BLOCK)) {
			builder.append("@ATTRIBUTE " + prefix + "BLOCK NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(BLOCK_COMMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "BLOCK_COMMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(BOOLEAN_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "BOOLEAN_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(BREAK_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "BREAK_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(CAST_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix + "CAST_EXPRESSION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(CATCH_CLAUSE)) {
			builder.append("@ATTRIBUTE " + prefix + "CATCH_CLAUSE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(CHARACTER_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "CHARACTER_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(CLASS_INSTANCE_CREATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "CLASS_INSTANCE_CREATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(COMPILATION_UNIT)) {
			builder.append("@ATTRIBUTE " + prefix + "COMPILATION_UNIT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(CONDITIONAL_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "CONDITIONAL_EXPRESSION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(CONSTRUCTOR_INVOCATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "CONSTRUCTOR_INVOCATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(CONTINUE_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "CONTINUE_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(DO_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "DO_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(EMPTY_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "EMPTY_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(ENHANCED_FOR_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "ENHANCED_FOR_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(ENUM_CONSTANT_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "ENUM_CONSTANT_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(ENUM_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix + "ENUM_DECLARATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(EXPRESSION_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "EXPRESSION_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(FIELD_ACCESS)) {
			builder.append("@ATTRIBUTE " + prefix + "FIELD_ACCESS NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(FIELD_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix + "FIELD_DECLARATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(FOR_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "FOR_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(IF_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "IF_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(IMPORT_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "IMPORT_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(INFIX_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix + "INFIX_EXPRESSION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(INITIALIZER)) {
			builder.append("@ATTRIBUTE " + prefix + "INITIALIZER NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(INSTANCEOF_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "INSTANCEOF_EXPRESSION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(JAVADOC)) {
			builder.append("@ATTRIBUTE " + prefix + "JAVADOC NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(LABELED_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "LABELED_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(LINE_COMMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "LINE_COMMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(MARKER_ANNOTATION)) {
			builder.append("@ATTRIBUTE " + prefix + "MARKER_ANNOTATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(MEMBER_REF)) {
			builder.append("@ATTRIBUTE " + prefix + "MEMBER_REF NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(MEMBER_VALUE_PAIR)) {
			builder.append("@ATTRIBUTE " + prefix + "MEMBER_VALUE_PAIR NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(METHOD_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "METHOD_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(METHOD_INVOCATION)) {
			builder.append("@ATTRIBUTE " + prefix + "METHOD_INVOCATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(METHOD_REF)) {
			builder.append("@ATTRIBUTE " + prefix + "METHOD_REF NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(METHOD_REF_PARAMETER)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "METHOD_REF_PARAMETER NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(MODIFIER)) {
			builder.append("@ATTRIBUTE " + prefix + "MODIFIER NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(NORMAL_ANNOTATION)) {
			builder.append("@ATTRIBUTE " + prefix + "NORMAL_ANNOTATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(NULL_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "NULL_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(NUMBER_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "NUMBER_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(PACKAGE_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "PACKAGE_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(PARAMETERIZED_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "PARAMETERIZED_TYPE NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(PARENTHESIZED_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "PARENTHESIZED_EXPRESSION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(POSTFIX_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "POSTFIX_EXPRESSION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(PREFIX_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix + "PREFIX_EXPRESSION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(PRIMITIVE_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix + "PRIMITIVE_TYPE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(QUALIFIED_NAME)) {
			builder.append("@ATTRIBUTE " + prefix + "QUALIFIED_NAME NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(QUALIFIED_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix + "QUALIFIED_TYPE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(RETURN_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "RETURN_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SIMPLE_NAME)) {
			builder.append("@ATTRIBUTE " + prefix + "SIMPLE_NAME NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SIMPLE_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix + "SIMPLE_TYPE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SINGLE_MEMBER_ANNOTATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SINGLE_MEMBER_ANNOTATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(SINGLE_VARIABLE_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SINGLE_VARIABLE_DECLARATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(STRING_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "STRING_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SUPER_CONSTRUCTOR_INVOCATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SUPER_CONSTRUCTOR_INVOCATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(SUPER_FIELD_ACCESS)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SUPER_FIELD_ACCESS NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(SUPER_METHOD_INVOCATION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SUPER_METHOD_INVOCATION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(SWITCH_CASE)) {
			builder.append("@ATTRIBUTE " + prefix + "SWITCH_CASE NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SWITCH_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "SWITCH_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(SYNCHRONIZED_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "SYNCHRONIZED_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(TAG_ELEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "TAG_ELEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(TEXT_ELEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "TEXT_ELEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(THIS_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix + "THIS_EXPRESSION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(THROW_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "THROW_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(TRY_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "TRY_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION)) {
			builder.append("@ATTRIBUTE " + prefix + "TYPE_DECLARATION NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(TYPE_DECLARATION_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "TYPE_DECLARATION_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(TYPE_LITERAL)) {
			builder.append("@ATTRIBUTE " + prefix + "TYPE_LITERAL NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(TYPE_PARAMETER)) {
			builder.append("@ATTRIBUTE " + prefix + "TYPE_PARAMETER NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_EXPRESSION)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "VARIABLE_DECLARATION_EXPRESSION NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_FRAGMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "VARIABLE_DECLARATION_FRAGMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(VARIABLE_DECLARATION_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix
					+ "VARIABLE_DECLARATION_STATEMENT NUMERIC" + lineSeparator);
		}

		if (!ignoreColumns.contains(WHILE_STATEMENT)) {
			builder.append("@ATTRIBUTE " + prefix + "WHILE_STATEMENT NUMERIC"
					+ lineSeparator);
		}

		if (!ignoreColumns.contains(WILDCARD_TYPE)) {
			builder.append("@ATTRIBUTE " + prefix + "WILDCARD_TYPE NUMERIC"
					+ lineSeparator);
		}

		return builder.toString();
	}

	public Set<Integer> getElementContainingColumns() {
		final Set<Integer> result = new HashSet<Integer>();

		if (this.getAnnotationTypeDeclarationCount() != 0) {
			result.add(ANNOTATION_TYPE_DECLARATION);
		}

		if (this.getAnnotationTypeMemberDeclarationCount() != 0) {
			result.add(ANNOTATION_TYPE_MEMBER_DECLARATION);
		}

		if (this.getAnonymousClassDeclarationCount() != 0) {
			result.add(ANONYMOUS_CLASS_DECLARATION);
		}

		if (this.getArrayAccessCount() != 0) {
			result.add(ARRAY_ACCESS);
		}

		if (this.getArrayCreationCount() != 0) {
			result.add(ARRAY_CREATION);
		}

		if (this.getArrayInitializerCount() != 0) {
			result.add(ARRAY_INITIALIZER);
		}

		if (this.getArrayTypeCount() != 0) {
			result.add(ARRAY_TYPE);
		}

		if (this.getAssertStatementCount() != 0) {
			result.add(ASSERT_STATEMENT);
		}

		if (this.getAssignmentCount() != 0) {
			result.add(ASSIGNMENT);
		}

		if (this.getBlockCount() != 0) {
			result.add(BLOCK);
		}

		if (this.getBlockCommentCount() != 0) {
			result.add(BLOCK_COMMENT);
		}

		if (this.getBooleanLiteralCount() != 0) {
			result.add(BOOLEAN_LITERAL);
		}

		if (this.getBreakStatementCount() != 0) {
			result.add(BREAK_STATEMENT);
		}

		if (this.getCastExpressionCount() != 0) {
			result.add(CAST_EXPRESSION);
		}

		if (this.getCatchClauseCount() != 0) {
			result.add(CATCH_CLAUSE);
		}

		if (this.getCharacterLiteralCount() != 0) {
			result.add(CHARACTER_LITERAL);
		}

		if (this.getClassInstanceCreationCount() != 0) {
			result.add(CLASS_INSTANCE_CREATION);
		}

		if (this.getCompilationUnitCount() != 0) {
			result.add(COMPILATION_UNIT);
		}

		if (this.getConditionalExpressionCount() != 0) {
			result.add(CONDITIONAL_EXPRESSION);
		}

		if (this.getConstructorInvocationCount() != 0) {
			result.add(CONSTRUCTOR_INVOCATION);
		}

		if (this.getContinueStatementCount() != 0) {
			result.add(CONTINUE_STATEMENT);
		}

		if (this.getDoStatementCount() != 0) {
			result.add(DO_STATEMENT);
		}

		if (this.getEmptyStatementCount() != 0) {
			result.add(EMPTY_STATEMENT);
		}

		if (this.getEnhancedForStatementCount() != 0) {
			result.add(ENHANCED_FOR_STATEMENT);
		}

		if (this.getEnumConstantDeclarationCount() != 0) {
			result.add(ENUM_CONSTANT_DECLARATION);
		}

		if (this.getEnumDeclarationCount() != 0) {
			result.add(ENUM_DECLARATION);
		}

		if (this.getExpressionStatementCount() != 0) {
			result.add(EXPRESSION_STATEMENT);
		}

		if (this.getFieldAccessCount() != 0) {
			result.add(FIELD_ACCESS);
		}

		if (this.getFieldDeclarationCount() != 0) {
			result.add(FIELD_DECLARATION);
		}

		if (this.getForStatementCount() != 0) {
			result.add(FOR_STATEMENT);
		}

		if (this.getIfStatementCount() != 0) {
			result.add(IF_STATEMENT);
		}

		if (this.getImportDeclarationCount() != 0) {
			result.add(IMPORT_DECLARATION);
		}

		if (this.getInfixExpressionCount() != 0) {
			result.add(INFIX_EXPRESSION);
		}

		if (this.getInitializerCount() != 0) {
			result.add(INITIALIZER);
		}

		if (this.getInstanceofExpressionCount() != 0) {
			result.add(INSTANCEOF_EXPRESSION);
		}

		if (this.getJavadocCount() != 0) {
			result.add(JAVADOC);
		}

		if (this.getLabeledStatementCount() != 0) {
			result.add(LABELED_STATEMENT);
		}

		if (this.getLineCommentCount() != 0) {
			result.add(LINE_COMMENT);
		}

		if (this.getMarkerAnnotationCount() != 0) {
			result.add(MARKER_ANNOTATION);
		}

		if (this.getMemberRefCount() != 0) {
			result.add(MEMBER_REF);
		}

		if (this.getMemberValuePairCount() != 0) {
			result.add(MEMBER_VALUE_PAIR);
		}

		if (this.getMethodDeclarationCount() != 0) {
			result.add(METHOD_DECLARATION);
		}

		if (this.getMethodInvocationCount() != 0) {
			result.add(METHOD_INVOCATION);
		}

		if (this.getMethodRefCount() != 0) {
			result.add(METHOD_REF);
		}

		if (this.getMethodRefParameterCount() != 0) {
			result.add(METHOD_REF_PARAMETER);
		}

		if (this.getModifierCount() != 0) {
			result.add(MODIFIER);
		}

		if (this.getNormalAnnotationCount() != 0) {
			result.add(NORMAL_ANNOTATION);
		}

		if (this.getNullLiteralCount() != 0) {
			result.add(NULL_LITERAL);
		}

		if (this.getNumberLiteralCount() != 0) {
			result.add(NUMBER_LITERAL);
		}

		if (this.getPackageDeclarationCount() != 0) {
			result.add(PACKAGE_DECLARATION);
		}

		if (this.getParameterizedTypeCount() != 0) {
			result.add(PARAMETERIZED_TYPE);
		}

		if (this.getParenthesizedExpressionCount() != 0) {
			result.add(PARENTHESIZED_EXPRESSION);
		}

		if (this.getPostfixExpressionCount() != 0) {
			result.add(POSTFIX_EXPRESSION);
		}

		if (this.getPrefixExpressionCount() != 0) {
			result.add(PREFIX_EXPRESSION);
		}

		if (this.getPrimitiveTypeCount() != 0) {
			result.add(PRIMITIVE_TYPE);
		}

		if (this.getQualifiedNameCount() != 0) {
			result.add(QUALIFIED_NAME);
		}

		if (this.getQualifiedTypeCount() != 0) {
			result.add(QUALIFIED_TYPE);
		}

		if (this.getReturnStatementCount() != 0) {
			result.add(RETURN_STATEMENT);
		}

		if (this.getSimpleNameCount() != 0) {
			result.add(SIMPLE_NAME);
		}

		if (this.getSimpleTypeCount() != 0) {
			result.add(SIMPLE_TYPE);
		}

		if (this.getSingleMemberAnnotationCount() != 0) {
			result.add(SINGLE_MEMBER_ANNOTATION);
		}

		if (this.getSingleVariableDeclarationCount() != 0) {
			result.add(SINGLE_VARIABLE_DECLARATION);
		}

		if (this.getStringLiteralCount() != 0) {
			result.add(STRING_LITERAL);
		}

		if (this.getSuperConstructorInvocationCount() != 0) {
			result.add(SUPER_CONSTRUCTOR_INVOCATION);
		}

		if (this.getSuperFieldAccessCount() != 0) {
			result.add(SUPER_FIELD_ACCESS);
		}

		if (this.getSuperMethodInvocationCount() != 0) {
			result.add(SUPER_METHOD_INVOCATION);
		}

		if (this.getSwitchCaseCount() != 0) {
			result.add(SWITCH_CASE);
		}

		if (this.getSwitchStatementCount() != 0) {
			result.add(SWITCH_STATEMENT);
		}

		if (this.getSynchronizedStatementCount() != 0) {
			result.add(SYNCHRONIZED_STATEMENT);
		}

		if (this.getTagElementCount() != 0) {
			result.add(TAG_ELEMENT);
		}

		if (this.getTextElementCount() != 0) {
			result.add(TEXT_ELEMENT);
		}

		if (this.getThisExpressionCount() != 0) {
			result.add(THIS_EXPRESSION);
		}

		if (this.getThrowStatementCount() != 0) {
			result.add(THROW_STATEMENT);
		}

		if (this.getTryStatementCount() != 0) {
			result.add(TRY_STATEMENT);
		}

		if (this.getTypeDeclarationCount() != 0) {
			result.add(TYPE_DECLARATION);
		}

		if (this.getTypeDeclarationStatementCount() != 0) {
			result.add(TYPE_DECLARATION_STATEMENT);
		}

		if (this.getTypeLiteralCount() != 0) {
			result.add(TYPE_LITERAL);
		}

		if (this.getTypeParameterCount() != 0) {
			result.add(TYPE_PARAMETER);
		}

		if (this.getVariableDeclarationExpressionCount() != 0) {
			result.add(VARIABLE_DECLARATION_EXPRESSION);
		}

		if (this.getVariableDeclarationFragmentCount() != 0) {
			result.add(VARIABLE_DECLARATION_FRAGMENT);
		}

		if (this.getVariableDeclarationStatementCount() != 0) {
			result.add(VARIABLE_DECLARATION_STATEMENT);
		}

		if (this.getWhileStatementCount() != 0) {
			result.add(WHILE_STATEMENT);
		}

		if (this.getWildcardTypeCount() != 0) {
			result.add(WILDCARD_TYPE);
		}

		return result;
	}

	public static Set<Integer> getNodeTypeIntegers() {
		final Set<Integer> result = new HashSet<Integer>();

		result.add(ANNOTATION_TYPE_DECLARATION);

		result.add(ANNOTATION_TYPE_MEMBER_DECLARATION);

		result.add(ANONYMOUS_CLASS_DECLARATION);

		result.add(ARRAY_ACCESS);

		result.add(ARRAY_CREATION);

		result.add(ARRAY_INITIALIZER);

		result.add(ARRAY_TYPE);

		result.add(ASSERT_STATEMENT);

		result.add(ASSIGNMENT);

		result.add(BLOCK);

		result.add(BLOCK_COMMENT);

		result.add(BOOLEAN_LITERAL);

		result.add(BREAK_STATEMENT);

		result.add(CAST_EXPRESSION);

		result.add(CATCH_CLAUSE);

		result.add(CHARACTER_LITERAL);

		result.add(CLASS_INSTANCE_CREATION);

		result.add(COMPILATION_UNIT);

		result.add(CONDITIONAL_EXPRESSION);

		result.add(CONSTRUCTOR_INVOCATION);

		result.add(CONTINUE_STATEMENT);

		result.add(DO_STATEMENT);

		result.add(EMPTY_STATEMENT);

		result.add(ENHANCED_FOR_STATEMENT);

		result.add(ENUM_CONSTANT_DECLARATION);

		result.add(ENUM_DECLARATION);

		result.add(EXPRESSION_STATEMENT);

		result.add(FIELD_ACCESS);

		result.add(FIELD_DECLARATION);

		result.add(FOR_STATEMENT);

		result.add(IF_STATEMENT);

		result.add(IMPORT_DECLARATION);

		result.add(INFIX_EXPRESSION);

		result.add(INITIALIZER);

		result.add(INSTANCEOF_EXPRESSION);

		result.add(JAVADOC);

		result.add(LABELED_STATEMENT);

		result.add(LINE_COMMENT);

		result.add(MARKER_ANNOTATION);

		result.add(MEMBER_REF);

		result.add(MEMBER_VALUE_PAIR);

		result.add(METHOD_DECLARATION);

		result.add(METHOD_INVOCATION);

		result.add(METHOD_REF);

		result.add(METHOD_REF_PARAMETER);

		result.add(MODIFIER);

		result.add(NORMAL_ANNOTATION);

		result.add(NULL_LITERAL);

		result.add(NUMBER_LITERAL);

		result.add(PACKAGE_DECLARATION);

		result.add(PARAMETERIZED_TYPE);

		result.add(PARENTHESIZED_EXPRESSION);

		result.add(POSTFIX_EXPRESSION);

		result.add(PREFIX_EXPRESSION);

		result.add(PRIMITIVE_TYPE);

		result.add(QUALIFIED_NAME);

		result.add(QUALIFIED_TYPE);

		result.add(RETURN_STATEMENT);

		result.add(SIMPLE_NAME);

		result.add(SIMPLE_TYPE);

		result.add(SINGLE_MEMBER_ANNOTATION);

		result.add(SINGLE_VARIABLE_DECLARATION);

		result.add(STRING_LITERAL);

		result.add(SUPER_CONSTRUCTOR_INVOCATION);

		result.add(SUPER_FIELD_ACCESS);

		result.add(SUPER_METHOD_INVOCATION);

		result.add(SWITCH_CASE);

		result.add(SWITCH_STATEMENT);

		result.add(SYNCHRONIZED_STATEMENT);

		result.add(TAG_ELEMENT);

		result.add(TEXT_ELEMENT);

		result.add(THIS_EXPRESSION);

		result.add(THROW_STATEMENT);

		result.add(TRY_STATEMENT);

		result.add(TYPE_DECLARATION);

		result.add(TYPE_DECLARATION_STATEMENT);

		result.add(TYPE_LITERAL);

		result.add(TYPE_PARAMETER);

		result.add(VARIABLE_DECLARATION_EXPRESSION);

		result.add(VARIABLE_DECLARATION_FRAGMENT);

		result.add(VARIABLE_DECLARATION_STATEMENT);

		result.add(WHILE_STATEMENT);

		result.add(WILDCARD_TYPE);

		return result;
	}

	public int getUnmatchColumns(final VectorData another) {
		int result = 0;

		final Set<Integer> nodeTypes = getNodeTypeIntegers();

		for (final int nodeType : nodeTypes) {
			final int thisValue = (this.vector.containsKey(nodeType)) ? this.vector
					.get(nodeType) : -1;
			final int anotherValue = (another.getVector().containsKey(nodeType)) ? another
					.getVector().get(nodeType) : -1;

			if (thisValue != anotherValue) {
				result++;
			}
		}

		return result;
	}
}
