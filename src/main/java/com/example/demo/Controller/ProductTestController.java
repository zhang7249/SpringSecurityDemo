package com.example.demo.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductTestController {


      //@RequestMapping("/info")
     // @ResponseBody
     // public String productInfo(){
     // return " some product info ";
      //}


      @RequestMapping("/info")
      @ResponseBody
      public String productInfo(@AuthenticationPrincipal UserDetails userDetails){
            String currentUser = "";
            Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principl instanceof UserDetails) {
                  currentUser = ((UserDetails)principl).getUsername();
            }else {
                  currentUser = principl.toString();
            }
            return " some product info,currentUser is: "+currentUser+"    "+userDetails.toString();
      }
}