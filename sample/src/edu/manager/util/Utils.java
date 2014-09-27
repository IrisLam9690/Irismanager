package edu.manager.util;

import java.util.HashMap;
import java.util.Map;

import niuzhixiang.util.Cookie;

public class Utils {

	/**
	 * �������cookieת���Map��ʽ
	 * @param cookies Ҫת����Map��ʽ��cookie����
	 * @param keys ����Ҫ��cookie��
	 * @return ת�����Map
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
