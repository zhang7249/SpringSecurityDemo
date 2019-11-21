package com.example.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class CaseModel extends BaseRowModel {

  /**
   * 经度
   */
  @ExcelProperty(index = 6)
  private String lon;

  /**
   * 纬度
   */
  @ExcelProperty(index = 7)
  private String lat;

  /**
   * 宽泛地址
   */
  @ExcelProperty(index = 8)
  private String address1;

  /**
   * 所属街道
   */
  @ExcelProperty(index = 9)
  private String street;

  /**
   * 详细地址
   */
  @ExcelProperty(index = 10)
  private String address2;

  /**
   * 备注
   */
  @ExcelProperty(index = 11)
  private String remark;

  /**
   * 立案时间
   */
  @ExcelProperty(index = 12)
  private String time;

  /**
   * 结案内容
   */
  @ExcelProperty(index = 13)
  private String content;


}
