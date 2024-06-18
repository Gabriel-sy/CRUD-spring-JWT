package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.user.User;
import com.gabriel.notesapp.domain.user.UserDTO;
import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.exception.InternalAuthenticationServiceException;
import com.gabriel.notesapp.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public void register(UserDTO user) {
        if (userRepository.findByLogin(user.login()) == null){
            //Encriptando a senha
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
            User newUser = new User(user.login(), encryptedPassword);
            userRepository.save(newUser);
        } else {
            throw new EntityExistsException("Usúario existente");
        }

    }

    public void login(UserDTO user, HttpServletResponse response) {
        try {
            //Armazenando os dados do usuário para poder usar o método "authenticate"
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());
            //Autenticando usuário
            var auth = this.authenticationManager.authenticate(usernamePassword);
            //Criando token do usuário
            String token = tokenService.generateToken((User) auth.getPrincipal());
            //Armazenando token jwt em um cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(300);
            cookie.setPath("/");
            response.addCookie(cookie);

        } catch (AuthenticationException e) {
            throw new InternalAuthenticationServiceException("Falha ao autenticar, verifique se o login e a senha estão corretos ou se o formato está correto");
        }
    }
}
