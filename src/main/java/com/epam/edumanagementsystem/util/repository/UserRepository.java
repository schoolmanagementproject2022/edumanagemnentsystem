package com.epam.edumanagementsystem.util.repository;

import com.epam.edumanagementsystem.util.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM public.user_table WHERE LOWER(email) = LOWER(?1)",nativeQuery = true)
    Optional<User> findByEmail(String email);


}
