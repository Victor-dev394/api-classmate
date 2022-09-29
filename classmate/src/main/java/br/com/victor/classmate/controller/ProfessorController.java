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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.victor.classmate.controller.dto.ProfessorDto;
import br.com.victor.classmate.controller.form.ProfessorForm;
import br.com.victor.classmate.model.Perfil;
import br.com.victor.classmate.model.Professor;
import br.com.victor.classmate.repository.PerfilRepository;
import br.com.victor.classmate.repository.ProfessorRepository;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired 
	private PerfilRepository perfilRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeProfessores")
	public Page<ProfessorDto> listar(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		Page<Professor> professores = professorRepository.findAll(paginacao);
		return ProfessorDto.converter(professores);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeProfessores", allEntries = true)
	public ResponseEntity<ProfessorDto> cadastrar(@RequestBody @Valid ProfessorForm form, UriComponentsBuilder uriBuilder) {
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		Perfil perfil = new Perfil(form.getEmail(), enconder.encode(form.getSenha()));
		perfilRepository.save(perfil);
		Professor professor = form.converter(perfil);
		professorRepository.save(professor);
		
		URI uri = uriBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProfessorDto(professor));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeProfessores", allEntries = true)
	public ResponseEntity<ProfessorDto> atualizar(@PathVariable Long id, @RequestBody @Valid ProfessorForm form) {
		Optional<Professor> professorOptional = professorRepository.findById(id);
		if(professorOptional.isPresent()) {
			Professor professor = form.atualizar(professorOptional.get());
			return ResponseEntity.ok(new ProfessorDto(professor));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeProfessores", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Professor> optional = professorRepository.findById(id);
		if(optional.isPresent()) {
			Professor professor = optional.get();
			perfilRepository.deleteById(professor.getPerfil().getId()); 
			professorRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
