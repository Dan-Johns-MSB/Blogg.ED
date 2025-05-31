
package com.example.blogged.controller;

import com.example.blogged.model.Profile;
import com.example.blogged.model.User;
import com.example.blogged.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        //user.ifPresent(u -> model.addAttribute("user", u));
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("profile", user.get().getProfile());
        }

        return "profile";
    }

    @GetMapping("/edit")
    public String editProfileForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            Profile profile = user.get().getProfile();
            if (profile == null) {
                profile = new Profile();
                profile.setUser(user.get());
            }
            model.addAttribute("profile", profile);
        }

        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute Profile profile, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            profile.setUser(user.get());
            user.get().setProfile(profile);
            userRepository.save(user.get());
        }

        return "redirect:/profile";
    }
}
