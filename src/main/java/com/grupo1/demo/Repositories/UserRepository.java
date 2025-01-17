package com.grupo1.demo.Repositories;


import com.grupo1.demo.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
/**
 * Interface UserRepository que extiende de JpaRepository
 */
public interface UserRepository  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findOptionalByUsername(@Param("email") String email);
    Usuario findByFirstName (String firstName);
    Usuario findByUsername (String username);
}
