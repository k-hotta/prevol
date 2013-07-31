package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import jp.ac.osaka_u.ist.sdl.prevol.data.CRDElement;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * あるファイルのASTを解析し，各メソッドのベクトルデータを計算するクラス
 * 
 * @author k-hotta
 * 
 */
public class ASTAnalyzer extends ASTVisitor {

	/**
	 * 解析中のリビジョン番号
	 */
	private final long revision;

	/**
	 * 解析中のファイル名
	 */
	private final String fileName;

	/**
	 * 解析した結果得られたMethodDataのリスト
	 */
	private final List<MethodData> methods;

	/**
	 * ルートノード
	 */
	private CompilationUnit root = null;

	/**
	 * 着目中のメソッドの外側にあるすべてのブロックのCRDElementを保持するスタック
	 */
	private final Stack<CRDElement> parentCrdElements;

	/**
	 * コンストラクタ
	 * 
	 * @param revision
	 * @param fileName
	 */
	public ASTAnalyzer(final long revision, final String fileName) {
		this.revision = revision;
		this.fileName = fileName;
		this.methods = new ArrayList<MethodData>();
		this.parentCrdElements = new Stack<CRDElement>();
	}

	/**
	 * 解析結果を取得　(MethodDataのリスト)
	 * 
	 * @return
	 */
	public final List<MethodData> getMethods() {
		return Collections.unmodifiableList(methods);
	}

	/**
	 * CompilationUnit 到達時
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		// ルートノードを設定
		if (root == null) {
			this.root = node;
		}

		return true;
	}

	/**
	 * 宣言されているメソッドを解析する <br>
	 * (メソッド宣言ノード探索時に呼ばれる)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		final String methodName = node.getName().toString();
		final int startLine = root.getLineNumber(node.getStartPosition());
		final int endLine = root.getLineNumber(node.getStartPosition()
				+ node.getLength());

		// ベクトルデータの算出
		final NodeTypeCounter counter = new NodeTypeCounter();
		node.accept(counter);

		final VectorData vectorData = counter.getVectorData();

		// メソッド情報をリストに登録
		final MethodData methodData = new MethodData(revision, fileName,
				methodName, startLine, endLine, vectorData);
		this.methods.add(methodData);

		// 子ノードも探索するので true
		// MethodDeclaration の中に MethodDeclaration が存在し得るから
		return true;
	}

}