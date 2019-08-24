package com.qc.function.sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 工具类:加密算法方法集合
 * @author Administrator
 *
 */
public class SecurityFunctions {
	//十六进制编码数字
	private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	/**
	 * 根据字符串生成十六进制编码的SHA1加密结果。
	 * @param str
	 * @return
	 */
	public static String generateVerifyCode(String str){
		String code = str;
		try{
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(str.getBytes());
			byte[] bytes = md.digest();
			int len = bytes.length;
			StringBuilder builder = new StringBuilder(len*2);//一个字节 => 两位十六进制编码   (一个字节8bit, 十六进制一位4bit)
			for(int i=0; i<len; i++){
				builder.append(hexDigits[bytes[i]>>4 & 0x0f]);//取高四位
				builder.append(hexDigits[bytes[i] & 0x0f]);//取低四位
			}
			code = builder.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return code;
	}
}
