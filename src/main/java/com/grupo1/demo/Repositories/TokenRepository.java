package com.grupo1.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo1.demo.Models.Token;

@Repository
public interface TokenRepository  extends JpaRepository<Token, Long> {
    Token findByUser_username(String username);
    Token findByToken(String token);
}
