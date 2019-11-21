package com.example.demo.security;



import com.example.demo.dao.UserRepository;
import com.example.demo.dto.MyUserDetails;
import com.example.demo.dto.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userDetailsService")
public class AuthUserDetailService implements UserDetailsService {

      @Autowired
      private UserRepository userRepository;

      @Override
      public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
      // 1. 查询用户
      User userFromDatabase =userRepository.findOneByLogin(login);
      if (userFromDatabase == null) {
      //log.warn("User: {} not found", login);
      throw new UsernameNotFoundException("User " + login + " was not found in db");
      //这里找不到必须抛异常
      }
      userFromDatabase.setPassword(new BCryptPasswordEncoder().encode(userFromDatabase.getPassword()));
      // 2. 设置角色
      Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userFromDatabase.getRole());
      grantedAuthorities.add(grantedAuthority);

      log.info("----------------"+this.getClass().getName()+"   loadUserByUsername     姓名:"+login+";  加密前密码:"+userFromDatabase.getPassword()+

          ";加密后密码:"+new BCryptPasswordEncoder().encode(userFromDatabase.getPassword())+

          ";角色:"+userFromDatabase.getRole()+"; 加密后角色:"+grantedAuthorities.toString());

      UserDetails  userDetails = new MyUserDetails(userFromDatabase, grantedAuthorities);

      return userDetails;


            //return new org.springframework.security.core.userdetails.User(login,
        //  new BCryptPasswordEncoder().encode(userFromDatabase.getPassword()), grantedAuthorities);
      }
}