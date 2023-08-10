package br.fvc.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Domain.User.UserDTO;
import br.fvc.api.Services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String all() {
        return "olá";
    }

    @GetMapping("/address")
    public String address() {
        return "endereço";
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDTO data) {
        return userService.store(data);
    }

}
