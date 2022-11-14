package com.hsn.exam.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Ut {

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof String == false) {
			return true;
		}

		String str = (String) obj;

		return str.trim().length() == 0;
	}

	public static String f(String format, Object... args) {

		return String.format(format, args);
	}
	
	public static String jsHistoryBack(String msg) {

		if (msg == null) {
			msg = "";
		}

		return Ut.f("""
				<script>
				const msg = '%s'.trim();
				if (msg.length > 0){
					alert(msg);
				}
				history.back();
				</script>
				""", msg);
	}
	
	public static String jsReplace(String msg,String uri) {
		
		if (msg == null) {
			msg = "";
		}

		if (uri == null) {
			uri = "";
		}

		return Ut.f("""
				<script>
				const msg = '%s'.trim();
				if (msg.length > 0){
					alert(msg);
				}
				location.replace('%s')
				</script>
				""", msg, uri);
	}

	public static String getUriEncoded(String str) {

		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}

	public static String getDateStrLater(long seconds) { 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format.format(System.currentTimeMillis() + seconds * 1000);

		return dateStr;
	}
	
	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> param = new HashMap<>();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);

			param.put(paramName, paramValue);
		}

		return param;
	}

	public static String getStrAttr(Map map, String attrName, String defaultValue) {

		if(map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}
		return defaultValue;
	}

	public static boolean allNumberString(String str) {//해당문장이 숫자로만 구성되었는지 참거짓으로 판별하는식

		if(str==null) {
			return false;
			
		}
		
		if(str.length()==0) {
			return true; //숫자만으로 구성된건아니니까 참으로 리턴
		}
		for(int i =0; i<str.length(); i++) {
			if(Character.isDigit(str.charAt(i))==false) { //문자열하나라도 숫자가 아니면 거짓을 리턴해줌
				
				return false;
			}
		}
		return true;
	}

	public static boolean startWithNumberString(String str) {
		if(str==null) {
			return false;
			
		}
		
		if(str.length()==0) {
			return false;
		}
		
		return Character.isDigit(str.charAt(0));
	}

	public static boolean isStandardLoginIdString(String str) {
		if ( str == null ) {
			return false;
		}

		if ( str.length() == 0 ) {
			return false;
		}

		// 조건
		// 5자 이상, 20자 이하로 구성
		// 숫자로 시작 금지
		// _, 알파벳, 숫자로만 구성
		return Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,19}$", str);
	}


}