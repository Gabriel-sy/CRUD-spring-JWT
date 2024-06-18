package com.gabriel.notesapp.controller;

import com.gabriel.notesapp.domain.user.UserDTO;
import com.gabriel.notesapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register")
    public ModelAndView registerPage(){
        return new ModelAndView("/register/index.html");
    }

    @GetMapping(path = "/login")
    public ModelAndView loginPage(){
        return new ModelAndView("/login/index.html");
    }

    @PostMapping(path = "/api/register")
    public ResponseEntity<Void> registerApi(@RequestBody @Valid UserDTO user){
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/api/login")
    public ResponseEntity<Void> loginApi(@RequestBody UserDTO user, HttpServletResponse response){
        userService.login(user, response);
        return ResponseEntity.ok().build();
    }
}
