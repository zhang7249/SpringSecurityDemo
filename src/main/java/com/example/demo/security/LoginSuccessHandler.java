package com.example.demo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication)
      throws IOException, ServletException {

    Object principal = authentication.getPrincipal();;
    if (principal != null && principal instanceof UserDetails) {
      UserDetails user = (UserDetails) principal;
      log.info("-----------------------"+this.getClass().getName()
            +"    onAuthenticationSuccess     loginUser:"+user.getUsername()+" 登录成功！！");
      //维护在session中
      httpServletRequest.getSession().setAttribute("userDetail", user);
      httpServletResponse.sendRedirect("/");
    }




  }
}
