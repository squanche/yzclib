package com.yangzhichao.yzclib.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/2/3
 * @class <code>dateTest</code>
 * @see
 * @since JDK1.8
 */
public class dateTest {
    public static void main(String[] args) {


        // 10位的秒级别的时间戳
        long time1 = 1576080000000L;
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time1 * 1000));
       // System.out.println("10位数的时间戳（秒）--->Date:" + result1);
        Date date1 = new Date(time1 * 1000);   //对应的就是时间戳对应的Date
        // 13位的秒级别的时间戳
        double time2 = 1576080000000L;
        String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time2);
        System.out.println("1576080000000L的时间戳（毫秒）--->Date:" + result2);


    }
}