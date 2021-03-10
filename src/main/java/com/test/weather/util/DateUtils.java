/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.test.weather.util;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期处理
 *
 * @author Mark sunlightcs@gmail.com
 */
public class DateUtils {
	public final static String DATE_TIME_PATTERN = "E hh:mm a";

    /**
     * 时间转换
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(new Date(timestamp*1000));
    }


}
