package com.qc.function.NaturalMonth;

import java.util.Date;

/**
 * s-HR自然月计算工具类
 * 
 * <p>对应的js类是这个文件：employee\web\js\shr\emp\shrEmpDateCalUtil.js</p>
 * 其中addNaturalMonths(beginDate, month)方法的第二个参数限制条件java和js不太一样，请注意。
 * <p>名字就不改成一样了，java中目前日期工具类比较多，比较完善，这个java类专门处理自然月逻辑就行了。
 * js可以考虑其他通用日期计算逻辑也写进去，这样视图可以不用引入那么多js，文件管理也不会太杂。</p>
 * @author Qc
 */
public class NaturalMonthUtil {
	//平年12个月份天数：
	private static final int[] monthModelAvg = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
	//闰年12个月份天数：
	private static final int[] monthModelLeap = new int[]{31,29,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 判断给出日期所在年是否闰年
	 * @param date 日期
	 * @return
	 */
	public static boolean isLeapYear(Date date){
		int year = DateTimeUtils.getYear(date);
		return isLeapYear(year);
	}
	
	/**
	 * 是否闰年
	 * @param year 年份（如：2019）
	 * @return
	 */
	public static boolean isLeapYear(int year){
		if(year%400==0 || (year%100!=0 && year%4==0)){
			//判断闰年，满100的需要能被400整除，否则需被4整除
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取当月最大天数
	 * @param month 月份，取值1~12
	 * @param isLeap 是否闰年
	 * @return
	 */
	public static int getDateCountOfMonth(int month, boolean isLeap){
		if(month>12 || month<1){
			return -1;
		}
		if(isLeap){//闰年
			return monthModelLeap[month-1];
		}else{//平年
			return monthModelAvg[month-1];
		}
	}
	
	/**
	 * 获取当月最大天数
	 * @param month 月份，取值1~12
	 * @param year 年份
	 * @return
	 */
	public static int getDateCountOfMonth(int month, int year){
		if(month>12 || month<1){
			return -1;
		}
		return getDateCountOfMonth(month,isLeapYear(year));
	}
	
	/**
	 * 获取所给日期所在月的最大天数
	 * @param date 日期
	 * @return
	 */
	public static int getDateCountOfMonth(Date date){
		int month = DateTimeUtils.getMonth(date);
		int year = DateTimeUtils.getYear(date);
		return getDateCountOfMonth(month, year);
	}
	
	/**
	 * 自然月加减，计算传入日期加上n个自然月的结果
	 * @param beginDate 开始日期
	 * @param month 月份（值为0则返回当天，值小于0则为减法计算，值大于0则为加法计算）
	 * 如果4月30，一个月后应该是5月31日
	 * 如果3月31日，一个月后应该是4月30日
	 * 示例:
	 * 1.传入{2019-04-16，2}结果为2019-06-16
	 * 2.传入{2019-01-30，1}结果为2019-02-28
	 * 3.传入{2019-01-31，1}结果为2019-02-28
	 * 4.传入{2016-01-30，1}结果为2019-02-29
	 * 5.传入{2016-01-31，1}结果为2019-02-29
	 * 6.传入{2016-04-30，1}结果为2019-05-31
	 * 7.传入{2016-03-31，1}结果为2019-04-30
	 * @return
	 */
	public static Date addNaturalMonths(Date beginDate, int month){
		if(month==0){
			//如果传入month为0，返回当天
			return beginDate;
		}
		int year1 = DateTimeUtils.getYear(beginDate);
		int month1 = DateTimeUtils.getMonth(beginDate);
		int date1 = DateTimeUtils.getDay(beginDate);
		//计算结果
		int year2 = year1;
		int month2 = month1 + month;
		int date2 = date1;
		
		if(month2 > 12){//结果大于12个月则进位到年
			year2 = year2 + month2/12;
			month2 = month2%12;
			if(month2 == 0){
				month2 = 12;
				year2--;
			}
		}else if(month2 < 0){//如果传入month为负数，则这里从年退位
			year2 = year2 - 1 + month2/12;
			month2 = 12 + month2%12;
		}
		
		boolean isLeap1 = isLeapYear(year1);
		boolean isLeap2 = isLeapYear(year2);
		int beginDateCount = getDateCountOfMonth(month1, isLeap1);
		int endDateCount = getDateCountOfMonth(month2, isLeap2);
		//如果4月30，一个月后应该是5月31日
    	//如果3月31日，一个月后应该是4月30日
		if(date1==beginDateCount || date2>endDateCount){
			date2 = endDateCount;
		}
		
		return DateTimeUtils.getDate(year2,month2,date2);
	}
	
	/**
	 * 获取传入日期几个月后的最后一天。
	 * 示例:
	 * 1.传入{2019-04-16，2}结果为2019-06-15
	 * 2.传入{2019-01-30，1}结果为2019-02-27
	 * 3.传入{2019-01-31，1}结果为2019-02-27
	 * 4.传入{2016-01-30，1}结果为2019-02-28
	 * 5.传入{2016-01-31，1}结果为2019-02-28
	 * 6.传入{2016-04-30，1}结果为2019-05-30
	 * 7.传入{2016-03-31，1}结果为2019-04-29
	 * @param beginDate
	 * @param month
	 * @return
	 */
	public static Date getLastDateInNaturalMonths(Date beginDate, int month){
		Date date = addNaturalMonths(beginDate, month);
		return DateTimeUtils.getBeforeDay(date);
	}
	
}
