package lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GetDate {
	static Date d=new Date();

	public static String getToday_yyyyMMdd() {
		return new SimpleDateFormat("yyyyMMdd").format(d);
	}
	
	public static String getTodayText() {
		return new SimpleDateFormat("M월 d일").format(d);
	}
	
	public static String getYesterday_yyyyMMdd() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DATE, -1);
		Date date=cal.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static String getTomorrow_yyyyMMdd() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DATE, 1);
		Date date=cal.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static String getAfterDay_yyyyMMdd() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DATE, 2);
		Date date=cal.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static String getBefor1hBaseTime() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		Date date=cal.getTime();
		return new SimpleDateFormat("HHmm").format(date);
	}
	
	public static String getAfter1hBaseTime() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, 1);
		Date date=cal.getTime();
		return new SimpleDateFormat("HHmm").format(date);
	}
	
	public static String getDay(int date) {
		// data=0 : 내일 요일, date=1 : 모레 요일
		String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Satday" };
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DAY_OF_WEEK, date);
		String result=days[cal.get(Calendar.DAY_OF_WEEK)];
		return result;
	}
	
	public static void main(String[] args) {
		
	}
}