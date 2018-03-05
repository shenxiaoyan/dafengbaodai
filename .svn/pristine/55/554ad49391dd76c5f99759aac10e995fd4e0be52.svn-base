package com.liyang.util;

import java.lang.reflect.Method;

/**
 * @author Administrator
 *
 */
public class Base64 {
	 public static byte[] decodeBase64(String input) throws Exception{ 
		    Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64"); 
		    Method mainMethod= clazz.getMethod("decode", String.class); 
		    mainMethod.setAccessible(true); 
		     Object retObj=mainMethod.invoke(null, input); 
		     return (byte[])retObj; 
		  } 
	 
	  public static String encodeBase64(byte[]input) throws Exception{ 
		    Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64"); 
		    Method mainMethod= clazz.getMethod("encode", byte[].class); 
		    mainMethod.setAccessible(true); 
		     Object retObj=mainMethod.invoke(null, new Object[]{input}); 
		     return (String)retObj; 
		  } 
}
