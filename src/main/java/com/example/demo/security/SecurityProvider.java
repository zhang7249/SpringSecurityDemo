package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 自定义认证服务
 * */
@Slf4j
@Service("securityProvider")
public class SecurityProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    public SecurityProvider(UserDetailsService userDetailsService) {  
        this.userDetailsService = userDetailsService;  
    }  
    @Override
    public Authentication authenticate(Authentication authenticate) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token
            = (UsernamePasswordAuthenticationToken) authenticate;
        String username = token.getName();
        UserDetails userDetails = null;
        
        if(username !=null) {
            userDetails = userDetailsService.loadUserByUsername(username);
        }

        log.info("----------------"+this.getClass().getName()
                    +"    authenticate    userDetails="+userDetails.toString());

        if(userDetails == null) {  
            throw new UsernameNotFoundException("用户名/密码无效");
        }
        
        else if (!userDetails.isEnabled()){  
            System.out.println("jinyong");
            log.info("----------------"+this.getClass().getName()
                +"    authenticate    用户已被禁用   "+userDetails.toString());
            throw new DisabledException("用户已被禁用");
        }else if (!userDetails.isAccountNonExpired()) {
            log.info("----------------"+this.getClass().getName()
                +"    authenticate    账号已过期   "+userDetails.toString());
            throw new LockedException("");
        }else if (!userDetails.isAccountNonLocked()) {
            log.info("----------------"+this.getClass().getName()
                +"    authenticate    账号已被锁定   "+userDetails.toString());
            throw new LockedException("账号已被锁定");
        }else if (!userDetails.isCredentialsNonExpired()) {
            log.info("----------------"+this.getClass().getName()
                +"    authenticate    凭证已过期   "+userDetails.toString());
            throw new LockedException("凭证已过期");
        }  
        
        String password =userDetails.getPassword();

         //与authentication里面的credentials相比较  
        if(!password.equals(token.getCredentials())) {  
            throw new BadCredentialsException("Invalid username/password");
        }  
        //授权  
        return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());  
    }

    @Override
    public boolean supports(Class<?> authentication) {
         //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型  
        return UsernamePasswordAuthenticationToken.class.equals(authentication);  
    }

}