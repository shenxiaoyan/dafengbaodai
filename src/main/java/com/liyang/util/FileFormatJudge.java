package com.liyang.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;


/**
 * @author Administrator
 *
 */
public class FileFormatJudge {
   private FileFormatJudge(){}
   /**
	 * 将文件头转化成16进制字符串
	 * @param src
	 * @return
	 */
private static String bytesToHexString(byte[] src) {  
       StringBuilder stringBuilder = new StringBuilder();  
       if (src == null || src.length <= 0) {  
           return null;  
       }  
       for (int i = 0; i < src.length; i++) {  
           int v = src[i] & 0xFF;  
           String hv = Integer.toHexString(v);  
           if (hv.length() < 2) {  
               stringBuilder.append(0);  
           }  
           stringBuilder.append(hv);  
       }  
       return stringBuilder.toString();  
   }  
   
   /**
     * 以流的形式获取到文件头的16进制字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
private static String getFileContent(InputStream in) throws IOException {  
       byte[] b = new byte[28];   
       try {  
           in.read(b, 0, 28);  
       } catch (IOException e) {  
           e.printStackTrace();  
           throw e;  
       } finally {  
           if (in != null) {  
               try {  
                   in.close(); 
               } catch (IOException e) {  
                   e.printStackTrace();  
                   throw e;  
               }  
           }  
       }  
       return bytesToHexString(b);   
   }
   
   /**
     * 判断文件类型 
	 * @param in
	 * @return
	 * @throws IOException
	 */
public static FileFormat getFormat(InputStream in) throws IOException {  
 
       String fileHead = getFileContent(in);  
 
       if (fileHead == null || fileHead.length() == 0) {  
           return null;  
       }  
       
       fileHead = fileHead.toUpperCase();  
       //获取到FileType的所有类型的值
       FileFormat[] fileTypes = FileFormat.values();     
 
       for (FileFormat type : fileTypes) {  
           if (fileHead.startsWith(type.getValue())) {  
               return type;  
           }  
       }  
       return null;  
   } 
   
 
   public static Boolean isImage(FileFormat value) {  
      
       FileFormat[] pics = { FileFormat.JPEG, FileFormat.PNG, FileFormat.GIF, FileFormat.TIFF, FileFormat.BMP};  
      
       for (FileFormat fileType : pics) {  
           if (fileType.equals(value)) { 
        	  return true;
           }  
       }   
       return false;  
   }    
}
