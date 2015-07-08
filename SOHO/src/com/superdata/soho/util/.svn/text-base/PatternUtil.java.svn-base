package com.superdata.soho.util;

import java.util.regex.Pattern;

import android.text.TextUtils;

public class PatternUtil {
	public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", 32);
	private static String aa2;
	private static String aa3;
	private static String aa;
	public static String clearHtml(String html) {
		String text1 = patternHtmlTag.matcher(html).replaceAll("");
		String text =text1.replace("&nbsp;", "");
		if (TextUtils.isEmpty(text)) {
			return "";
		}
		return text.replaceAll("[\\s　]{1,}||(\r\n)", "");
	}
	public static String clearHtml1(String html) {
		String text = patternHtmlTag.matcher(html).replaceAll("");
		if (TextUtils.isEmpty(text)) {
			return "";
		}
		 aa = text.replaceAll("[\\s　]{2,}||(\r\n)", "");
		for(int i=0;i<30;i++){
		 aa3 =aa.replace("--", "-");
		 aa =aa3;
		}
	//	aa3 =aa.replaceAll("[--]{2,}","   ");
		String aa4 =aa3.replace("-&nbsp;"," ");
		String aan =aa4.replace("※&nbsp;", "                                   ");
		String aa5 =aan.replace("&nbsp;&nbsp;"," ");
		String aa6 =aa5.replace("※","");
		String aa7 =aa6.replace("&gt;", "");
		String aa8 =aa7.replace("&lt;","");
	
		return aa8;
	}

}
