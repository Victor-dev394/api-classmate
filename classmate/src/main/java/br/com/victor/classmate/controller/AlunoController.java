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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.victor.classmate.controller.dto.AlunoDto;
import br.com.victor.classmate.controller.form.AlunoForm;
import br.com.victor.classmate.model.Aluno;
import br.com.victor.classmate.model.Curso;
import br.com.victor.classmate.model.Perfil;
import br.com.victor.classmate.repository.AlunoRepository;
import br.com.victor.classmate.repository.CursoRepository;
import br.com.victor.classmate.repository.PerfilRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired 
	private CursoRepository cursoRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeAlunos")
	public Page<AlunoDto> listar(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		Page<Aluno> alunos = alunoRepository.findAll(paginacao);
		return AlunoDto.converter(alunos);
	}
	
	@PostMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAlunos", allEntries = true)
	public ResponseEntity<AlunoDto> cadastrar(@PathVariable Long id, @RequestBody @Valid AlunoForm form, 
			UriComponentsBuilder uriBuilder) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if(curso.isPresent()) {
			BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
			Perfil perfil = new Perfil(form.getEmail(), enconder.encode(form.getSenha()));
			perfilRepository.save(perfil);
			Aluno aluno = form.converter(curso.get(), perfil);
			alunoRepository.save(aluno);
			
			URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(uri).body(new AlunoDto(aluno));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAlunos", allEntries = true)
	public ResponseEntity<AlunoDto> atualizar(@PathVariable Long id, @RequestParam(required = false) Long idCurso,
			@RequestBody @Valid AlunoForm form) {
		Optional<Aluno> alunoOptional = alunoRepository.findById(id);
		Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
		if(alunoOptional.isPresent() && cursoOptional.isPresent()) {
			Aluno aluno = form.atualizar(alunoOptional.get(), cursoOptional.get());
			return ResponseEntity.ok(new AlunoDto(aluno));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAlunos", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Aluno> alunoOptional = alunoRepository.findById(id);
		if(alunoOptional.isPresent()) {
			Aluno aluno = alunoOptional.get();
			perfilRepository.deleteById(aluno.getPerfil().getId()); 
			alunoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
