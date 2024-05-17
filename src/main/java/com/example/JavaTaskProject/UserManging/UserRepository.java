package com.example.JavaTaskProject.UserManging;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // by naming findBy(attribute in the related class)
    Optional<User> findByEmail(String email);

}
