package br.com.ruben.todoist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    @PostMapping("/")
    public void create(@RequestBody UserModel userModel){
        System.out.println(userModel.getName());
    }
}
