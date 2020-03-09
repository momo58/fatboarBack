package com.pfa.fatboar.FatboarBack.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
	
	public static String formatDate(Date date) {
		if (date == null) return null;
		
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		 return formatter.format(ldt);
	}

}
