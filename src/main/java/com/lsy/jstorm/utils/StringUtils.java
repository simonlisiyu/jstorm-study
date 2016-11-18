package com.lsy.jstorm.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {

	private final static Logger log = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * 判断字符不为空
	 * @param str 传入字符
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(str != null && str != "" && str.length() > 0 && !"null".equals(str)){
			return true;
		}else{
			return false;
		}
	}

}
