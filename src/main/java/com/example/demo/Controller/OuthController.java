package com.example.demo.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.dto.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outh")
public class OuthController {

  @RequestMapping("/hello")
  public String hello(){
    return "hello world";
  }


  //@RequestMapping("/studet")
  @GetMapping("/studet")
  public String student(@RequestBody(required = false)  Student s1,@ModelAttribute Student s2,@RequestParam("token") String token){

    return "s1="+s1.toString()+"   ,   s2="+(s2==null?"s2 is null":s2.toString()+"  ,  token="+token);
  }

  @PostMapping("/student")
  public String stu(@RequestBody(required = false) Student student){

    return student==null?"studnet is null":student.toString();

  }

  @RequestMapping("receiveCode")
  public void receiveCode(String code){

    System.out.println("code="+code);

  }



}
