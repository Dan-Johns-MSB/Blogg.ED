
package com.example.blogged.repository;

import com.example.blogged.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorUsername(String username);
}
