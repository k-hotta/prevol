package jp.ac.osaka_u.ist.sdl.prevol.setting;

/**
 * An enumeration to represent target languages
 * 
 * @author k-hotta
 * 
 */
public enum Language {

	JAVA("java", new String[] { ".java" });

	private final String str;

	private final String[] suffixes;

	private Language(final String str, final String[] suffixes) {
		this.str = str;
		this.suffixes = suffixes;
	}

	public final String getStr() {
		return str;
	}

	public static final Language getCorrespondingLanguage(final String str) {
		if (str.equalsIgnoreCase(JAVA.getStr())) {
			return JAVA;
		} else {
			return null;
		}
	}

	/**
	 * check whether the specified file is a target source file
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isTarget(final String fileName) {
		for (final String suffix : suffixes) {
			if (fileName.endsWith(suffix)) {
				return true;
			}
		}

		return false;
	}

}
