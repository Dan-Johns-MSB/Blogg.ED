
package com.example.simpleblog.repository;

import com.example.simpleblog.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
