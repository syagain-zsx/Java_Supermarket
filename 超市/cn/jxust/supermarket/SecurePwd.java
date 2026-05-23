package cn.jxust.supermarket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SecurePwd {
	/*public static void main(String[] args) {
		System.out.println(MD5Test("123"));
		
	}*/
	
	/**
	 * 生成随机盐值
	 * @return 8位随机盐值字符串
	 */
	public static String generateSalt() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder salt = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			salt.append(chars.charAt(random.nextInt(chars.length())));
		}
		return salt.toString();
	}
	
	/**
	 * 带盐值的MD5加密
	 * @param source 原始密码
	 * @param salt 盐值
	 * @return 加密后的密码
	 */
	public static String MD5WithSalt(String source, String salt) {
		return MD5Test(source + salt);
	}
	
	public static String MD5Test(String source) {
		StringBuilder builder=new StringBuilder();
		try {
			MessageDigest messDigest=MessageDigest.getInstance("MD5");
			byte[] bytes=source.getBytes();
			char[] chars=new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			byte[] targetByte=messDigest.digest(bytes);
			for(byte b:targetByte) {
				int high=(b>>>4)&0xf;
				int low=b&0xf;
				char charHigh=chars[high];
				char charLow=chars[low];
				builder.append(charHigh).append(charLow);
			}
			
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return builder.toString();
		
	}

}