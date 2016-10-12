package com.atraxo.f4f.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.user.User;

public class UserUtils {
	
	private static final Logger LOGGER = Logger.getLogger(UserUtils.class);
	
	private static final String ENCRYPTION_MD5 = "MD5" ;
	private static final String CHARSET_ENCRYPTION_ISO = "iso-8859-1" ;
	private static final String TIMEZONE_COOKIE = "TIMEZONE_COOKIE";
	
	public static final String ENCRYPTION_PATTERN_MD5 = "[a-fA-F0-9]{32}" ;
	
	private UserUtils(){
		//private constructor
	}
	
	public static User getCurrentLoggedUser(){
		User loggedUser = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if ( context != null ) { 
			ExternalContext externalContext = context.getExternalContext();
			if ( externalContext != null ) {
				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				loggedUser = (User)request.getSession().getAttribute("user");
			}
		}

		return loggedUser;
	}
	
	public static String getClientTimeOffset(){
		ExternalContext externalContext;
		try {
			externalContext = FacesContext.getCurrentInstance().getExternalContext();
		} catch (Exception e) {
			LOGGER.error("Could not get external context", e);
			return null;
		}
		
		
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(TIMEZONE_COOKIE)) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
	} 
	
	public static String encryptMD5Password(String text) throws UnsupportedEncodingException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ENCRYPTION_MD5);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("ERROR:could not find the algorithm encryption !", e);
			return null;
		}
		
		md.update(text.getBytes(CHARSET_ENCRYPTION_ISO), 0, text.length());
		byte[] md5 = md.digest();
		return convertedToHex(md5);
	} 
	
	public static String decryptMD5Password (String password){
		byte[] defaultBytes = password.getBytes();
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance(ENCRYPTION_MD5);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("ERROR:could not find the algorithm encryption !", e);
		}
		StringBuilder hexString = new StringBuilder();

		if ( algorithm != null ) {
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte[] messageDigest = algorithm.digest();

			for (int i=0;i<messageDigest.length;i++) {
				String hexCode = Integer.toHexString(0xFF & messageDigest[i]);
				if (hexCode.length() == 1) {
					hexCode = "0".concat(hexCode);
				}
				hexString.append(hexCode);
			}
		}
		return hexString.toString();	
	}
	
	private static String convertedToHex(byte[] data){
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < data.length; i++){
			int halfOfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfBytes = 0;

			do{
				if ((0 <= halfOfByte) && (halfOfByte <= 9)){
					buf.append( (char) ('0' + halfOfByte) );
				}
				else{
					buf.append( (char) ('a' + (halfOfByte - 10)) );
				}

				halfOfByte = data[i] & 0x0F;

			} while(twoHalfBytes++ < 1);
		}
		return buf.toString();
	}
	
}
