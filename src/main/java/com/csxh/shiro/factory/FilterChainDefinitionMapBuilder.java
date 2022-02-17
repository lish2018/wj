package com.csxh.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//map.put("/admin/question/list", "perms[ques:list]");
		//map.put("/admin/center", "perms[admin:list]");
		map.put("/admin/question/ipsearch", "anon");
		map.put("/login", "anon");
		map.put("/logout", "logout");
		map.put("/admin/**", "authc");
		map.put("/report/**", "authc");
		map.put("/**", "anon");
		return map;
	}
}
