package jp.ac.osaka_u.ist.sdl.prevol.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * データベースファイルを構築するクラス
 * 
 * @author k-hotta
 * 
 */
public class DBMaker {

	public static void main(String[] args) {
		try {

			// コマンドライン引数を処理
			final Options options = new Options();

			{
				final Option d = new Option("d", "db", true, "database");
				d.setArgName("db");
				d.setArgs(1);
				d.setRequired(true);
				options.addOption(d);
			}

			final CommandLineParser parser = new PosixParser();
			final CommandLine cmd = parser.parse(options, args);

			// 出力先ファイル名
			final String dbFile = cmd.getOptionValue("d");

			// URLを導出
			final StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("jdbc:sqlite:");
			urlBuilder.append(dbFile);

			final String url = urlBuilder.toString();

			Class.forName("org.sqlite.JDBC");

			final Connection connection = DriverManager.getConnection(url);

			// リビジョンテーブルを作成
			{
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(getRevisionTableQuery());
				stmt.close();
			}

			// リビジョンテーブルにインデックスを作成
			{
				final Statement stmt = connection.createStatement();
				createIndexesOnRevisionTable(stmt);
				stmt.close();
			}

			// メソッドテーブルを作成
			{
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(getMethodTableQuery());
				stmt.close();
			}

			// メソッドテーブルにインデックスを作成
			{
				final Statement stmt = connection.createStatement();
				createIndexesOnMethodTable(stmt);
				stmt.close();
			}

			// ファイルテーブルを作成
			{
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(getFileTableQuery());
				stmt.close();
			}

			// ファイルテーブルにインデックスを作成
			{
				final Statement stmt = connection.createStatement();
				createIndexesOnFileTable(stmt);
				stmt.close();
			}

			// ベクトルテーブルを作成
			{
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(getVectorTableQuery());
				stmt.close();
			}

			// ベクトルテーブルにインデックスを作成
			{
				final Statement stmt = connection.createStatement();
				createIndexesOnVectorTable(stmt);
				stmt.close();
			}

			// 変更前後のベクトルのペアを表すテーブルを作成
			{
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(getVectorLinkTableQuery());
				stmt.close();
			}

			// 変更前後のベクトルのペアを表すテーブルにインデックスを作成
			{
				final Statement stmt = connection.createStatement();
				createIndexesOnVectorLinkTable(stmt);
				stmt.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getRevisionTableQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("create table REVISION(");
		builder.append("REVISION_ID LONG PRIMARY KEY,");
		builder.append("REVISION_NUM LONG UNIQUE");
		builder.append(")");

		return builder.toString();
	}

	private static void createIndexesOnRevisionTable(final Statement stmt)
			throws Exception {
		stmt.executeUpdate("create index REVISION_ID_INDEX_REVISION on REVISION(REVISION_ID)");
	}

	private static String getFileTableQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("create table FILE(");
		builder.append("FILE_ID LONG PRIMARY KEY,");
		builder.append("START_REVISION_ID LONG,");
		builder.append("END_REVISION_ID LONG,");
		builder.append("FILE_PATH TEXT");
		builder.append(")");

		return builder.toString();
	}

	private static void createIndexesOnFileTable(final Statement stmt)
			throws Exception {
		stmt.executeUpdate("create index FILE_ID_INDEX_FILE on FILE(FILE_ID)");
		stmt.executeUpdate("create index START_REVISION_ID_INDEX_FILE on FILE(START_REVISION_ID)");
		stmt.executeUpdate("create index END_REVISION_ID_INDEX_FILE on FILE(END_REVISION_ID)");
		stmt.executeUpdate("create index START_END_REVISION_ID_INDEX_FILE on FILE(START_REVISION_ID,END_REVISION_ID)");
	}

	private static String getMethodTableQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("create table METHOD(");
		builder.append("METHOD_ID LONG PRIMARY KEY,");
		builder.append("START_REVISION_ID LONG,");
		builder.append("END_REVISION_ID LONG,");
		builder.append("OWNER_FILE_ID LONG,");
		builder.append("METHOD_NAME TEXT NOT NULL,");
		builder.append("START_LINE INTEGER CHECK (START_LINE >= 0),");
		builder.append("END_LINE INTEGER CHECK (END_LINE >= 0),");
		builder.append("CRD TEXT NOT NULL,");
		builder.append("VECTOR_ID LONG,");
		builder.append("METHOD_HASH INTEGER,");
		builder.append("METHOD_PARAMETER TEXT NOT NULL");
		builder.append(")");

		return builder.toString();
	}

	private static void createIndexesOnMethodTable(final Statement stmt)
			throws Exception {
		stmt.executeUpdate("create index METHOD_ID_INDEX_METHOD on METHOD(METHOD_ID)");
		stmt.executeUpdate("create index START_REVISION_ID_INDEX_METHOD on METHOD(START_REVISION_ID)");
		stmt.executeUpdate("create index END_REVISION_ID_INDEX_METHOD on METHOD(END_REVISION_ID)");
		stmt.executeUpdate("create index OWNER_FILE_ID_INDEX_METHOD on METHOD(OWNER_FILE_ID)");
		stmt.executeUpdate("create index VECTOR_ID_INDEX_METHOD on METHOD(VECTOR_ID)");
		stmt.executeUpdate("create index METHOD_HASH_INDEX_METHOD on METHOD(METHOD_HASH)");
		stmt.executeUpdate("create index START_END_REVISION_ID_INDEX_METHOD on METHOD(START_REVISION_ID,END_REVISION_ID)");
	}

	private static String getVectorTableQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("create table VECTOR(");

		builder.append("VECTOR_ID LONG PRIMARY KEY,");
		builder.append("ANNOTATION_TYPE_DECLARATION INTEGER,");
		builder.append("ANNOTATION_TYPE_MEMBER_DECLARATION INTEGER,");
		builder.append("ANONYMOUS_CLASS_DECLARATION INTEGER,");
		builder.append("ARRAY_ACCESS INTEGER,");
		builder.append("ARRAY_CREATION INTEGER,");
		builder.append("ARRAY_INITIALIZER INTEGER,");
		builder.append("ARRAY_TYPE INTEGER,");
		builder.append("ASSERT_STATEMENT INTEGER,");
		builder.append("ASSIGNMENT INTEGER,");
		builder.append("BLOCK INTEGER,");
		builder.append("BLOCK_COMMENT INTEGER,");
		builder.append("BOOLEAN_LITERAL INTEGER,");
		builder.append("BREAK_STATEMENT INTEGER,");
		builder.append("CAST_EXPRESSION INTEGER,");
		builder.append("CATCH_CLAUSE INTEGER,");
		builder.append("CHARACTER_LITERAL INTEGER,");
		builder.append("CLASS_INSTANCE_CREATION INTEGER,");
		builder.append("COMPILATION_UNIT INTEGER,");
		builder.append("CONDITIONAL_EXPRESSION INTEGER,");
		builder.append("CONSTRUCTOR_INVOCATION INTEGER,");
		builder.append("CONTINUE_STATEMENT INTEGER,");
		builder.append("DO_STATEMENT INTEGER,");
		builder.append("EMPTY_STATEMENT INTEGER,");
		builder.append("ENHANCED_FOR_STATEMENT INTEGER,");
		builder.append("ENUM_CONSTANT_DECLARATION INTEGER,");
		builder.append("ENUM_DECLARATION INTEGER,");
		builder.append("EXPRESSION_STATEMENT INTEGER,");
		builder.append("FIELD_ACCESS INTEGER,");
		builder.append("FIELD_DECLARATION INTEGER,");
		builder.append("FOR_STATEMENT INTEGER,");
		builder.append("IF_STATEMENT INTEGER,");
		builder.append("IMPORT_DECLARATION INTEGER,");
		builder.append("INFIX_EXPRESSION INTEGER,");
		builder.append("INITIALIZER INTEGER,");
		builder.append("INSTANCEOF_EXPRESSION INTEGER,");
		builder.append("JAVADOC INTEGER,");
		builder.append("LABELED_STATEMENT INTEGER,");
		builder.append("LINE_COMMENT INTEGER,");
		builder.append("MARKER_ANNOTATION INTEGER,");
		builder.append("MEMBER_REF INTEGER,");
		builder.append("MEMBER_VALUE_PAIR INTEGER,");
		builder.append("METHOD_DECLARATION INTEGER,");
		builder.append("METHOD_INVOCATION INTEGER,");
		builder.append("METHOD_REF INTEGER,");
		builder.append("METHOD_REF_PARAMETER INTEGER,");
		builder.append("MODIFIER INTEGER,");
		builder.append("NORMAL_ANNOTATION INTEGER,");
		builder.append("NULL_LITERAL INTEGER,");
		builder.append("NUMBER_LITERAL INTEGER,");
		builder.append("PACKAGE_DECLARATION INTEGER,");
		builder.append("PARAMETERIZED_TYPE INTEGER,");
		builder.append("PARENTHESIZED_EXPRESSION INTEGER,");
		builder.append("POSTFIX_EXPRESSION INTEGER,");
		builder.append("PREFIX_EXPRESSION INTEGER,");
		builder.append("PRIMITIVE_TYPE INTEGER,");
		builder.append("QUALIFIED_NAME INTEGER,");
		builder.append("QUALIFIED_TYPE INTEGER,");
		builder.append("RETURN_STATEMENT INTEGER,");
		builder.append("SIMPLE_NAME INTEGER,");
		builder.append("SIMPLE_TYPE INTEGER,");
		builder.append("SINGLE_MEMBER_ANNOTATION INTEGER,");
		builder.append("SINGLE_VARIABLE_DECLARATION INTEGER,");
		builder.append("STRING_LITERAL INTEGER,");
		builder.append("SUPER_CONSTRUCTOR_INVOCATION INTEGER,");
		builder.append("SUPER_FIELD_ACCESS INTEGER,");
		builder.append("SUPER_METHOD_INVOCATION INTEGER,");
		builder.append("SWITCH_CASE INTEGER,");
		builder.append("SWITCH_STATEMENT INTEGER,");
		builder.append("SYNCHRONIZED_STATEMENT INTEGER,");
		builder.append("TAG_ELEMENT INTEGER,");
		builder.append("TEXT_ELEMENT INTEGER,");
		builder.append("THIS_EXPRESSION INTEGER,");
		builder.append("THROW_STATEMENT INTEGER,");
		builder.append("TRY_STATEMENT INTEGER,");
		builder.append("TYPE_DECLARATION INTEGER,");
		builder.append("TYPE_DECLARATION_STATEMENT INTEGER,");
		builder.append("TYPE_LITERAL INTEGER,");
		builder.append("TYPE_PARAMETER INTEGER,");
		builder.append("VARIABLE_DECLARATION_EXPRESSION INTEGER,");
		builder.append("VARIABLE_DECLARATION_FRAGMENT INTEGER,");
		builder.append("VARIABLE_DECLARATION_STATEMENT INTEGER,");
		builder.append("WHILE_STATEMENT INTEGER,");
		builder.append("WILDCARD_TYPE INTEGER");

		builder.append(")");

		return builder.toString();
	}

	private static void createIndexesOnVectorTable(final Statement stmt)
			throws Exception {
		stmt.executeUpdate("create index VECTOR_ID_INDEX_VECTOR on VECTOR(VECTOR_ID)");
	}

	private static String getVectorLinkTableQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("create table VECTOR_LINK(");
		builder.append("VECTOR_LINK_ID LONG PRIMARY KEY,");
		builder.append("BEFORE_REVISION_ID LONG,");
		builder.append("AFTER_REVISION_ID LONG,");
		builder.append("BEFORE_METHOD_ID LONG,");
		builder.append("AFTER_METHOD_ID LONG,");
		builder.append("BEFORE_VECTOR_ID LONG,");
		builder.append("AFTER_VECTOR_ID LONG");
		builder.append(")");

		return builder.toString();
	}

	private static void createIndexesOnVectorLinkTable(final Statement stmt)
			throws Exception {
		stmt.executeUpdate("create index VECTOR_LINK_ID_INDEX_VECTOR_LINK on VECTOR_LINK(VECTOR_LINK_ID)");
		stmt.executeUpdate("create index BEFORE_REVISION_ID_INDEX_VECTOR_LINK on VECTOR_LINK(BEFORE_REVISION_ID)");
		stmt.executeUpdate("create index AFTER_REVISION_ID_INDEX_VECTOR_LINK on VECTOR_LINK(AFTER_REVISION_ID)");
		stmt.executeUpdate("create index BEFORE_METHOD_ID_INDEX_VECTOR_LINK on VECTOR_LINK(BEFORE_METHOD_ID)");
		stmt.executeUpdate("create index AFTER_METHOD_ID_INDEX_VECTOR_LINK on VECTOR_LINK(AFTER_METHOD_ID)");
		stmt.executeUpdate("create index BEFORE_VECTOR_ID_INDEX_VECTOR_LINK on VECTOR_LINK(BEFORE_VECTOR_ID)");
		stmt.executeUpdate("create index AFTER_VECTOR_ID_INDEX_VECTOR_LINK on VECTOR_LINK(AFTER_VECTOR_ID)");
	}

}
