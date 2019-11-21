package com.example.demo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutPrintLogHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        log.info("authentication="+authentication);

        log.info("---------------LogoutPrintLogHandler   onLogoutSuccess    "
                        + "  username:"+(authentication==null?"null":authentication.toString())+"退出成功");

        response.sendRedirect("/logoutSuccess");
    }
}
