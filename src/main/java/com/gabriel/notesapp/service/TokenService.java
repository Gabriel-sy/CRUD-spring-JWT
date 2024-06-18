package com.gabriel.notesapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel.notesapp.domain.user.User;
import com.gabriel.notesapp.exception.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    public String generateToken(User user){
        final String secret = "123";
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.create()
                    .withIssuer("notesapp")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getTokenExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new JWTCreationException("Erro ao gerar token");
        }
    }

    public String validateToken(String token){
        final String secret = "123";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("notesapp")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Token invalido");
        }
    }

    public Instant getTokenExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
