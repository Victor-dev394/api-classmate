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

import br.com.victor.classmate.controller.dto.NotaDto;
import br.com.victor.classmate.controller.form.NotaForm;
import br.com.victor.classmate.model.Aluno;
import br.com.victor.classmate.model.Nota;
import br.com.victor.classmate.repository.AlunoRepository;
import br.com.victor.classmate.repository.NotaRepository;

@RestController
@RequestMapping("/notas")
public class NotaController {
	@Autowired
	private NotaRepository notaRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeNotas")
	public Page<NotaDto> listar(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		Page<Nota> notas = notaRepository.findAll(paginacao);
		return NotaDto.converter(notas);
	}
	
	@PostMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeNotas", allEntries = true)
	public ResponseEntity<NotaDto> cadastrar(@PathVariable Long id, @RequestBody @Valid NotaForm form, 
			UriComponentsBuilder uriBuilder) {
		
		Optional<Aluno> aluno = alunoRepository.findById(id);
		if(aluno.isPresent()) {
			Nota nota = form.converter(aluno.get());
			notaRepository.save(nota);
			
			URI uri = uriBuilder.path("/notas/{id}").buildAndExpand(nota.getId()).toUri();
			return ResponseEntity.created(uri).body(new NotaDto(nota));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeNotas", allEntries = true)
	public ResponseEntity<NotaDto> atualizar(@PathVariable Long id, @RequestParam(required = false) Long idAluno,
			@RequestBody @Valid NotaForm form) {
		
		Optional<Nota> notaOptional = notaRepository.findById(id);
		Optional<Aluno> alunoOptional = alunoRepository.findById(idAluno);
		
		if(notaOptional.isPresent() && alunoOptional.isPresent()) {
			Nota nota = form.atualizar(notaOptional.get(), alunoOptional.get());
			return ResponseEntity.ok(new NotaDto(nota));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeNotas", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Nota> nota = notaRepository.findById(id);
		
		if(nota.isPresent()) {
			notaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
