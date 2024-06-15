package com.gabriel.notesapp.controller;

import com.gabriel.notesapp.domain.user.LoginDTO;
import com.gabriel.notesapp.domain.user.User;
import com.gabriel.notesapp.domain.user.UserDTO;
import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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
    public ResponseEntity<Void> registerApi(@RequestBody UserDTO user){
        if (userRepository.findByLogin(user.login()) != null){
            throw new EntityExistsException("Usúario existente");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
        User newUser = new User(user.login(), encryptedPassword, user.role());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/api/login")
    public ResponseEntity<Void> loginApi(@RequestBody LoginDTO user){
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());

        //Essa variavel authUser tá authenticando o usuário, o authenticationManager precisa
        // receber um "UsernamePasswordAuthenticationToken", por isso a variavel ali em cima,
        // ele authentica automaticamente, usando o que a gente colocou la no AuthenticationService
        this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }
}
