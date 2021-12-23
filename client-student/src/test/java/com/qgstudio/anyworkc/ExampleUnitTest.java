package com.qgstudio.anyworkc;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(getYesterDay());
        System.out.println(getToday());
    }

    private String getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy.MM.dd").format(time);
    }

    private String getToday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy.MM.dd").format(time);
    }
}