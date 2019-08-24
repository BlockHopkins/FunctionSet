package com.qc.function.NaturalMonth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author Qc
 */
public class DateTimeUtils {
	
	/**
	 * 获取日期的年份值，如2019
	 */
	public static int getYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int era = calendar.get(Calendar.ERA);
		if(era == GregorianCalendar.BC){
			//era == GregorianCalendar.BC 公元前
			return -1 * year;
		}else{
			//era == GregorianCalendar.AD 公元后
			return year;
		}
	}
	
	/**
	 * 获取日期的月份值  (1-12)
	 */
	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 获取日期的日(1-31)
	 */
	public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取指定日期的前一天
	 */
	public static Date getBeforeDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期的后一天
	 */
	public static Date getAfterDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取一周中第n天的名称，如 1 为 星期日，2位星期一
	 */
	public static String getDayName(int dayIndex){
		if(dayIndex>=1 && dayIndex<=7){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, dayIndex);
			return format(calendar.getTime(), "EEEE", null, null);
		}else{
			throw new RuntimeException("DateTimeUtils.getDateName(dayIndex) : dayIndex must be between 1 and 7, now it is " + dayIndex + " !");
		}
	}
	
	/**
	 * 获取指定日期的星期名称，如星期日，星期一
	 */
	public static String getDayName(Date date){
		return format(date, "EEEE", null, null);
	}
	
	/**
	 * 格式化日期为字符串
	 * @param date 日期
	 * @param formatStr 格式 （允许为null，为null时采用默认格式：yyyy-MM-dd HH:mm:ss）
	 * @param timeZone 时区 （允许为null，为null时默认使用JVM本地时间）
	 * @param locale 语言 （允许为null）
	 * @return
	 */
	public static String format(Date date, String formatStr, TimeZone timeZone, Locale locale){
		if(formatStr == null ){
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf;
		if(locale==null){
			sdf = new SimpleDateFormat(formatStr);
		}else{
			sdf = new SimpleDateFormat(formatStr, locale);
		}
		if(timeZone == null){
			timeZone = TimeZone.getDefault();
		}
		sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}
	
	/**
	 * 转换年月日为日期Date对象
	 * @param year 年份
	 * @param month 月份，取值1~12
	 * @param date 日
	 * @return
	 */
	public static Date getDate(int year, int month, int date){
		Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, date, 0, 0, 0);
        return new Date((cal.getTimeInMillis() / 1000) * 1000);
	}
	
	/**
	 * 转换年月日时分秒为日期Date对象
	 * @param year 年份
	 * @param month 月份，取值1~12
	 * @param date 日
	 * @param hour
	 * @param min
	 * @param second
	 * @return
	 */
	public static Date getDate(int year, int month, int date, int hour, int min, int second){
		Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, date, hour, min, second);
        return new Date((cal.getTimeInMillis() / 1000) * 1000);
	}
}
