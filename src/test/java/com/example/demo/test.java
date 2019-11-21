package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class test {


  public static void main(String[] args) {


    String time ="2019-11-12 13:46:19+08";

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    try {
      Date dd = simpleDateFormat.parse(time);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dd);
      calendar.add(Calendar.HOUR, 1);//加一天
      System.out.println("增加一天之后：" + simpleDateFormat.format(calendar.getTime()));
    } catch (ParseException e) {
      e.printStackTrace();
    }








  }

}
