package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

/**
 * MethodAnalyzer のデフォルト設定を保持するインターフェース
 * 
 * @author k-hotta
 * 
 */
interface DefaultMethodAnalyzerSettingValues {

	static final Language DEFAULT_LANGUAGE = Language.JAVA;
	
	static final int DEFAULT_THREADS = 1;
	
	static final String DEFAULT_ADDITIONAL_PATH = null;
	
	static final String DEFAULT_USERNAME = null;
	
	static final String DEFAULT_PASSWD = null;
	
}
