package br.com.victor.classmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victor.classmate.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Optional<Perfil> findByEmail(String email);
}
