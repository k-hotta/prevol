package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * ASTを探索し，メトリクス値(サイクロマチック数とfan-out数)を算出するクラス
 * 
 * @author k-hotta
 * 
 */
public class MetricsCalculator extends ASTVisitor {

	/**
	 * サイクロマチック数　＝　分岐の数+1
	 */
	private int cc;

	/**
	 * 呼び出されたメソッドの名前から算出したハッシュ値を格納するセット <br>
	 * これのサイズをfan-out数とみなす
	 */
	private final Set<Integer> invokedMethodNames;

	public MetricsCalculator() {
		this.cc = 0;
		this.invokedMethodNames = new HashSet<Integer>();
	}

	/**
	 * サイクロマチック数を取得
	 * 
	 * @return
	 */
	public final int getCC() {
		return cc;
	}

	/**
	 * fan-out数を取得
	 * 
	 * @return
	 */
	public final int getFO() {
		return invokedMethodNames.size();
	}

	/**
	 * メソッド呼び出しに到達した場合，呼び出されているメソッドの名前を保存
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		invokedMethodNames.add(node.getName().toString().hashCode());
		return true;
	}

	/*
	 * 以降，サイクロマチック数算出用のメソッド群
	 * 以下の文に到達した場合，分岐の数をインクリメントする
	 * if, for, for-each, while, do-while, switch-case
	 */

	@Override
	public boolean visit(IfStatement node) {
		this.cc++;
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		this.cc++;
		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		this.cc++;
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		this.cc++;
		return true;
	}

	@Override
	public boolean visit(DoStatement node) {
		this.cc++;
		return true;
	}

	@Override
	public boolean visit(SwitchCase node) {
		this.cc++;
		return true;
	}

}
