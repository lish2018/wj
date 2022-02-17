package com.csxh.tools;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5 {

	public static void main(String[] args) {
		String algorithmName = "MD5";//加密算法
		Object source = "txl/0229./";
		String salt = null;
		int hashIterations = 1024;
		String result = new SimpleHash(algorithmName, source,salt,hashIterations).toString();
		System.out.println(result);
	}

}
