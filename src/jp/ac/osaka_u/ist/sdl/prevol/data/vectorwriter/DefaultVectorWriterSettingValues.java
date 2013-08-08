package jp.ac.osaka_u.ist.sdl.prevol.data.vectorwriter;

import static org.eclipse.jdt.core.dom.ASTNode.*;

import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode;

/**
 * CSVWriter のデフォルト設定を保持するクラス
 * 
 * @author k-hotta
 * 
 */
interface DefaultVectorWriterSettingValues {

	static String DEFAULT_QUERY = null;

	static long DEFAULT_START_REVISION = 1;

	static long DEFAULT_END_REVISION = Long.MAX_VALUE;

	static final MessagePrinterMode DEFAULT_PRINT_MODE = MessagePrinterMode.VERBOSE;

	static final int[] DEFAULT_IGNORE_LIST = new int[] { JAVADOC, LINE_COMMENT,
			BLOCK_COMMENT };

}
