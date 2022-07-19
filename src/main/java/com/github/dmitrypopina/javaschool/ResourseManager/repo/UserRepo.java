package com.github.dmitrypopina.javaschool.ResourseManager.repo;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User, Long> {
    public User findByUsername(String name);
    boolean existsByUsername(String username);
}
