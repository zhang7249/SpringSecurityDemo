package com.example.demo.weibo;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class test {

  public static void main(String[] args) throws IOException {

    String path="/Users/chuangzhang/Desktop/cgError.txt";

    FileReader reader = new FileReader(path);
    BufferedReader br = new BufferedReader(reader);
    String temp=null;
    Set<String> set=new HashSet<>();
    HashMap<String,String> hashMap = new HashMap<>();

    while ((temp=br.readLine()) != null) {

      if(temp.contains("values")){

        String json=temp.substring(temp.indexOf("{"),temp.indexOf("}")+1);
        JSONObject j=JSONObject.parseObject(json);
        String caseguid=j.getString("caseguid");
        set.add(caseguid);
        //System.out.println("--------------"+json);

        String v=hashMap.get(caseguid);
        String eventType=j.getString("eventType");
        if(v==null)
          hashMap.put(caseguid,eventType);
        else
          hashMap.put(caseguid,v+"   "+eventType);

      }

    }

    for (String s : set) {
      System.out.println(s);
    }

    System.out.println("count="+set.size());

    Set keys=hashMap.keySet();
    for(Object s:keys) {
      if (s instanceof String) {
        System.out.println("caseguid: " + s + "  ;   eventType: " + hashMap.get(s));
      }
    }

    //关闭读取流
    br.close();
    reader.close();
  }



}
