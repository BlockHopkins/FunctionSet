/***
 * 日期计算工具类
 * 对应的java类是com.qc.function.NaturalMonth.NaturalMonthUtil
 */

var naturalMonthCalUtil = naturalMonthCalUtil || {};

$.extend(naturalMonthCalUtil, {
	
	//平年12个月份天数：
    _monthModelAvg : [31,28,31,30,31,30,31,31,30,31,30,31],
    //闰年12个月份天数：
    _monthModelLeap : [31,29,31,30,31,30,31,31,30,31,30,31],
	
	/**
	 * 判断是否是闰年
	 * 闰年 Leap Year
	 * 平年 average Year
	 */
	isLeapYear : function(fullYear){
		var year = fullYear;
		if(typeof(fullYear) == "string"){
			year = parseInt(fullYear);
		}else if(fullYear instanceof Date){
			year = fullYear.getFullYear();
		}
		if(typeof(year) == "number"){
			if(year%400==0 || (year%100!=0 && year%4==0)){
				return true;
			}
		}else{
			console.log("error---> shrEmpDateCalUtil.isLeapYear(fullYear): param is not a Date nor a String");
		}
		return false;
	},

    /**
     * 获取月份的天数：
     * @Param month 月份，一月则传入数字1
     * @Param year 年，可传入字符串或Date类型，不传则按平年返回
     */
    getDateCountOfMonthYear(month, year){
    	var self = this;
    	if(month>12 || month<1){
    		return -1
    	}
    	if(year!=null){
    		return self.getDateCountOfMonth(self.isLeapYear(year));
    	}else{
    		return self._monthModelAvg[month-1];
    	}
    },
    
    /**
     * 获取月份的天数：
     * @Param month 月份，一月则传入数字1
     * @Param isLeap 是否闰年，boolean值，不传则按平年返回
     */
    getDateCountOfMonth(month, isLeap){
    	var self = this;
    	if(month>12 || month<1){
    		return -1
    	}
    	if(isLeap){
    		return self._monthModelLeap[month-1];
    	}else{
    		return self._monthModelAvg[month-1];
    	}
    },
    
    /**
     * 计算传入日期加上n个自然月的结果, 
     * 参数month需大于0，否则返回false
     */
    addNaturalMonths(beginDateStr, month){
    	var self = this;
    	if(beginDateStr==null){
    		return false;
    	}
    	if(typeof(month)=="string"){
    		month == parseInt(month);
    	}
    	if(month==NaN || typeof(month)!="number" || month<=0){
    		return false;
    	}
    	var beginDate = new Date(beginDateStr);
    	//原来的年月日
    	var year1 = beginDate.getFullYear();
    	var month1 = beginDate.getMonth()+1;
    	var date1 = beginDate.getDate();
    	//目标的年月日
    	var year2 = year1;
    	var month2 = month1 + month;
    	var date2 = date1;
    	//如果加完后大于12个月则进位到年
    	if(month2 > 12){
    		year2 = year2 + parseInt(month2/12);
    		month2 = month2%12;
    		if(month2 == 0){
    			month2 = 12;
    			year2 = year2 - 1;
    		}
    	}
//    	if(month2 < 0){
//    		year2 = year2 + parseInt(month2/12);
//    		month2 = -month2%12;
//    		if(month2 == 0){
//    			month2 == 12;
//    		}
//    	}
    	//年月计算完毕，开始计算日期
    	var isLeap1 = self.isLeapYear(year1);//结果是否闰年
    	var isLeap2 = self.isLeapYear(year2);//结果是否闰年
    	var beginDateCount = self.getDateCountOfMonth(month1, isLeap1);
    	var endDateCount = self.getDateCountOfMonth(month2, isLeap2);
    	//如果4月30，一个月后应该是5月31日
    	//如果3月31日，一个月后应该是4月30日
    	if(date1 == beginDateCount || date2 > endDateCount){
    		date2 = endDateCount;
    	}
    	//格式化，10以下加0
    	if(month2 <= 9){
    		month2 = "0"+month2;
    	}
    	if(date2 <= 9){
    		date2 = "0"+date2;
    	}
    	return year2+"-"+month2+"-"+date2;
    },
    
    /**
     * 获取传入日期几个月后的最后一天
     */
    getLastDateInNaturalMonths(beginDateStr, month){
    	var self = this;
    	var date = new Date(self.addNaturalMonths(beginDateStr, month));
    	date.setDate(date.getDate()-1);
    	var monthStr = date.getMonth()+1;
    	var dateStr = date.getDate();
    	if(monthStr <= 9){
    		monthStr = "0"+monthStr;
    	}
    	if(dateStr <= 9){
    		dateStr = "0"+dateStr;
    	}
    	return date.getFullYear()+"-"+monthStr+"-"+dateStr;
    }
    
});
