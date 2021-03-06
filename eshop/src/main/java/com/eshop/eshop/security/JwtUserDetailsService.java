package com.eshop.eshop.security;

import com.eshop.eshop.entity.User;
import com.eshop.eshop.security.jwt.JwtUser;
import com.eshop.eshop.security.jwt.JwtUserFactory;
import com.eshop.eshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with username: "+ username +" not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("Loaded user with login: "+jwtUser.getUsername());
        return jwtUser;
    }
}
