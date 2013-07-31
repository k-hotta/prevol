package jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.DataManagerManager;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.UnitManager;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.CatchBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.ClassInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.DoBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.ElseBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.EnhancedForBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.FinallyBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.ForBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.IfBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.MethodInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.SwitchBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.SynchronizedBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.TryBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.UnitInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.block.WhileBlockInfo;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.data.crd.CorroborationMetric;
import jp.ac.osaka_u.ist.sdl.c20r.rev_analyzer.settings.Settings;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

public class BlockDetector extends StringCreateVisitor {

	private static final int threshold = Settings.getIntsance().getThreshold();

	private final List<UnitInfo> detectedUnits;

	private final String ownerFileName;

	private final CompilationUnit root;

	private final UnitManager manager;

	private final long ownerRevisionId;

	private final long ownerFileId;

	private int cc = 0;

	private int fo = 0;

	private String normalizedDiscriminator;

	public BlockDetector(String ownerFile, CompilationUnit root,
			long ownerRevisionId, long ownerFileId) {
		this.detectedUnits = new LinkedList<UnitInfo>();
		this.ownerFileName = ownerFile;
		this.root = root;
		this.manager = DataManagerManager.getInstance().getUnitManager();
		this.ownerRevisionId = ownerRevisionId;
		this.ownerFileId = ownerFileId;
	}

	public List<UnitInfo> getDetectedUnits() {
		return detectedUnits;
	}

	/**
	 * サイクロマチック数を取得
	 * 
	 * @return
	 */
	public int getCC() {
		return cc;
	}

	/**
	 * fan-out の数を取得
	 * 
	 * @return
	 */
	public int getFO() {
		return fo;
	}

	/**
	 * 引数で受け取ったユニットを登録する
	 * 
	 * @param detectedUnit
	 *            登録したいユニット
	 * @param descendentUnits
	 *            登録したいユニットの子孫ユニットのリスト
	 */
	private void registDetectedUnit(UnitInfo detectedUnit,
			List<UnitInfo> descendentUnits, boolean isSatisfyThreshold) {
		for (UnitInfo descendentUnit : descendentUnits) {
			descendentUnit.addAncestorUnit(detectedUnit);
		}
		if (isSatisfyThreshold) {
			this.detectedUnits.add(detectedUnit);
		}
		getBuffer().append(detectedUnit.getReplaceStatement() + "\n");
		this.cc += detectedUnit.getCM().getCC() - 1;
		this.fo += detectedUnit.getCM().getFO();
	}

}
