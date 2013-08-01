package jp.ac.osaka_u.ist.sdl.prevol.data;

/**
 * データベースに登録する要素を表す抽象クラス
 * 
 * @author k-hotta
 * 
 */
public abstract class AbstractElement implements Comparable<AbstractElement> {

	/**
	 * 識別用ID
	 */
	protected long id;

	public AbstractElement(final long id) {
		this.id = id;
	}

	public final long getId() {
		return id;
	}

	public int compareTo(final AbstractElement anotherElement) {
		return ((Long) id).compareTo(anotherElement.getId());
	}

}
