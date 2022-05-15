package com.eshop.eshop.rest;

import com.eshop.eshop.dto.RoleUserDTO;
import com.eshop.eshop.dto.UserDto;
import com.eshop.eshop.entity.User;
import com.eshop.eshop.service.UserService;
import liquibase.pro.packaged.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {
    private final UserService userService;
    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id")Long id){
        User user = userService.findById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "addroleforuser/")
    public ResponseEntity addRoleForUser(@RequestBody RoleUserDTO roleUserDTO){
        if(roleUserDTO == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.addRole(roleUserDTO.getUserId(), roleUserDTO.getRoleId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
