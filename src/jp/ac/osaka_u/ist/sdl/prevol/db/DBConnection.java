package jp.ac.osaka_u.ist.sdl.prevol.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jp.ac.osaka_u.ist.sdl.prevol.db.registerer.FileDataRegisterer;
import jp.ac.osaka_u.ist.sdl.prevol.db.registerer.MethodDataRegisterer;
import jp.ac.osaka_u.ist.sdl.prevol.db.registerer.RevisionDataRegisterer;
import jp.ac.osaka_u.ist.sdl.prevol.db.registerer.VectorDataRegisterer;
import jp.ac.osaka_u.ist.sdl.prevol.db.retriever.FileDataRetriever;
import jp.ac.osaka_u.ist.sdl.prevol.db.retriever.MethodDataRetriever;
import jp.ac.osaka_u.ist.sdl.prevol.db.retriever.RevisionDataRetriever;
import jp.ac.osaka_u.ist.sdl.prevol.db.retriever.VectorDataRetriever;

/**
 * DBとの接続を管理するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class DBConnection {

	private static DBConnection SINGLETON = null;

	private Connection connection;
	
	private RevisionDataRegisterer revisionRegisterer;

	private FileDataRegisterer fileRegisterer;
	
	private MethodDataRegisterer methodRegisterer;
	
	private VectorDataRegisterer vectorRegisterer;
	
	private RevisionDataRetriever revisionRetriever;
	
	private FileDataRetriever fileRetriever;
	
	private MethodDataRetriever methodRetriever;
	
	private VectorDataRetriever vectorRetriever;
	
	private DBConnection(final String dbPath) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:"
					+ dbPath);
			this.connection.setAutoCommit(false);
			this.revisionRegisterer = new RevisionDataRegisterer(this);
			this.fileRegisterer = new FileDataRegisterer(this);
			this.methodRegisterer = new MethodDataRegisterer(this);
			this.vectorRegisterer = new VectorDataRegisterer(this);
			this.revisionRetriever = new RevisionDataRetriever(this);
			this.fileRetriever = new FileDataRetriever(this);
			this.methodRetriever = new MethodDataRetriever(this);
			this.vectorRetriever = new VectorDataRetriever(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * インスタンスを生成
	 * 
	 * @param dbPath
	 */
	public static void createInstance(final String dbPath) {
		if (SINGLETON == null) {
			SINGLETON = new DBConnection(dbPath);
		}
	}

	/**
	 * インスタンスを取得
	 * 
	 * @return
	 */
	public static DBConnection getInstance() {
		return SINGLETON;
	}

	public RevisionDataRegisterer getRevisionRegisterer() {
		return revisionRegisterer;
	}

	public FileDataRegisterer getFileRegisterer() {
		return fileRegisterer;
	}

	public MethodDataRegisterer getMethodRegisterer() {
		return methodRegisterer;
	}

	public VectorDataRegisterer getVectorRegisterer() {
		return vectorRegisterer;
	}

	public RevisionDataRetriever getRevisionRetriever() {
		return revisionRetriever;
	}

	public FileDataRetriever getFileRetriever() {
		return fileRetriever;
	}

	public MethodDataRetriever getMethodRetriever() {
		return methodRetriever;
	}

	public VectorDataRetriever getVectorRetriever() {
		return vectorRetriever;
	}

	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement createStatement() {
		Statement result = null;
		try {
			result = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public PreparedStatement createPreparedStatement(String queue) {
		PreparedStatement result = null;
		try {
			result = connection.prepareStatement(queue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public synchronized void commit() {
		try {
			connection.commit();
		} catch (SQLException e1) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
	}

	public void setAutoCommit(boolean autoCommit) {
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
