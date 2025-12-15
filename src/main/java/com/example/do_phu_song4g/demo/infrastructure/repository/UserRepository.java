package com.example.do_phu_song4g.demo.infrastructure.repository;

import com.example.do_phu_song4g.demo.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findBySoDienThoai(String soDienThoai);
    Optional<User> findByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);
    boolean existsByEmail(String email);
}