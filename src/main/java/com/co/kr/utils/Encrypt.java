package com.co.kr.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypt {
	public static String[] encrypt(String input) {
		String[] res = new String[2];
		
		try {
			//Making Salt value
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			rand.nextBytes(bytes);
			
			String salt = new String(Base64.getEncoder().encode(bytes));
			
			//SHA 512
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			md.update(input.getBytes());
			
			res[0] = String.format("%0128x", new BigInteger(1, md.digest()));
			res[1] = salt;
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println("ERROR : NoSuchAlgorithmException");
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static Integer loginCheck(String input, String salt) {
		Integer res = 0;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			
			md.update(salt.getBytes());
			md.update(input.getBytes());
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println("ERROR : NoSuchAlgorithmException");
			e.printStackTrace();
		}
		
		return res;
	}
}
