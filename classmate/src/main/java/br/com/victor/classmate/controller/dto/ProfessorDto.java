package br.com.victor.classmate.controller.dto;

import org.springframework.data.domain.Page;

import br.com.victor.classmate.model.Professor;

public class ProfessorDto {
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	
	public ProfessorDto(Professor professor) {
		this.id = professor.getId();
		this.nome = professor.getNome();
		this.email = professor.getPerfil().getEmail();
		this.senha = professor.getPerfil().getSenha();
		this.telefone = professor.getTelefone();
	}
	
	public static Page<ProfessorDto> converter(Page<Professor> professores) {
		return professores.map(ProfessorDto::new);
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getTelefone() {
		return telefone;
	}

}
