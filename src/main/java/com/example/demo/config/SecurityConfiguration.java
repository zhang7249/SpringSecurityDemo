package com.example.demo.config;

import com.example.demo.security.LoginFailHandler;
import com.example.demo.security.LoginSuccessHandler;
import com.example.demo.security.LogoutPrintLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义身份验证类（用于重写WebSecurityConfigurerAdapter默认配置）
 * @Configuration     表示这是一个配置类
 * @EnableWebSecurity    允许security
 * configure()     该方法重写了父类的方法，用于添加用户与角色
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutPrintLogHandler logoutPrintLogHandler;
    @Autowired
    private LoginSuccessHandler LoginSuccessHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationProvider securityProvider;
    @Autowired
    private LoginFailHandler loginFailHandler;

    @Override
    protected UserDetailsService userDetailsService() {
      //自定义用户信息类
      return this.userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


      http.csrf().disable();
      http.sessionManagement().invalidSessionUrl("/session/invalid");
      http.formLogin()          // 定义当需要用户登录时候，转到的登录页面。
          .loginPage("/login")
          .successHandler(LoginSuccessHandler)
          .successForwardUrl("/")
          .failureHandler(loginFailHandler)
          .permitAll()
          .and()
          .authorizeRequests()    // 定义哪些URL需要被保护、哪些不需要被保护
          .antMatchers("/session/invalid","/login",  "/logout", "/signOut","/logoutSuccess","/outh/**")
          .permitAll()
          .anyRequest()        // 任何请求,登录后可以访问
          .authenticated()
          ;
      http.logout()
          .logoutUrl("/logout")
          .logoutSuccessHandler(logoutPrintLogHandler)
          .deleteCookies("JSESSIONID")
          .permitAll();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

     auth.userDetailsService(userDetailsService)
         .passwordEncoder(new BCryptPasswordEncoder());
      auth.authenticationProvider(securityProvider);

    }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();

  }



}