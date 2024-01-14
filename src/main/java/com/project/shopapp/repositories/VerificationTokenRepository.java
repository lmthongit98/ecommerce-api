package com.project.shopapp.repositories;

import com.project.shopapp.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("SELECT t from VerificationToken t JOIN FETCH t.user WHERE t.token = :token")
    VerificationToken findByToken(String token);
}