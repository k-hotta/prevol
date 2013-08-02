package jp.ac.osaka_u.ist.sdl.prevol.utils;

import static jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinterMode.*;

/**
 * メッセージ出力を管理するクラス
 * 
 * @author k-hotta
 * 
 */
public class MessagePrinter {

	/**
	 * 出力モード
	 */
	private static MessagePrinterMode mode;

	/**
	 * 出力モードを設定
	 * 
	 * @param newMode
	 */
	public static void setMode(final MessagePrinterMode newMode) {
		mode = newMode;
	}

	/**
	 * VERBOSEのときにのみ出力
	 * 
	 * @param str
	 */
	public static void println(final String str) {
		if (mode == VERBOSE) {
			System.out.println(str);
		}
	}

	/**
	 * VERBOSEのときにのみ出力
	 */
	public static void println() {
		if (mode == VERBOSE) {
			System.out.println();
		}
	}

	/**
	 * VERBOSEのときにのみ出力
	 * 
	 * @param str
	 */
	public static void print(final String str) {
		if (mode == VERBOSE) {
			System.out.print(str);
		}
	}

	/**
	 * NONE以外なら出力
	 * 
	 * @param str
	 */
	public static void stronglyPrintln(final String str) {
		if (mode != NONE) {
			System.out.println(str);
		}
	}

	/**
	 * NONE以外なら出力
	 */
	public static void stronglyPrintln() {
		if (mode != NONE) {
			System.out.println();
		}
	}

	/**
	 * NONE以外なら出力
	 * 
	 * @param str
	 */
	public static void stronglyPrint(final String str) {
		if (mode != NONE) {
			System.out.print(str);
		}
	}

}
