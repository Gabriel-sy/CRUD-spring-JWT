package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.user.UserDTO;
import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;


    @Test
    @DisplayName("register doesnt throw exception when successfull")
    void register_DoesntThrowException_WhenSuccessfull() {
        UserDTO validDTO = new UserDTO("test", "test");
        Assertions.assertThatCode(() -> userService.register(validDTO))
                .doesNotThrowAnyException();

        verify(userRepository).findByLogin(ArgumentMatchers.any());
        verify(userRepository).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("register throws EntityExistsException when user already exists")
    void register_ThrowsEntityExistsException_WhenUserAlreadyExists() {
        UserDTO validDTO = new UserDTO("test", "test");
        BDDMockito.when(userRepository.findByLogin(ArgumentMatchers.anyString()))
                        .thenThrow(EntityExistsException.class);

        Assertions.assertThatExceptionOfType(EntityExistsException.class)
                        .isThrownBy(() -> userService.register(validDTO));

        verify(userRepository).findByLogin(ArgumentMatchers.anyString());
        verify(userRepository, never()).save(ArgumentMatchers.any());
    }

}