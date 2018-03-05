package com.liyang.util;

import java.util.Random;

/**
 * 邀请码工具类
 * @author Adam
 *
 */
public class InviteCodeUtil {

	static final String ALPHA[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7",
			"8", "9" };
	
	public static String genetateInviteCode(){
		StringBuffer result = new StringBuffer() ;
		for(int i = 0 ; i<6 ; i++){
			result.append(randomChar());
		}
		return result.toString();
	}
	
	public static String randomChar(){
		Random random = new Random();
		int i = random.nextInt(61);
		return ALPHA[i];
	}
}
