package com.eshop.eshop.rest;

import com.eshop.eshop.dto.AuthenticationRequestDto;
import com.eshop.eshop.entity.User;
import com.eshop.eshop.security.jwt.JwtTokenProvider;
import com.eshop.eshop.service.UserService;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) throws Exception {
        try{
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            if(user == null){
                throw new UsernameNotFoundException("User with login "+username+" not found");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username",username);
            response.put("token",token);
            return ResponseEntity.ok(response);
        }
        catch(AuthenticationServiceException ex){
            throw new BadCredentialsException("Invalid login or password");
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    @PostMapping("register")
    public ResponseEntity register(@RequestBody AuthenticationRequestDto requestDto) throws Exception {
        try{
            User user = new User();
            user.setUsername(requestDto.getUsername());
            user.setPassword(requestDto.getPassword());
            userService.register(user);
            userService.addRole(user.getId(), 1L);
            return ResponseEntity.ok(user.getUsername());
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
