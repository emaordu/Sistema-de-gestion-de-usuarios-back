
package com.grupo1.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo1.demo.Models.Permisos;
import com.grupo1.demo.Models.Sistema;
import com.grupo1.demo.Models.Usuario;

@Repository
public interface PermisoRepository extends JpaRepository<Permisos, Long>{
    boolean existsByUsuarioAndSistema(Usuario usuario, Sistema sistemaId);
}