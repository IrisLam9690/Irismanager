package edu.manager.util;

import java.util.HashMap;
import java.util.Map;

import niuzhixiang.util.Cookie;

public class Utils {

	/**
	 * 将所需的cookie转变成Map形式
	 * @param cookies 要转换成Map形式的cookie数组
	 * @param keys 所需要的cookie名
	 * @return 转换后的Map
	 */
	public static Map<String, String> convertCookies(Cookie[] cookies, String ...keys) {
		Map<String, String> cookiesMap = new HashMap<String, String>();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				for (int i = 0; i < keys.length; i++) {
					if (cookie.getName().equals(keys[i])) {
						cookiesMap.put(cookie.getName(), cookie.getValue());
						break;
					}
				}		
			}
			return cookiesMap;
		} else {
			return null;
		}	
	}
}
