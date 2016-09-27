package com.delhel.dorman.uachiman.Printer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * Comandos de impresion con ESP/POS
 * 
 * @see https 
 *      ://code.google.com/p/openbravoposru/source/browse/src-pos/com/openbravo
 *      /pos/printer/escpos/ESCPOS.java?name=0.3.2&r=09d74e0d
 *      6eeb59fc47b8be87b3fecf4a75ade314
 * 
 */
public class ESCPos {

	public Boolean debug = false; // debug switch

	public final static String VERSION = "##version##";
	public static final byte[] INIT = { 0x1B, 0x40 };

	public static final byte[] SELECT_PRINTER = { 0x1B, 0x3D, 0x01 };
	public static final byte[] SELECT_DISPLAY = { 0x1B, 0x3D, 0x02 };

	// public static final byte[] LF = {0x0A}; // Print and line feed
	// public static final byte[] CR = {0x0D}; // Print and carriage return
	public static final byte[] HT = { 0x09 }; // Horizontal Tab
	public static final byte[] FF = { 0x0C }; //

	public static final byte[] CHAR_FONT_0 = { 0x1B, 0x4D, 0x00 };
	public static final byte[] CHAR_FONT_1 = { 0x1B, 0x4D, 0x01 };
	public static final byte[] CHAR_FONT_2 = { 0x1B, 0x4D, 0x30 };
	public static final byte[] CHAR_FONT_3 = { 0x1B, 0x4D, 0x31 };

	public static final byte[] BAR_HEIGHT = { 0x1D, 0x68, (byte) 0x60 };
	public static final byte[] BAR_POSITIONDOWN = { 0x1D, 0x48, 0x02 };
	public static final byte[] BAR_POSITIONUP = { 0x1D, 0x48, 0x01 };
	public static final byte[] BAR_POSITIONNONE = { 0x1D, 0x48, 0x00 };
	public static final byte[] BAR_HRIFONT1 = { 0x1D, 0x66, 0x01 };

	public static final byte[] BAR_EAN13 = { 0x1D, 0x6B, 0x02 }; // 12 numbers
	public static final byte[] BAR_EAN8 = { 0x1D, 0x6B, 0x03 }; // 7 numbers
	public static final byte[] BAR_CODE39 = { 0x1D, 0x6B, 0x04 }; // numbers and upper latin symbols
	public static final byte[] BAR_CODE128 = { 0x1D, 0x6B, 0x49 }; // 128 numbers and latin symbols

	public static final byte[] BAR_CODE_CODE128_A = { 0x7B, 0x41 }; // SET A
	public static final byte[] BAR_CODE_CODE128_B = { 0x7B, 0x42 }; // SET B
	public static final byte[] BAR_CODE_CODE128_C = { 0x7B, 0x43 }; // SET C

	public static final byte[] VISOR_HIDE_CURSOR = { 0x1F, 0x43, 0x00 };
	public static final byte[] VISOR_SHOW_CURSOR = { 0x1F, 0x43, 0x01 };
	public static final byte[] VISOR_HOME = { 0x0B };
	public static final byte[] VISOR_CLEAR = { 0x0C };

	public static final byte[] CANCEL_USER_CHAR = { 0x1B, 0x25, 0x00 };
	public static final byte[] SELECT_USER_CHAR = { 0x1B, 0x25, 0x01 };

	public static final byte[] CODE_TABLE_00 = { 0x1B, 0x74, 0x00 };
	public static final byte[] CODE_TABLE_13 = { 0x1B, 0x74, 0x13 };

	public static final byte[] CODE_TABLE_7 = { 0x1B, 0x74, 0x07 };
	public static final byte[] CODE_TABLE_17 = { 0x1B, 0x74, 0x17 };

	public static final byte[] CODE_TABLE_RUS = { 0x1B, 0x63, 0x52 };

	public static final byte[] CODE_TABLE_KANJI_RUS = { 0x1C, 0x2E, 0x1B, 0x74,
			0x11 };

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 * 
	 * @example Hello
	 * @param theParent
	 */
	public ESCPos() {
		welcome();
	}

	/**
	 * Select code character table
	 * 
	 * 0 PC437 (USA: Standard Europe) 1 Katakana 2 PC850(Multilingual) 3
	 * PC860(Portuguese) 4 PC863(Canadian-French) 5 PC865(Nordic) 16 WPC1252 17
	 * PC866 (Cyrillic #2) 18 PC852 (Latin2) 19 PC858 255 Blank page
	 * 
	 * Initial Value n = 0
	 * 
	 * @param charset_id
	 * @return
	 */
	public byte[] setCharset(int charset_id) {
		char[] command = new char[] { 0x1B, 't', (char) charset_id };
		return new String(command).getBytes();
	}

	/**
	 * Format text in 2 columns same row, left part and right part
	 * 
	 * @param text_left
	 * @param text_right
	 * @return
	 */
	public String formatTextLikeLine(String text_left, String text_right) {
		return String.format("%1$-22s %2$-10s ", text_left, text_right);
	}

	/**
	 * Message the welcome
	 */
	private void welcome() {
		System.out.println("\n##name## ##version## by ##author##!\n");
	}

	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	/**
	 * Prints n lines of blank paper.
	 * */
	public byte[] feed(int feed) {
		// escInit();
		char[] command = new char[] { 0x1B, 'd', (char) feed };
		return new String(command).getBytes();
	}

	/**
	 * Sets bold / emphasized ESC E n
	 * 
	 * Work!!!
	 * 
	 * @param val
	 *            When the LSB of n is 0, emphasized mode is turned off. When
	 *            the LSB of n is 1, emphasized mode is turned on.
	 * @return
	 * */
	public byte[] setBold(boolean bool) {
		char[] command = new char[] { 0x1B, 'E', (char) (bool ? 1 : 0) };
		return new String(command).getBytes();
	}

	/**
	 * Sets bold / emphasized ESC E = on ESC F = off
	 * 
	 * Work!!!
	 * 
	 * @param bool
	 * @return
	 */
	public byte[] setBold2(boolean bool) {
		char[] command = new char[] { 0x1B, (char) (bool ? 'E' : 'F') };
		return new String(command).getBytes();
	}

	/**
	 * 
	 * @param val
	 *            n = 0 normal, n = 1 outline, n = 2 shadow, n = 3 outline &
	 *            shadow
	 * @return
	 */
	public byte[] setChararterStyle(int val) {
		char[] command = new char[] { 0x1B, 'q', (char) val };
		return new String(command).getBytes();
	}

	/**
	 * Sets white on black printing
	 * 
	 * */
	public byte[] setInverse(boolean bool) {
		char[] command = new char[] { 0x1D, 'B', (bool ? '1' : '0') };
		return new String(command).getBytes();
	}

	/**
	 * Sets underline and weight
	 * 
	 * @param val
	 *            0 = no underline. 1 = single weight underline. 2 = double
	 *            weight underline.
	 * */

	public byte[] setUnderline(char val) {
		char[] command = new char[] { 0x1B, 0x2D, val };
		return new String(command).getBytes();

	}

	/**
	 * Sets left, center, right justification
	 * 
	 * @param val
	 *            0 = left justify. 1 = center justify. 2 = right justify.
	 * */
	public byte[] setJustification(int val) {
		char[] command = new char[] { 0x1B, 'a', (char) val };
		return new String(command).getBytes();
	}

	/**
	 * 
	 * @param val
	 *            0 is small-font => 7 large-font
	 * @return
	 */
	public byte[] setCharacterSize(int val) {
		char[] command = new char[] { 0x1D, '!', (char) val };
		return new String(command).getBytes();
	}

	/**
	 * 
	 * @param val
	 *            {0,1,2,4,8,16,32,64,128} 0 pica 1 elite 2 proportional 4
	 *            condensed 8 emphanized 16 double strike 32 double wide 64
	 *            Italic 128 underline
	 * 
	 * @return
	 */
	public byte[] setChararterStyle2(int val) {
		char[] command = new char[] { 0x1B, 0x21, (char) val };
		return new String(command).getBytes();
	}

	/**
	 * 
	 * @param val
	 *            0 = Character font A (12 | 24) selected. 1 = Character font B
	 *            (9 | 17) selected.
	 * @return
	 */
	public byte[] setCharacterFont(int val) {
		char[] command = new char[] { 0x1B, 'M', (char) val };
		return new String(command).getBytes();
	}

	/**
	 * The default tab positions are at intervals of 8 characters (columns 9,
	 * 17, 25,...). font A (12 x 24)
	 * 
	 * @param val
	 * @return
	 */
	public byte[] setHorizontalTab(int val) {
		char[] command = new char[] { 0x1B, 'e', 0x00, (char) val };
		return new String(command).getBytes();
	}

	/**
	 * Horizontal tab
	 * 
	 * @param val
	 * @return
	 */
	public byte[] setHorizontalTab2(int val) {
		char[] command = new char[] { 0x1B, 'f', 0x00, (char) val };
		return new String(command).getBytes();
	}

	/**
	 * Horizontal tab Moves the print position to the next horizontal tab
	 * position.
	 * 
	 * @return
	 */
	public byte[] horizontalTab() {
		return new byte[] { 0x09 };
	}

	/**
	 * Print text
	 * 
	 * @param txt
	 *            String to print
	 */
	public byte[] printString(String str) {
		// escInit();
		byte[] send;
		if (str.length() > 0) {
			send = str.getBytes();
		} else {
			send = new byte[] { 0xA };
		}
		return send;
	}

	/**
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public byte[] printString(String str, String charset) {
		// escInit();
		byte[] send;
		if (str.length() > 0) {
			try {
				send = str.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				send = str.getBytes();
			}
		} else {
			send = new byte[] { 0xA };
		}

		return send;
	}

	/**
	 * Sets the character spacing for the right side of the character to [ n
	 * horizontal or vertical motion units].
	 * 
	 * @param val
	 *            0 <= n <= 255
	 * @return
	 */
	public byte[] SetRightSideCharacterSpacing(int val) {
		char[] command = new char[] { 0x1B, 0x20, (char) val };
		return new String(command).getBytes();
	}

	/**
	 * ESC @ reusable init esc code Initialize printer Clears the data in the
	 * print buffer and resets the printer mode to the mode that was in effect
	 * when the power was turned on.
	 * 
	 */
	public byte[] escInit() {
		char[] command = new char[] { 0x1B, '@' };
		return new String(command).getBytes();
	}

	/**
	 * GS L nL nH (nL+ nH | 256) = 0 (nL= 0, nH= 0) This command is effective
	 * only processed at t he beginning of the line.
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public byte[] setLeftMargin(int nL, int nH) {
		char[] command = new char[] { 0x1D, 'L', (char) nL, (char) nH };
		return new String(command).getBytes();
	}

	/**
	 * E S C d n Prints a string and outputs n lines of blank paper. Print and
	 * feed n lines
	 * 
	 * @param feed
	 *            0 <= n <= 255
	 */
	public byte[] printAndFeed(int feed) {
		char[] command = new char[] { 0x1B, 'd', (char) feed };
		return new String(command).getBytes();
	}

	/**
	 * Merge two array byte type
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public byte[] merge_array(byte[] a, byte[] b) {
		int aLen = a.length;
		int bLen = b.length;

		byte[] c = new byte[aLen + bLen];

		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, 0, bLen);

		return c;
	}

	/**
	 * Convert Object to byte primitive
	 * 
	 * @param bBytes
	 * @return
	 */
	public byte[] convertClasstoPrimitiveByte(Byte[] bBytes) {
		int size = bBytes.length;
		byte[] bbytes = new byte[size];
		int j = 0;
		for (Byte b : bBytes)
			bbytes[j++] = b;

		return bbytes;
	}

	/**
	 * resets all printer settings to default
	 * 
	 * @throws IOException
	 * 
	 */
	public void resetToDefault() throws IOException {
		setInverse(false);
		setBold(false);
		setUnderline('0');
		setJustification('0');
	}

	/**
	 * Print storage
	 * 
	 * @return
	 */
	public byte[] printStorage() {
		byte[] bytes = new byte[1];
		bytes[0] = 0xA;

		return bytes;
	}

	/**
	 * Store custom character input array of column bytes. NOT WORKING
	 * 
	 * @param spacing
	 *            Integer representing Vertical motion of unit in inches. 0-255
	 * @throws IOException
	 * 
	 */
	public byte[] setLineSpacing(int spacing) throws IOException {
		// function ESC 3
		Object[] obj = new Object[3];
		obj[0] = 0x1B;
		obj[1] = "3";
		obj[2] = spacing;

		return serializeObject(obj);
	}

	/**
	 * Cut
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] cut() throws IOException {
		Object[] obj = new Object[4];
		obj[0] = 0x1D;
		obj[1] = "V";
		obj[2] = 48;
		obj[3] = 0;

		return serializeObject(obj);
	}

	/**
	 * Feed and cut
	 * 
	 * @param feed
	 * @throws IOException
	 */
	public void feedAndCut(int feed) throws IOException {
		feed(feed);
		cut();
	}

	/**
	 * Beep
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] beep() throws IOException {
		Object[] obj = new Object[8];
		obj[0] = 0x1B;
		obj[1] = "(A";
		obj[2] = 4;
		obj[3] = 0;
		obj[4] = 48;
		obj[5] = 55;
		obj[6] = 3;
		obj[7] = 15;

		return serializeObject(obj);
	}

	/**
	 * Deserialize bytes
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserializeBytes(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bytesIn);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

	/**
	 * Serialize object
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Object obj) throws IOException {
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
		oos.writeObject(obj);
		oos.flush();
		byte[] bytes = bytesOut.toByteArray();
		bytesOut.close();
		oos.close();
		return bytes;
	}

	/**
	 * Encode and print QR code
	 * 
	 * @param str
	 *            String to be encoded in QR.
	 * @param moduleSize
	 *            The size of the QR module (pixel) in dots. The QR code will
	 *            not print if it is too big. Try setting this low and
	 *            experiment in making it larger.
	 * @param str
	 * @param errCorrect
	 * @param moduleSize
	 */
	public void printQR(String str, int errCorrect, int moduleSize) {
		/*
		 * // save data function 80 printer.write(0x1D);// init
		 * printer.write("(k");// adjust height of barcode
		 * printer.write(str.length() + 3); // pl printer.write(0); // ph
		 * printer.write(49); // cn printer.write(80); // fn printer.write(48);
		 * // printer.write(str);
		 * 
		 * // error correction function 69 printer.write(0x1D);
		 * printer.write("(k"); printer.write(3); // pl printer.write(0); // ph
		 * printer.write(49); // cn printer.write(69); // fn
		 * printer.write(errCorrect); // 48<= n <= 51
		 * 
		 * // size function 67 printer.write(0x1D); printer.write("(k");
		 * printer.write(3); printer.write(0); printer.write(49);
		 * printer.write(67); printer.write(moduleSize);// 1<= n <= 16
		 * 
		 * // print function 81 printer.write(0x1D); printer.write("(k");
		 * printer.write(3); // pl printer.write(0); // ph printer.write(49); //
		 * cn printer.write(81); // fn printer.write(48); // m
		 */
	}


}
