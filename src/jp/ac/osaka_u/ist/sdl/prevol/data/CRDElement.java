package jp.ac.osaka_u.ist.sdl.prevol.data;

/**
 * CRDの構成要素を表すクラス．CRDの一行に相当する．
 */
public class CRDElement {

	/**
	 * ブロック情報を表す文字列
	 */
	private final String blockStr;
	
	/**
	 * CM (サイクロマチック数 + fan-out数)
	 */
	private final int cm;
	
	public CRDElement(final String blockStr, final int cm) {
		this.blockStr = blockStr;
		this.cm = cm;
	}
	
	public final String getStr() {
		return this.blockStr;
	}
	
	public final int getCm() {
		return this.cm;
	}
	
	@Override
	public String toString() {
		return this.blockStr + "," + this.cm;
	}
	
}
