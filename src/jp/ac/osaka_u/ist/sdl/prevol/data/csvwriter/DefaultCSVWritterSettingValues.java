package jp.ac.osaka_u.ist.sdl.prevol.data.csvwriter;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

/**
 * CSVWriter のデフォルト設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
interface DefaultCSVWritterSettingValues {

	static String DEFAULT_QUERY = "select * from VECTOR_LINK";
	
	static final MessagePrinterMode DEFAULT_PRINT_MODE = MessagePrinterMode.VERBOSE;

}
