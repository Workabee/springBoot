package com.boot.web.user;

import com.boot.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by abee on 17-3-13.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/addUser")
    public Object addUser() {

        //添加用户
        userService.createUser("OXOXOXO", 10);

        return null;
    }

}
