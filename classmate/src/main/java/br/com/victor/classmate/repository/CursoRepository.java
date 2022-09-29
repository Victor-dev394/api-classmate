package br.com.victor.classmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victor.classmate.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{

}
