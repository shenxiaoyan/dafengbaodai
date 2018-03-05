package com.liyang.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.Arrays;

/**
 * @author Administrator
 *
 */
public class Base64_url {
	static byte base64_table_url[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '*', '-', '\0' };

	static byte base64_pad_url = '_';

	static short base64_reverse_table_url[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1,
			63, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

	public static int unsignedToBytes(int b) {
		return b & 0xFF;
	}

	// int base64_encode_url(const unsigned char *in_str, int length, char
	// *out_str,int *ret_length)
	public static byte[] base64EncodeUrl(byte[] inStr) {
		byte[] outStr = new byte[1024];

		int outCurrent = 0;
		int current = 0;
		int length = inStr.length;

		while (length > 2) { /* keep going until we have less than 24 bits */

			outStr[outCurrent++] = base64_table_url[unsignedToBytes((unsignedToBytes(inStr[current]) >>> 2))];
			outStr[outCurrent++] = base64_table_url[unsignedToBytes(
					unsignedToBytes(unsignedToBytes(inStr[current]) & 0x03) << 4)
					+ unsignedToBytes((unsignedToBytes(inStr[current + 1]) >>> 4))];
			outStr[outCurrent++] = base64_table_url[(unsignedToBytes(
					(unsignedToBytes(inStr[current + 1]) & 0x0f)) << 2)
					+ unsignedToBytes((unsignedToBytes(inStr[current + 2]) >>> 6))];
			outStr[outCurrent++] = base64_table_url[unsignedToBytes((unsignedToBytes(inStr[current + 2]) & 0x3f))];
			current += 3;
			length -= 3; /* we just handle 3 octets of data */
		}

		/* now deal with the tail end of things */
		if (length != 0) {
			outStr[outCurrent++] = base64_table_url[unsignedToBytes(inStr[current]) >>> 2];
			if (length > 1) {
				outStr[outCurrent++] = base64_table_url[unsignedToBytes(
						(unsignedToBytes(inStr[current]) & 0x03) << 4)
						+ unsignedToBytes(unsignedToBytes(inStr[current + 1]) >>> 4)];
				outStr[outCurrent++] = base64_table_url[unsignedToBytes(
						(unsignedToBytes(inStr[current + 1]) & 0x0f) << 2)];
				outStr[outCurrent++] = base64_pad_url;
			} else {
				outStr[outCurrent++] = base64_table_url[unsignedToBytes(
						(unsignedToBytes(inStr[current]) & 0x03) << 4)];
				outStr[outCurrent++] = base64_pad_url;
				outStr[outCurrent++] = base64_pad_url;
			}
		}

		// System.out.println("length in base64EncodeUrl: " + out_current );
		byte[] outBytes = new String(outStr).getBytes();
		return Arrays.copyOfRange(outBytes, 0, outCurrent);
	}

	// int base64_decode_url(const unsigned char *in_str, int length, char
	// *out_str, int *ret_length)
	public static byte[] base64DecodeUrl(byte[] inStr) {
		// const unsigned char *current = in_str;
		int ch, i = 0, j = 0, k;

		int current = 0;
		int result = 0;
		byte[] outStr = new byte[1024];
		int length = inStr.length;
		/* this sucks for threaded environments */

		/* run through the whole string, converting as we go */
		// while ((ch = in_str[current++]) != '\0' && length-- > 0) {
		ch = inStr[0];
		while (length-- > 0) {
			ch = inStr[current++];
			if (ch == base64_pad_url) {
				break;
			}
			/*
			 * When Base64 gets POSTed, all pluses are interpreted as spaces.
			 * This line changes them back. It's not exactly the Base64 spec,
			 * but it is completely compatible with it (the spec says that
			 * spaces are invalid). This will also save many people considerable
			 * headache. - Turadg Aleahmad <turadg@wise.berkeley.edu>
			 */
			if (ch == ' ') {
				ch = '*'; // never using '+'
			}

			ch = base64_reverse_table_url[ch];
			if (ch < 0) {
				continue;
			}

			switch (i % 4) {
			case 0:
				outStr[j] = (byte) unsignedToBytes(unsignedToBytes(ch) << 2);
				break;
			case 1:
				outStr[j++] |= (byte) unsignedToBytes(unsignedToBytes(ch) >>> 4);
				outStr[j] = (byte) unsignedToBytes(unsignedToBytes(unsignedToBytes(ch) & 0x0f) << 4);
				break;
			case 2:
				outStr[j++] |= (byte) unsignedToBytes(unsignedToBytes(ch) >>> 2);
				outStr[j] = (byte) unsignedToBytes(unsignedToBytes(unsignedToBytes(ch) & 0x03) << 6);
				break;
			case 3:
				outStr[j++] |= (byte) unsignedToBytes(ch);
				break;
			default:
				break;
			}
			i++;
		}
		k = j;
		/* mop things up if we ended on a boundary */
		if (ch == base64_pad_url) {
			switch (i % 4) {
			case 0:
			case 1:
				byte[] error = new byte[1];
				error[0] = '\0';
				return error;
			case 2:
				k++;
			case 3:
				outStr[k++] = 0;
			default:
				break;
			}
		}
		return Arrays.copyOfRange(outStr, 0, j);
	}

}
