package com.eshop.eshop.rest;

//import com.eshop.eshop.entity.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserRestController {
/*    private List<UserEntity> USERS = Stream.of(
            new UserEntity(),
            new UserEntity(),
            new UserEntity()
    ).collect(Collectors.toList());
    @GetMapping
    public List<UserEntity> getAll(){
        return null;
    }
    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable Long id){
        return USERS.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }*/
}
