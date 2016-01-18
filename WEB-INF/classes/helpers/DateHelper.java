package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateHelper {
	public static String printTime(Date date){
		long different = new Date().getTime() - date.getTime();
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;
		String s="";
		if(elapsedDays>=10){
			DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
			return dateFormat.format(date).toString();
		}else if(elapsedDays<10&&elapsedDays>0){
			if(elapsedDays!=1) s="s";
			return elapsedDays + " day"+s+" ago";
		}
		
		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;
		if(elapsedHours!=0){
			if(elapsedHours!=1) s="s";
			return elapsedHours + " hour"+s+" ago";
		}
		
		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;
		if(elapsedMinutes!=0){
			if(elapsedMinutes!=1) s="s";
			return elapsedMinutes + " minute"+s+" ago";
		}
		
		long elapsedSeconds = different / secondsInMilli;
		if(elapsedSeconds!=0){
			if(elapsedSeconds!=1) s="s";
			return elapsedSeconds + " second"+s+" ago";
		}
		return "0 seconds ago";
	}

	public static String printDob(Date dob){
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, YYYY");
		return dateFormat.format(dob).toString();		
	}
	
	public static Date getDate(String dob){
		String[] d = dob.split("-");
		dob=d[1]+"/"+d[2]+"/"+d[0];
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = null;
		try {
			date = (Date)formatter.parse(dob);
		} catch (ParseException e) {
		}
		return date;
	}
	
	public static String printAge(Date dob){
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(dob);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date());

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		String age=diffYear+" Years";
		
		if(diffMonth!=0){
			age += ", "+diffMonth+" Months";
		}
		return age;
	}
}

