package br.com.victor.classmate.controller.dto;

import org.springframework.data.domain.Page;

import br.com.victor.classmate.model.Curso;

public class CursoDto {
	private Long id;
	private String nome;
	private String descricao;
	private Long idCoordenador;
	private Long idProfessor;
	
	public CursoDto(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.descricao = curso.getDescricao();
		this.idCoordenador = curso.getCoordenador().getId();
		this.idProfessor = curso.getProfessor().getId();
	}
	
	public static Page<CursoDto> converter(Page<Curso> cursos) {
		return cursos.map(CursoDto::new);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getIdCoordenador() {
		return idCoordenador;
	}

	public Long getIdProfessor() {
		return idProfessor;
	}
	
}
