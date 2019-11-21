package com.example.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.sound.midi.Soundbank;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;

public class ReadExcel {

  public static final String WARNING="insert into t_warning (id, img_url, processed_img_url, state, area, flight_area, type, geometry, address, remark, case_guid, created_at, created_by, updated_at, updated_by,newinstcondcode)"
      + "  values (nextval('t_warning_id_seq'), '{1}', '{1}', '8', '%s', NULL, '1', ST_GeomFromText('POINT(%s %s)', 4326), '%s', '%s', '%s', '%s', NULL, '%s', NULL,'1802010101');";


  public static final String FLOW_1_CASE_VERIFY_START=""+
      "INSERT INTO t_flow (\"id\", \"state\", \"img_url\", \"result_desc\", \"user_guid\", \"task_guid\", \"username\", \"remark\", \"created_at\", \"updated_at\", \"warning_id\", \"plan_id\", \"created_by\", \"updated_by\", \"is_real\", \"event_type\", \"case_complete_type\") "
      + "VALUES (nextval('t_flow_id_seq'), '4', '{}', NULL, NULL, NULL, NULL, '%s', '%s', '%s', (select id from t_warning where case_guid='%s'), NULL, NULL, NULL, NULL, 'CASE_VERIFY_START', NULL);";


  public static final String FLOW_2_CASE_VERIFY_SEND=""
      + "INSERT INTO t_flow(\"id\", \"state\", \"img_url\", \"result_desc\", \"user_guid\", \"task_guid\", \"username\", \"remark\", \"created_at\", \"updated_at\", \"warning_id\", \"plan_id\", \"created_by\", \"updated_by\", \"is_real\", \"event_type\", \"case_complete_type\") "
      + "VALUES (nextval('t_flow_id_seq'), NULL, '{}', NULL, NULL, NULL, NULL, '%s', '%s', '%s', (select id from t_warning where case_guid='%s'), NULL, NULL, NULL, NULL, 'CASE_VERIFY_SEND', NULL);";


  public static final String FLOW_3_CASE_PROCESS_START=""+
      "INSERT INTO t_flow(\"id\", \"state\", \"img_url\", \"result_desc\", \"user_guid\", \"task_guid\", \"username\", \"remark\", \"created_at\", \"updated_at\", \"warning_id\", \"plan_id\", \"created_by\", \"updated_by\", \"is_real\", \"event_type\", \"case_complete_type\") "
      + "VALUES (nextval('t_flow_id_seq'), '5', '{}', NULL, NULL, NULL, NULL, '%s', '%s', '%s',  (select id from t_warning where case_guid='%s'), NULL, NULL, NULL, NULL, 'CASE_PROCESS_START', NULL);";

  public static final String FLOW_4_CASE_PROCESS_COMPLETE=""+
      "INSERT INTO t_flow(\"id\", \"state\", \"img_url\", \"result_desc\", \"user_guid\", \"task_guid\", \"username\", \"remark\", \"created_at\", \"updated_at\", \"warning_id\", \"plan_id\", \"created_by\", \"updated_by\", \"is_real\", \"event_type\", \"case_complete_type\") "
      + "VALUES (nextval('t_flow_id_seq'), '5', '{}', NULL, NULL, NULL, NULL, '%s', '%s', '%s', (select id from t_warning where case_guid='%s'), NULL, NULL, NULL, NULL, 'CASE_PROCESS_COMPLETE', NULL);";


  public static final String FLOW_5_CASE_COMPLETE=""+
      "INSERT INTO t_flow(\"id\", \"state\", \"img_url\", \"result_desc\", \"user_guid\", \"task_guid\", \"username\", \"remark\", \"created_at\", \"updated_at\", \"warning_id\", \"plan_id\", \"created_by\", \"updated_by\", \"is_real\", \"event_type\", \"case_complete_type\") "
      + "VALUES (nextval('t_flow_id_seq'), '8', '{}', NULL, NULL, NULL, NULL, '%s', '%s', '%s',  (select id from t_warning where case_guid='%s'), NULL, NULL, NULL, NULL, 'CASE_COMPLETE', '%s');";

  public static void main(String[] args) throws ParseException {

//    1 remark
//    2 createAt
//    3 updateAt
//    4 caseGuid



    List<Object> list=readSmallExcel();

    System.out.println("----------------------------------------------------------------->");

    System.out.println("共"+list.size()+"条数据");

    System.out.println("----------------------------------------------------------------->");
    System.out.println("----------------------------------------------------------------->");
    System.out.println("----------------------------------------------------------------->");

    System.out.println();
    System.out.println();


    for(Object caseModel:list){

    //Object caseModel=list.get(0);

      if(caseModel instanceof CaseModel){

        String street=chooseStreet(((CaseModel) caseModel).getStreet());
        String gpsx=((CaseModel) caseModel).getLon();
        String gpsy=((CaseModel) caseModel).getLat();
        String address=((CaseModel) caseModel).getAddress1()+"  "+((CaseModel) caseModel).getAddress2();
        String remark=((CaseModel) caseModel).getRemark();
        String caseGuid= UUID.randomUUID().toString();
        String time=((CaseModel) caseModel).getTime();
        String content=((CaseModel) caseModel).getContent();


        String warningSql=  String.format(WARNING,street,gpsx,gpsy,address,remark,caseGuid,time,timeHandle(time,5));

        String time1=timeHandle(time,1);
        String flow_1_sql=  String.format(FLOW_1_CASE_VERIFY_START,remark,time1,time1,caseGuid);

        String time2=timeHandle(time,2);
        String flow_2_sql=  String.format(FLOW_2_CASE_VERIFY_SEND,remark,time2,time2,caseGuid);

        String time3=timeHandle(time,3);
        String flow_3_sql=  String.format(FLOW_3_CASE_PROCESS_START,remark,time3,time3,caseGuid);

        String time4=timeHandle(time,4);
        String flow_4_sql=  String.format(FLOW_4_CASE_PROCESS_COMPLETE,content,time4,time4,caseGuid);

        String time5=timeHandle(time,5);
        String flow_5_sql=  String.format(FLOW_5_CASE_COMPLETE,content,time5,time5,caseGuid,caseCompleteType(content));

        System.out.println(warningSql);
        System.out.println(flow_1_sql);
        System.out.println(flow_2_sql);
        System.out.println(flow_3_sql);
        System.out.println(flow_4_sql);
        System.out.println(flow_5_sql);
        System.out.println();
        System.out.println();

      }



    }

    System.out.println();
    System.out.println();


    System.out.println("----------------------------------------------------------------->");
    System.out.println("----------------------------------------------------------------->");
    System.out.println("----------------------------------------------------------------->");


  }

  private static String caseCompleteType(String content){

    if(content==null)
      return  "拆除（恢复";

    if(content.contains("非违建")){
      return "非违建";
    }else{
      return "拆除（恢复";
    }


  }

  private static String timeHandle(String time,int hour) throws ParseException {



    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date dd = simpleDateFormat.parse(time);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dd);
    calendar.add(Calendar.HOUR, hour);//加一小时
    return simpleDateFormat.format(calendar.getTime());

  }


  private  static String chooseStreet(String street){

    if(street==null)
      return "-1";

    if(street.equals("金阊街道"))
      return "1";
    if(street.equals("吴门桥街道"))
      return "2";
    if(street.equals("白洋湾街道"))
      return "3";
    if(street.equals("虎丘街道"))
      return "4";
    if(street.equals("苏锦街道"))
      return "5";
    if(street.equals("双塔街道"))
      return "6";
    if(street.equals("沧浪街道"))
      return "7";
    if(street.equals("平江街道"))
      return "8";
    if(street.equals("火车站区域"))
      return "9";
    return "-1";
  }

  /**
   * 读取小于1000行的数据
   * @return
   */
  private static List<Object> readSmallExcel(){
    String fileName = "/Users/chuangzhang/Desktop/caseOld2.xlsx";
    InputStream inputStream = getInputStream(fileName);
    List<Object> data = null;
    try {
      //new Sheet(int sheetNum, int headLineNum, Class<? extends BaseRowModel> clazz)
      data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, CaseModel.class));
//      for(Object obj : data) {
//        System.out.println(obj);
//      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return data;
  }


  private static InputStream getInputStream(String fileName) {
    try {
      return new FileInputStream(new File(fileName));
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

}
