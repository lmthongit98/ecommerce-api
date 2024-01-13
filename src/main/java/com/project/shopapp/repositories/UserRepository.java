package com.project.shopapp.repositories;

import com.project.shopapp.models.Permission;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT p FROM User u JOIN u.role r JOIN r.permissions p WHERE u.phoneNumber = :phoneNumber")
    List<Permission> findAllPermissions(String phoneNumber);

    @Query("SELECT u FROM User u JOIN FETCH u.role r LEFT JOIN FETCH r.permissions WHERE u.phoneNumber = :phoneNumber")
    Optional<User> findUserWithRoleAndPermissions(String phoneNumber);

}
