package com.gabriel.notesapp.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel.notesapp.domain.user.User;
import com.gabriel.notesapp.domain.user.UserRoles;
import com.gabriel.notesapp.exception.JWTCreationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;
    @Mock
    TokenService tokenServiceMock;

    @Test
    @DisplayName("generateToken returns token when Successfull")
    void generateToken_ReturnsToken_WhenSuccessfull() {
        User validUser = new User(1L, "test", "test", UserRoles.ADMIN);
        Assertions.assertThat(tokenService.generateToken(validUser))
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThatCode(() -> tokenService.generateToken(validUser))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("generateToken throws JWTCreationException when token is not created")
    void generateToken_ThrowsJWTCreationException_WhenTokenIsNotCreated() {
        User validUser = new User(1L, "test", "test", UserRoles.ADMIN);

        BDDMockito.when(tokenServiceMock.generateToken(validUser)).thenThrow(JWTCreationException.class);

        Assertions.assertThatExceptionOfType(JWTCreationException.class)
                .isThrownBy(() -> tokenServiceMock.generateToken(validUser));

    }

    @Test
    @DisplayName("validateToken returns token when Successfull")
    void validateToken_ReturnsUser_WhenSuccessfull() {
        User validUser = new User(1L, "test", "test", UserRoles.ADMIN);
        BDDMockito.when(tokenServiceMock.validateToken(ArgumentMatchers.anyString()))
                .thenReturn(String.valueOf(validUser));

        Assertions.assertThat(tokenServiceMock.validateToken("sdasd"))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(String.valueOf(validUser));

        Assertions.assertThatCode(() -> tokenServiceMock.validateToken("asd"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("validateToken throws JWTVerificationException when token is invalid")
    void validateToken_ThrowsJWTVerificationException_WhenTokenIsInvalid() {
        BDDMockito.when(tokenServiceMock.validateToken(ArgumentMatchers.anyString())).thenThrow(JWTVerificationException.class);

        Assertions.assertThatExceptionOfType(JWTVerificationException.class)
                .isThrownBy(() -> tokenService.validateToken("asdasd"));

    }

}