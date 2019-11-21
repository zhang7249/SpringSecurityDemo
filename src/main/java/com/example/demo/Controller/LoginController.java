package com.example.demo.Controller;

import ch.qos.logback.core.joran.conditional.ElseAction;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        log.info(".....LoginController   login   ....系统登录页");
        return "login";
    }


    @GetMapping("/session/invalid")
    public String sessionInvalid(){

        log.info(".....LoginController   sessionInvalid   ....登录超时，重新请求登录页面");
        return "login";

    }


    @RequestMapping("/")
    public String index(){
        log.info("------------------" + this.getClass().getName() + "    index  登陆成功，显示主页");
        return "A";
    }


    @RequestMapping("/toB")
    public String toB(){
        return "B";
    }

    @RequestMapping("/toA")
    public String toA(){
        return "A";
    }

    @RequestMapping("userInfo")
    public ModelAndView userInfo(@AuthenticationPrincipal UserDetails userDetails){

        HashMap<String,String> hashMap=new HashMap<String,String>();
        hashMap.put("userInfo",userDetails.toString());
        hashMap.put("name","zhangchuang");
        hashMap.put("age","25");

        return new ModelAndView("C","data",hashMap);

    }

    @RequestMapping("/logoutSuccess")
    public String logOut(){
        log.info("------------------" + this.getClass().getName() + "  /logoutSuccess     跳转到登出页面");
        return "logout";
    }

}