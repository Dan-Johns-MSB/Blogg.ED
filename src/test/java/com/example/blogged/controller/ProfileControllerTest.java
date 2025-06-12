package com.example.blogged.controller;

import com.example.blogged.model.Profile;
import com.example.blogged.model.User;
import com.example.blogged.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private ProfileController profileController;

    private User user;
    private Profile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testuser");

        profile = new Profile();
        profile.setFullName("Test User");
        profile.setBio("Bio");
        profile.setUser(user);

        user.setProfile(profile);
    }

    @Test
    void testUserProfile_UserExists() {
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        String viewName = profileController.userProfile(authentication, model);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("profile", profile);
        assertEquals("profile", viewName);
    }

    @Test
    void testEditProfileForm_ProfileIsNull() {
        user.setProfile(null);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        String viewName = profileController.editProfileForm(authentication, model);

        verify(model).addAttribute(eq("profile"), any(Profile.class));
        assertEquals("edit-profile", viewName);
    }
}
