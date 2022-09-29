package br.com.victor.classmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victor.classmate.model.Coordenador;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long>{

}
