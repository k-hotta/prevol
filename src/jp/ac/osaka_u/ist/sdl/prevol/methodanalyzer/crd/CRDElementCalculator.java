package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import jp.ac.osaka_u.ist.sdl.prevol.data.CRDElement;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * CRDElement を計算するクラス
 * 
 * @author k-hotta
 * 
 */
public class CRDElementCalculator {

	/**
	 * 与えられたノードと文字列生成器を用いて CRDElement を生成する
	 * 
	 * @param node
	 * @param strBuilder
	 * @return
	 */
	public final CRDElement calc(final ASTNode node,
			final AbstractBlockStringBuilder<?> strBuilder) {
		final MetricsCalculator metricsCalculator = new MetricsCalculator();
		node.accept(metricsCalculator);

		final int cm = metricsCalculator.getCC() + metricsCalculator.getFO();

		return new CRDElement(strBuilder.getBlockStr(), cm);
	}

}
