package com.example.demo;

public class Global {

  public static String WARNING="insert into t_warning (id, img_url, processed_img_url, state, area, flight_area, type, geometry, address, remark, case_guid, created_at, created_by, updated_at, updated_by)\n"
      + "  values (nextval('t_warning_id_seq'), NULL, NULL, '8', '%s', NULL, '1', ST_GeomFromText('POINT(%s %s)', 4326), %s, %s, %s, %s, NULL, %s, NULL, NULL)";

}
