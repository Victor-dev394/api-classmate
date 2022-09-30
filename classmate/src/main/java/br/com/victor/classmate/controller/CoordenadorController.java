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

import br.com.victor.classmate.controller.dto.CoordenadorDto;
import br.com.victor.classmate.controller.form.CoordenadorForm;
import br.com.victor.classmate.model.Coordenador;
import br.com.victor.classmate.model.Perfil;
import br.com.victor.classmate.repository.CoordenadorRepository;
import br.com.victor.classmate.repository.PerfilRepository;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {
	@Autowired
	private CoordenadorRepository cursoRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeCoordenadores")
	public Page<CoordenadorDto> listar(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		Page<Coordenador> coordenadores = cursoRepository.findAll(paginacao);
		return CoordenadorDto.converter(coordenadores);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeCoordenadores", allEntries = true)
	public ResponseEntity<CoordenadorDto> cadastrar(@RequestBody @Valid CoordenadorForm form, UriComponentsBuilder uriBuilder) {
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder(); 
		Perfil perfil = new Perfil(form.getEmail(), enconder.encode(form.getSenha()));
		perfilRepository.save(perfil);
		Coordenador coordenador = form.converter(perfil);
		cursoRepository.save(coordenador);
		
		URI uri = uriBuilder.path("/coordenadores/{id}").buildAndExpand(coordenador.getId()).toUri();
		return ResponseEntity.created(uri).body(new CoordenadorDto(coordenador));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCoordenadores", allEntries = true)
	public ResponseEntity<CoordenadorDto> atualizar(@PathVariable Long id, @RequestBody @Valid CoordenadorForm form) {
		Optional<Coordenador> optional = cursoRepository.findById(id);
		if(optional.isPresent()) {
			Coordenador coordenador = form.atualizar(optional.get());
			return ResponseEntity.ok(new CoordenadorDto(coordenador));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCoordenadores", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Coordenador> optional = cursoRepository.findById(id);
		if(optional.isPresent()) {
			Coordenador coordenador = optional.get();
			perfilRepository.deleteById(coordenador.getPerfil().getId());
			cursoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
