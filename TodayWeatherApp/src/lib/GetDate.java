package lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GetDate {
	public static Date date;
	public static Calendar cal;
	public static String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Satday" };

	public static String date_today = yyyyMMdd(0);
	public static String date_yesterday = yyyyMMdd(-1);
	public static String date_tomorrow = yyyyMMdd(1);
	public static String date_afterDay = yyyyMMdd(2);

	public static String time_now = HH00(0);
	public static String time_before1h = HH00(-1);
	public static String time_after1h = HH00(1);

	public static String day_tomorrow = day(0);
	public static String day_afterDay = day(1);

	public static String text_todayDate = new SimpleDateFormat("M¿ù dÀÏ").format(new Date());

	public static String yyyyMMdd(int num) {
		cal = new GregorianCalendar();
		cal.add(Calendar.DATE, num);
		date = cal.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	public static String HH00(int num) {
		cal = new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, num);
		date = cal.getTime();
		return new SimpleDateFormat("HH00").format(date);
	}

	public static String day(int num) {
		cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_WEEK, num);
		return days[cal.get(Calendar.DAY_OF_WEEK)];
	}
}