package com.liyang.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
public class AESAndURLUtils {
	static String algorithm="AES";
	static String transformation="AES";
	static String key="AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDD12345678";
	static String rng="SHA1PRNG";
	static String encoding="UTF-8";
	/**
	 * AES算法，解密
	 */
	public static String decodeValue(String data) throws Exception {
		return new String(decrypt(Base64.decodeBase64(data)),encoding);
	}
	
	/**
	 * AES算法，加密
	 */
	public static String encodeValue(String data) throws Exception {
	    return Base64.encodeBase64String(encrypt(data.getBytes(encoding)));
	  }
	
	 public static byte[] encrypt(byte[] src) throws Exception {
		    byte[] keybyte = getBytes(key);
		    SecureRandom secureRandom = SecureRandom.getInstance(rng);
		    secureRandom.setSeed(keybyte);
		    KeyGenerator kegen = KeyGenerator.getInstance(algorithm);
		    kegen.init(128, secureRandom);
		    SecretKey sk = kegen.generateKey();
		    Cipher cip = Cipher.getInstance(transformation);
		    cip.init(Cipher.ENCRYPT_MODE, sk);
		    return cip.doFinal(src);
		  }
	 
	 public static byte[] decrypt(byte[] src) throws Exception {
		    byte[] keybyte = getBytes(key);
		    SecureRandom secureRandom = SecureRandom.getInstance(rng);
		    secureRandom.setSeed(keybyte);
		    KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		    keygen.init(128, secureRandom);
		    SecretKey sk = keygen.generateKey();
		    Cipher cip = Cipher.getInstance(transformation);
		    cip.init(Cipher.DECRYPT_MODE, sk);
		    return cip.doFinal(src);
		  }
		  
	 public static byte[] getBytes(String str) {
		 	if (str == null || "".equals(str)) {
		 		return new byte[0];
		 	}
		 	byte[] ivBytes = new byte[str.length() / 2];
		 	Integer integer = 0;
		 	for (int i = 0; i < ivBytes.length; i++) {
		 		integer = Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		 		ivBytes[i] = integer.byteValue();
		 	}
		 	return ivBytes;
	 }
	 
	 /**
		 * URL解码,去处空格，换行符后数据成封装Map
		 * 小马post回来数据处理工具
	 * @throws Exception 
		 */ 
	 public static Map<String, Object> urlDecoderAndHander(String handerStr) throws Exception{
		//url解码,以及将空格,空字符,制表服等去掉
		String handerOffResStr=URLDecoder.decode(handerStr, "UTF-8").replaceAll("\\s*|\t|\r|\n", "");
		//字符串转换Map
		ObjectMapper objectMapper=new ObjectMapper();
		Map<String,Object> offerResultMap=objectMapper.readValue(handerOffResStr, Map.class);
		return offerResultMap;
	 }
	 
	 
	
		 
	 
}
