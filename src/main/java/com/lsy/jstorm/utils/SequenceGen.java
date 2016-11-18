package com.lsy.jstorm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SequenceGen {

	private static DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public static String number(String prefix){
		int random = (int)(1000+Math.random()*(9999-1000+1));
		String sequenceNumber = prefix +"-"+ sdf.format(new Date()) +"-000"+ random;
		return sequenceNumber;
	}
}
