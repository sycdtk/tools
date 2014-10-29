package com.wolffy.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

	public static String MD5(String str){
		String r = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			r = md.digest(str.getBytes("UTF-8")).toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r;
	}

}
