
package com.example.simpleblog.controller;

import com.example.simpleblog.model.Comment;
import com.example.simpleblog.model.Post;
import com.example.simpleblog.model.User;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "home";
    }

    @GetMapping("/post/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "new-post";
    }

    @PostMapping("/post/new")
    public String createPost(@ModelAttribute Post post, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> post.setAuthor(value));
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> model.addAttribute("post", p));
        model.addAttribute("comment", new Comment());
        return "post";
    }

    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute Comment comment,
                             Authentication authentication) {
        Optional<Post> post = postRepository.findById(id);
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (post.isPresent() && user.isPresent()) {
            comment.setPost(post.get());
            comment.setAuthor(user.get());
            comment.setCreatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }
        return "redirect:/post/" + id;
    }
}
