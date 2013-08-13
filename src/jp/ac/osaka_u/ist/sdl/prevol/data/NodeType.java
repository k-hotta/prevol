package jp.ac.osaka_u.ist.sdl.prevol.data;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * ノードの種類を表す列挙型
 * 
 * @author k-hotta
 * 
 */
public enum NodeType {

	ANNOTATION_TYPE_DECLARATION(
			org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_DECLARATION)
					.getSimpleName()),

	ANNOTATION_TYPE_MEMBER_DECLARATION(
			org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_MEMBER_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ANNOTATION_TYPE_MEMBER_DECLARATION)
					.getSimpleName()),

	ANONYMOUS_CLASS_DECLARATION(
			org.eclipse.jdt.core.dom.ASTNode.ANONYMOUS_CLASS_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ANONYMOUS_CLASS_DECLARATION)
					.getSimpleName()),

	ARRAY_ACCESS(org.eclipse.jdt.core.dom.ASTNode.ARRAY_ACCESS, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.ARRAY_ACCESS)
			.getSimpleName()),

	ARRAY_CREATION(org.eclipse.jdt.core.dom.ASTNode.ARRAY_CREATION, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.ARRAY_CREATION)
			.getSimpleName()),

	ARRAY_INITIALIZER(org.eclipse.jdt.core.dom.ASTNode.ARRAY_INITIALIZER,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ARRAY_INITIALIZER)
					.getSimpleName()),

	ARRAY_TYPE(org.eclipse.jdt.core.dom.ASTNode.ARRAY_TYPE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.ARRAY_TYPE)
			.getSimpleName()),

	ASSERT_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.ASSERT_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ASSERT_STATEMENT)
					.getSimpleName()),

	ASSIGNMENT(org.eclipse.jdt.core.dom.ASTNode.ASSIGNMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.ASSIGNMENT)
			.getSimpleName()),

	BLOCK(org.eclipse.jdt.core.dom.ASTNode.BLOCK, ASTNode.nodeClassForType(
			org.eclipse.jdt.core.dom.ASTNode.BLOCK).getSimpleName()),

	BLOCK_COMMENT(org.eclipse.jdt.core.dom.ASTNode.BLOCK_COMMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.BLOCK_COMMENT)
			.getSimpleName()),

	BOOLEAN_LITERAL(org.eclipse.jdt.core.dom.ASTNode.BOOLEAN_LITERAL, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.BOOLEAN_LITERAL)
			.getSimpleName()),

	BREAK_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.BREAK_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.BREAK_STATEMENT)
			.getSimpleName()),

	CAST_EXPRESSION(org.eclipse.jdt.core.dom.ASTNode.CAST_EXPRESSION, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.CAST_EXPRESSION)
			.getSimpleName()),

	CATCH_CLAUSE(org.eclipse.jdt.core.dom.ASTNode.CATCH_CLAUSE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.CATCH_CLAUSE)
			.getSimpleName()),

	CHARACTER_LITERAL(org.eclipse.jdt.core.dom.ASTNode.CHARACTER_LITERAL,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.CHARACTER_LITERAL)
					.getSimpleName()),

	CLASS_INSTANCE_CREATION(
			org.eclipse.jdt.core.dom.ASTNode.CLASS_INSTANCE_CREATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.CLASS_INSTANCE_CREATION)
					.getSimpleName()),

	COMPILATION_UNIT(org.eclipse.jdt.core.dom.ASTNode.COMPILATION_UNIT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.COMPILATION_UNIT)
					.getSimpleName()),

	CONDITIONAL_EXPRESSION(
			org.eclipse.jdt.core.dom.ASTNode.CONDITIONAL_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.CONDITIONAL_EXPRESSION)
					.getSimpleName()),

	CONSTRUCTOR_INVOCATION(
			org.eclipse.jdt.core.dom.ASTNode.CONSTRUCTOR_INVOCATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.CONSTRUCTOR_INVOCATION)
					.getSimpleName()),

	CONTINUE_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.CONTINUE_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.CONTINUE_STATEMENT)
					.getSimpleName()),

	DO_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT)
			.getSimpleName()),

	EMPTY_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.EMPTY_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.EMPTY_STATEMENT)
			.getSimpleName()),

	ENHANCED_FOR_STATEMENT(
			org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT)
					.getSimpleName()),

	ENUM_CONSTANT_DECLARATION(
			org.eclipse.jdt.core.dom.ASTNode.ENUM_CONSTANT_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ENUM_CONSTANT_DECLARATION)
					.getSimpleName()),

	ENUM_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.ENUM_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.ENUM_DECLARATION)
					.getSimpleName()),

	EXPRESSION_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.EXPRESSION_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.EXPRESSION_STATEMENT)
					.getSimpleName()),

	FIELD_ACCESS(org.eclipse.jdt.core.dom.ASTNode.FIELD_ACCESS, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.FIELD_ACCESS)
			.getSimpleName()),

	FIELD_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.FIELD_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.FIELD_DECLARATION)
					.getSimpleName()),

	FOR_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT)
			.getSimpleName()),

	IF_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.IF_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.IF_STATEMENT)
			.getSimpleName()),

	IMPORT_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.IMPORT_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.IMPORT_DECLARATION)
					.getSimpleName()),

	INFIX_EXPRESSION(org.eclipse.jdt.core.dom.ASTNode.INFIX_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.INFIX_EXPRESSION)
					.getSimpleName()),

	INITIALIZER(org.eclipse.jdt.core.dom.ASTNode.INITIALIZER, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.INITIALIZER)
			.getSimpleName()),

	INSTANCEOF_EXPRESSION(
			org.eclipse.jdt.core.dom.ASTNode.INSTANCEOF_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.INSTANCEOF_EXPRESSION)
					.getSimpleName()),

	JAVADOC(org.eclipse.jdt.core.dom.ASTNode.JAVADOC, ASTNode.nodeClassForType(
			org.eclipse.jdt.core.dom.ASTNode.JAVADOC).getSimpleName()),

	LABELED_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.LABELED_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.LABELED_STATEMENT)
					.getSimpleName()),

	LINE_COMMENT(org.eclipse.jdt.core.dom.ASTNode.LINE_COMMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.LINE_COMMENT)
			.getSimpleName()),

	MARKER_ANNOTATION(org.eclipse.jdt.core.dom.ASTNode.MARKER_ANNOTATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.MARKER_ANNOTATION)
					.getSimpleName()),

	MEMBER_REF(org.eclipse.jdt.core.dom.ASTNode.MEMBER_REF, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.MEMBER_REF)
			.getSimpleName()),

	MEMBER_VALUE_PAIR(org.eclipse.jdt.core.dom.ASTNode.MEMBER_VALUE_PAIR,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.MEMBER_VALUE_PAIR)
					.getSimpleName()),

	METHOD_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.METHOD_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.METHOD_DECLARATION)
					.getSimpleName()),

	METHOD_INVOCATION(org.eclipse.jdt.core.dom.ASTNode.METHOD_INVOCATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.METHOD_INVOCATION)
					.getSimpleName()),

	METHOD_REF(org.eclipse.jdt.core.dom.ASTNode.METHOD_REF, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.METHOD_REF)
			.getSimpleName()),

	METHOD_REF_PARAMETER(org.eclipse.jdt.core.dom.ASTNode.METHOD_REF_PARAMETER,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.METHOD_REF_PARAMETER)
					.getSimpleName()),

	MODIFIER(org.eclipse.jdt.core.dom.ASTNode.MODIFIER, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.MODIFIER)
			.getSimpleName()),

	NORMAL_ANNOTATION(org.eclipse.jdt.core.dom.ASTNode.NORMAL_ANNOTATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.NORMAL_ANNOTATION)
					.getSimpleName()),

	NULL_LITERAL(org.eclipse.jdt.core.dom.ASTNode.NULL_LITERAL, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.NULL_LITERAL)
			.getSimpleName()),

	NUMBER_LITERAL(org.eclipse.jdt.core.dom.ASTNode.NUMBER_LITERAL, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.NUMBER_LITERAL)
			.getSimpleName()),

	PACKAGE_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.PACKAGE_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.PACKAGE_DECLARATION)
					.getSimpleName()),

	PARAMETERIZED_TYPE(org.eclipse.jdt.core.dom.ASTNode.PARAMETERIZED_TYPE,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.PARAMETERIZED_TYPE)
					.getSimpleName()),

	PARENTHESIZED_EXPRESSION(
			org.eclipse.jdt.core.dom.ASTNode.PARENTHESIZED_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.PARENTHESIZED_EXPRESSION)
					.getSimpleName()),

	POSTFIX_EXPRESSION(org.eclipse.jdt.core.dom.ASTNode.POSTFIX_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.POSTFIX_EXPRESSION)
					.getSimpleName()),

	PREFIX_EXPRESSION(org.eclipse.jdt.core.dom.ASTNode.PREFIX_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.PREFIX_EXPRESSION)
					.getSimpleName()),

	PRIMITIVE_TYPE(org.eclipse.jdt.core.dom.ASTNode.PRIMITIVE_TYPE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.PRIMITIVE_TYPE)
			.getSimpleName()),

	QUALIFIED_NAME(org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_NAME, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_NAME)
			.getSimpleName()),

	QUALIFIED_TYPE(org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_TYPE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.QUALIFIED_TYPE)
			.getSimpleName()),

	RETURN_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.RETURN_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.RETURN_STATEMENT)
					.getSimpleName()),

	SIMPLE_NAME(org.eclipse.jdt.core.dom.ASTNode.SIMPLE_NAME, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.SIMPLE_NAME)
			.getSimpleName()),

	SIMPLE_TYPE(org.eclipse.jdt.core.dom.ASTNode.SIMPLE_TYPE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.SIMPLE_TYPE)
			.getSimpleName()),

	SINGLE_MEMBER_ANNOTATION(
			org.eclipse.jdt.core.dom.ASTNode.SINGLE_MEMBER_ANNOTATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SINGLE_MEMBER_ANNOTATION)
					.getSimpleName()),

	SINGLE_VARIABLE_DECLARATION(
			org.eclipse.jdt.core.dom.ASTNode.SINGLE_VARIABLE_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SINGLE_VARIABLE_DECLARATION)
					.getSimpleName()),

	STRING_LITERAL(org.eclipse.jdt.core.dom.ASTNode.STRING_LITERAL, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.STRING_LITERAL)
			.getSimpleName()),

	SUPER_CONSTRUCTOR_INVOCATION(
			org.eclipse.jdt.core.dom.ASTNode.SUPER_CONSTRUCTOR_INVOCATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SUPER_CONSTRUCTOR_INVOCATION)
					.getSimpleName()),

	SUPER_FIELD_ACCESS(org.eclipse.jdt.core.dom.ASTNode.SUPER_FIELD_ACCESS,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SUPER_FIELD_ACCESS)
					.getSimpleName()),

	SUPER_METHOD_INVOCATION(
			org.eclipse.jdt.core.dom.ASTNode.SUPER_METHOD_INVOCATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SUPER_METHOD_INVOCATION)
					.getSimpleName()),

	SWITCH_CASE(org.eclipse.jdt.core.dom.ASTNode.SWITCH_CASE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.SWITCH_CASE)
			.getSimpleName()),

	SWITCH_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.SWITCH_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SWITCH_STATEMENT)
					.getSimpleName()),

	SYNCHRONIZED_STATEMENT(
			org.eclipse.jdt.core.dom.ASTNode.SYNCHRONIZED_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.SYNCHRONIZED_STATEMENT)
					.getSimpleName()),

	TAG_ELEMENT(org.eclipse.jdt.core.dom.ASTNode.TAG_ELEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.TAG_ELEMENT)
			.getSimpleName()),

	TEXT_ELEMENT(org.eclipse.jdt.core.dom.ASTNode.TEXT_ELEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.TEXT_ELEMENT)
			.getSimpleName()),

	THIS_EXPRESSION(org.eclipse.jdt.core.dom.ASTNode.THIS_EXPRESSION, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.THIS_EXPRESSION)
			.getSimpleName()),

	THROW_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.THROW_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.THROW_STATEMENT)
			.getSimpleName()),

	TRY_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.TRY_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.TRY_STATEMENT)
			.getSimpleName()),

	TYPE_DECLARATION(org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION)
					.getSimpleName()),

	TYPE_DECLARATION_STATEMENT(
			org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.TYPE_DECLARATION_STATEMENT)
					.getSimpleName()),

	TYPE_LITERAL(org.eclipse.jdt.core.dom.ASTNode.TYPE_LITERAL, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.TYPE_LITERAL)
			.getSimpleName()),

	TYPE_PARAMETER(org.eclipse.jdt.core.dom.ASTNode.TYPE_PARAMETER, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.TYPE_PARAMETER)
			.getSimpleName()),

	VARIABLE_DECLARATION_EXPRESSION(
			org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_EXPRESSION,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_EXPRESSION)
					.getSimpleName()),

	VARIABLE_DECLARATION_FRAGMENT(
			org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_FRAGMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_FRAGMENT)
					.getSimpleName()),

	VARIABLE_DECLARATION_STATEMENT(
			org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_STATEMENT,
			ASTNode.nodeClassForType(
					org.eclipse.jdt.core.dom.ASTNode.VARIABLE_DECLARATION_STATEMENT)
					.getSimpleName()),

	WHILE_STATEMENT(org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT)
			.getSimpleName()),

	WILDCARD_TYPE(org.eclipse.jdt.core.dom.ASTNode.WILDCARD_TYPE, ASTNode
			.nodeClassForType(org.eclipse.jdt.core.dom.ASTNode.WILDCARD_TYPE)
			.getSimpleName()), ;

	/**
	 * JDTのASTノードに定義されている，各ノードに割り振られた整数値
	 */
	private final int jdtOrdinal;

	/**
	 * 文字列表記 (JDTにおけるクラス名に準拠)
	 */
	private final String className;

	/**
	 * この列挙型の要素をすべてつなげた配列
	 */
	private static NodeType[] values = null;

	private NodeType(final int jdtOrdinal, final String className) {
		this.jdtOrdinal = jdtOrdinal;
		this.className = className;
	}

	public final int getJdtOrdinal() {
		return this.jdtOrdinal;
	}

	public final String getClassName() {
		return className;
	}

	public static final NodeType getNodeType(final String str) {
		if (values == null) {
			// NodeTypes.values() の呼び出しを一度だけに抑えるための処置
			values = NodeType.values();
		}

		for (final NodeType currentType : values) {
			if (str.equalsIgnoreCase(currentType.name())) {
				return currentType;
			}
			if (str.equalsIgnoreCase(currentType.getClassName())) {
				return currentType;
			}
		}

		return null;
	}

	public static final NodeType getNodeType(final int ordinal) {
		if (values == null) {
			// NodeTypes.values() の呼び出しを一度だけに抑えるための処置
			values = NodeType.values();
		}

		for (final NodeType currentType : values) {
			if (ordinal == currentType.getJdtOrdinal()) {
				return currentType;
			}
		}

		return null;
	}

	public static final NodeType getNodeTypeWithFileName(final String fileName) {
		if (values == null) {
			// NodeTypes.values() の呼び出しを一度だけに抑えるための処置
			values = NodeType.values();
		}

		final String prefix = "-";
		final String suffix = ".arff";

		for (final NodeType currentType : values) {
			if (fileName.contains(prefix + currentType.name() + suffix)) {
				return currentType;
			}
			if (fileName.contains(prefix + currentType.getClassName() + suffix)) {
				return currentType;
			}
			if (fileName.contains(prefix + currentType.name().toLowerCase()
					+ suffix)) {
				return currentType;
			}
			if (fileName.contains(prefix
					+ currentType.getClassName().toLowerCase() + suffix)) {
				return currentType;
			}
			if (fileName.contains(prefix + currentType.name().toUpperCase()
					+ suffix)) {
				return currentType;
			}
			if (fileName.contains(prefix
					+ currentType.getClassName().toUpperCase() + suffix)) {
				return currentType;
			}
		}

		return null;
	}

}
