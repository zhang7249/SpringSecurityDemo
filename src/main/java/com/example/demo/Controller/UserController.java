package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
  @RequestMapping("/hello")
  @ResponseBody
  String home() {
    return "Hello ,spring security!   lala  aa a";
  }
}