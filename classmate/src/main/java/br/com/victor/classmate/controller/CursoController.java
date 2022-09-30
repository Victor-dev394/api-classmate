package br.com.victor.classmate.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.victor.classmate.controller.dto.CursoDto;
import br.com.victor.classmate.controller.form.CursoForm;
import br.com.victor.classmate.model.Coordenador;
import br.com.victor.classmate.model.Curso;
import br.com.victor.classmate.model.Professor;
import br.com.victor.classmate.repository.CoordenadorRepository;
import br.com.victor.classmate.repository.CursoRepository;
import br.com.victor.classmate.repository.ProfessorRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private CoordenadorRepository coordenadorRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeCursos")
	public Page<CursoDto> listar(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		Page<Curso> cursos = cursoRepository.findAll(paginacao);
		return CursoDto.converter(cursos);
	}
	
	@PostMapping("/{idCoordenador}")
	@Transactional
	@CacheEvict(value = "listaDeCursos", allEntries = true)
	public ResponseEntity<CursoDto> cadastrar(@PathVariable Long idCoordenador, @RequestParam(required = false) Long idProfessor,
			@RequestBody @Valid CursoForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Coordenador> coordenadorOptional = coordenadorRepository.findById(idCoordenador);
		Optional<Professor> professorOptional = professorRepository.findById(idProfessor);
		
		if(coordenadorOptional.isPresent() && professorOptional.isPresent()) {
			Curso curso = form.converter(coordenadorOptional.get(), professorOptional.get());
			cursoRepository.save(curso);
			
			URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
			return ResponseEntity.created(uri).body(new CursoDto(curso));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCursos", allEntries = true)
	public ResponseEntity<CursoDto> atualizar(@PathVariable Long id, @RequestParam(required = false) Long idCoordenador,
			@RequestParam(required = false) Long idProfessor, @RequestBody @Valid CursoForm form) {
		
		Optional<Curso> cursoOptional = cursoRepository.findById(id);
		Optional<Coordenador> coordenadorOptional = coordenadorRepository.findById(idCoordenador);
		Optional<Professor> professorOptional = professorRepository.findById(idProfessor);
		
		if(cursoOptional.isPresent() && coordenadorOptional.isPresent() && professorOptional.isPresent()) {
			Curso curso = form.atualizar(cursoOptional.get(), coordenadorOptional.get(), professorOptional.get());
			return ResponseEntity.ok(new CursoDto(curso));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCursos", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		
		if(curso.isPresent()) {
			cursoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
