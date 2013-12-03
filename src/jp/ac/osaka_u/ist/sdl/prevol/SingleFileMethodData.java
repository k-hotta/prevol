package jp.ac.osaka_u.ist.sdl.prevol;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;

public class SingleFileMethodData {

	private final int start;

	private final int end;

	private final VectorData vector;

	private final String signature;

	public SingleFileMethodData(final int start, final int end,
			final VectorData vector, final String signature) {
		this.start = start;
		this.end = end;
		this.vector = vector;
		this.signature = signature;
	}

	public final int getStart() {
		return start;
	}

	public final int getEnd() {
		return end;
	}

	public final VectorData getVector() {
		return vector;
	}

	public final String getSignature() {
		return signature;
	}

}
