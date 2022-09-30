package br.com.victor.classmate.controller.dto;

import org.springframework.data.domain.Page;

import br.com.victor.classmate.model.Nota;

public class NotaDto {
	private String titulo;
	private int nota;
	private String descricao;
	private Long idAluno;
	
	public NotaDto(Nota nota) {
		this.titulo = nota.getTitulo();
		this.nota = nota.getNota();
		this.descricao = nota.getDescricao();
		this.idAluno = nota.getAluno().getId();
	}
	
	public static Page<NotaDto> converter(Page<Nota> notas) {
		return notas.map(NotaDto::new);
	}
	
	public String getTitulo() {
		return titulo;
	}

	public int getNota() {
		return nota;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getIdAluno() {
		return idAluno;
	}
	
}
