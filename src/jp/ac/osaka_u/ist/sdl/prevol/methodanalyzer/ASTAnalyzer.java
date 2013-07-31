package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import jp.ac.osaka_u.ist.sdl.prevol.data.CRD;
import jp.ac.osaka_u.ist.sdl.prevol.data.CRDElement;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.CRDElementCalculator;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.CatchClauseStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.DoStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.ElseStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.EnhancedForStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.FinallyBlockStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.ForStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.IfStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.MethodDeclarationStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.SwitchStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.SynchronizedStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.TryStatementStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.TypeDeclarationStringBuilder;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd.WhileStatementStringBuilder;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

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
	 * CRDElement を算出するためのインスタンス
	 */
	private final CRDElementCalculator crdElementCalculator;

	/**
	 * finally節を一時保管するためのマップ
	 */
	private final Map<TryStatement, Block> optionalFinallyBlocks;

	/**
	 * else節を一時保管するためのマップ
	 */
	private final Map<IfStatement, Block> optionalElseBlocks;

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
		this.crdElementCalculator = new CRDElementCalculator();
		this.optionalFinallyBlocks = new HashMap<TryStatement, Block>();
		this.optionalElseBlocks = new HashMap<IfStatement, Block>();
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
	 * クラス or インターフェースの宣言に到達したとき
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		// インターフェースの場合は何もせず，子ノードの探索もしない
		if (node.isInterface()) {
			return false;
		}

		// CRDElement を算出し、スタックにプッシュ
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new TypeDeclarationStringBuilder(node));
		parentCrdElements.push(crdElement);

		// 子ノード以下の探索
		return true;
	}

	/**
	 * クラス or インターフェースの宣言から出るとき
	 */
	@Override
	public void endVisit(TypeDeclaration node) {
		// インターフェースの場合は何もしない
		if (node.isInterface()) {
			return;
		}

		// スタックからポップ
		// ポップした要素はいらない
		parentCrdElements.pop();
	}

	/**
	 * メソッド宣言に到達したとき
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		// メソッド名と行番号を特定
		final String methodName = node.getName().toString();
		final int startLine = root.getLineNumber(node.getStartPosition());
		final int endLine = root.getLineNumber(node.getStartPosition()
				+ node.getLength());

		// ベクトルデータの算出
		final NodeTypeCounter counter = new NodeTypeCounter();
		node.accept(counter);

		final VectorData vectorData = counter.getVectorData();

		// CRDElement の算出
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new MethodDeclarationStringBuilder(node));
		parentCrdElements.push(crdElement);

		// CRD の算出
		final CRD crd = new CRD(parentCrdElements);

		// メソッド情報をリストに登録
		final MethodData methodData = new MethodData(revision, fileName,
				methodName, startLine, endLine, vectorData, crd);
		this.methods.add(methodData);

		// 子ノードも探索するので true
		// MethodDeclaration の中に MethodDeclaration が存在し得るから
		return true;
	}

	/**
	 * メソッド宣言から出るとき
	 */
	@Override
	public void endVisit(MethodDeclaration node) {
		// スタックからポップ
		parentCrdElements.pop();
	}

	/**
	 * for-each文に到達したとき
	 */
	@Override
	public boolean visit(EnhancedForStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new EnhancedForStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * for-each文から出るとき
	 */
	@Override
	public void endVisit(EnhancedForStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * for文に到達したとき
	 */
	@Override
	public boolean visit(ForStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new ForStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * for文から出るとき
	 */
	@Override
	public void endVisit(ForStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * while文に到達したとき
	 */
	@Override
	public boolean visit(WhileStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new WhileStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * while文から出るとき
	 */
	@Override
	public void endVisit(WhileStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * do-while文に到達したとき
	 */
	@Override
	public boolean visit(DoStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new DoStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * do-while文から出るとき
	 */
	@Override
	public void endVisit(DoStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * switch文に到達したとき
	 */
	@Override
	public boolean visit(SwitchStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new SwitchStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * switch文から出るとき
	 */
	@Override
	public void endVisit(SwitchStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * synchronized文に到達したとき
	 */
	@Override
	public boolean visit(SynchronizedStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new SynchronizedStatementStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * synchronized文から出るとき
	 */
	@Override
	public void endVisit(SynchronizedStatement node) {
		parentCrdElements.pop();
	}

	/**
	 * try文に到達したとき
	 */
	@Override
	public boolean visit(TryStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new TryStatementStringBuilder(node));
		parentCrdElements.push(crdElement);

		// finally節を持つ場合は，それを保持しておく
		final Block finallyBlock = node.getFinally();
		if (finallyBlock != null) {
			this.optionalFinallyBlocks.put(node, finallyBlock);
		}

		return true;
	}

	/**
	 * try文から出るとき
	 */
	@Override
	public void endVisit(TryStatement node) {
		parentCrdElements.pop();

		// finally 節を持つ場合は，保持してある情報を破棄
		if (this.optionalFinallyBlocks.containsKey(node)) {
			this.optionalFinallyBlocks.remove(node);
		}
	}

	/**
	 * catch節に到達したとき
	 */
	@Override
	public boolean visit(CatchClause node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new CatchClauseStringBuilder(node));
		parentCrdElements.push(crdElement);
		return true;
	}

	/**
	 * catch節から出るとき
	 */
	@Override
	public void endVisit(CatchClause node) {
		parentCrdElements.pop();
	}

	/**
	 * if文に到達したとき
	 */
	@Override
	public boolean visit(IfStatement node) {
		final CRDElement crdElement = crdElementCalculator.calc(node,
				new IfStatementStringBuilder(node));
		parentCrdElements.push(crdElement);

		// else節があり，かつそれがelse-if文でないときは，else節の情報を保持
		final Statement elseStatement = node.getElseStatement();
		if (elseStatement != null) {
			if (!(elseStatement instanceof IfStatement)) {
				final Block elseBlock = (Block) elseStatement;
				this.optionalElseBlocks.put(node, elseBlock);
			}
		}

		return true;
	}

	/**
	 * if文から出るとき
	 */
	@Override
	public void endVisit(IfStatement node) {
		parentCrdElements.pop();

		// このif文に付属するelse節が保持されている場合はそれを破棄
		if (this.optionalElseBlocks.containsKey(node)) {
			this.optionalElseBlocks.remove(node);
		}
	}

	/**
	 * Blockに到達したとき <br>
	 * finally節もしくはelse節の場合にのみ特別対応が必要
	 */
	@Override
	public boolean visit(Block node) {
		// finally 節
		if (this.optionalFinallyBlocks.containsValue(node)) {
			final CRDElement crdElement = crdElementCalculator.calc(node,
					new FinallyBlockStringBuilder(node));
			parentCrdElements.push(crdElement);
		}

		// else 節
		else if (this.optionalElseBlocks.containsValue(node)) {
			final CRDElement crdElement = crdElementCalculator.calc(node,
					new ElseStatementStringBuilder(node));
			parentCrdElements.push(crdElement);
		}

		return true;
	}

	/**
	 * Blockから出るとき
	 */
	@Override
	public void endVisit(Block node) {
		// finally 節
		if (this.optionalFinallyBlocks.containsValue(node)) {
			parentCrdElements.pop();
		}

		// else 節
		else if (this.optionalElseBlocks.containsValue(node)) {
			parentCrdElements.pop();
		}
	}
}