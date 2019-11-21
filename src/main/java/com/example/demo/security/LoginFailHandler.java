package com.example.demo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException authenticationException)
      throws IOException, ServletException {
      System.out.println("error"+authenticationException.getMessage());
      log.info("------------------LoginFailHandler   onAuthenticationFailure（） 用户登录失败！authenticationException="
          + authenticationException==null?"null":authenticationException.toString()
          + "; httpServletRequest="+httpServletRequest.toString());
      httpServletResponse.sendRedirect("/login");
  }
}
