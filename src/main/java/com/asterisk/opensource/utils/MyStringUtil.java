package com.asterisk.opensource.utils;

import com.google.common.base.Strings;

public class MyStringUtil {

	public static String decodeUnicode(String s) {  
		 StringBuilder sb = new StringBuilder(s.length());
		    char[] chars = s.toCharArray();
		    for (int i = 0; i < chars.length; i++) {
		        char c = chars[i];
		        if (c == '\\' && chars[i + 1] == 'u') {
		            char cc = 0;
		            for (int j = 0; j < 4; j++) {
		                char ch = Character.toLowerCase(chars[i + 2 + j]);
		                if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
		                    cc |= (Character.digit(ch, 16) << (3 - j) * 4);
		                } else {
		                    cc = 0;
		                    break;
		                }
		            }
		            if (cc > 0) {
		                i += 5;
		                sb.append(cc);
		                continue;
		            }
		        }
		        sb.append(c);
		    }
		    return sb.toString();
	}

	public static boolean isNullOrEmptyOr404(String string) {
		return Strings.isNullOrEmpty(string) || string.contains("404失效页面寻人计划");
	}
}
