package com.csxh.tools;

import org.apache.shiro.crypto.hash.SimpleHash;
/**
 * 加密
 */
public class PasswordHelper {

   
    //加密算法
    public String encryptPassword(String password,String salt) {
    	String algorithmName = "MD5";//加密算法
		int hashIterations = 1024;
		String result = new SimpleHash(algorithmName, password,salt,hashIterations).toString();
		return result;
    }
}

