package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

/**
 * 出力ファイル形式
 * 
 * @author k-hotta
 * 
 */
public enum OutputFileFormat {

	ARFF(".arff"), CSV(".csvF");

	private final String suffix;

	private OutputFileFormat(final String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public int getSuffixLength() {
		return suffix.length();
	}

}
