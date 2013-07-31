package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CRD を表すクラス
 * 
 * @author k-hotta
 * 
 */
public class CRD {

	/**
	 * CRDElement のリスト
	 */
	private final List<CRDElement> elements;

	/**
	 * このCRDの文字列表記
	 */
	private final String str;
	
	private static final String DIVIDER = "/";

	public CRD(final List<CRDElement> elements) {
		this.elements = new ArrayList<CRDElement>();
		this.elements.addAll(elements);

		final StringBuilder builder = new StringBuilder();
		for (final CRDElement element : elements) {
			builder.append(element.toString()
					+ DIVIDER);
		}
		this.str = builder.toString();
	}

	public final List<CRDElement> getElements() {
		return Collections.unmodifiableList(elements);
	}

	public final String toString() {
		return str;
	}

}
