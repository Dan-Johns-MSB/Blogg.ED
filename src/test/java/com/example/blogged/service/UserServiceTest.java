package com.example.blogged.service;

import com.example.blogged.model.User;
import com.example.blogged.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("rawPassword");
        user.setSecretAnswer("blue");
    }

    @Test
    void testRegisterUser_ShouldEncodePasswordAndSave() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.registerUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetPassword_Successful() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        Optional<User> result = userService.resetPassword("test@example.com", "blue", "newPassword");

        assertTrue(result.isPresent());
        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetPassword_WrongAnswer() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.resetPassword("test@example.com", "wrongAnswer", "newPassword");

        assertFalse(result.isPresent());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testResetPassword_UserNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.resetPassword("notfound@example.com", "blue", "newPassword");

        assertFalse(result.isPresent());
        verify(userRepository, never()).save(any());
    }
}
