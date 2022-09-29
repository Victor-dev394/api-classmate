package br.com.victor.classmate.controller.dto;

import org.springframework.data.domain.Page;

import br.com.victor.classmate.model.Aluno;

public class AlunoDto {
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private Long idCurso;
	
	public AlunoDto(Aluno aluno) {
		this.nome = aluno.getNome();
		this.email = aluno.getPerfil().getEmail();
		this.senha = aluno.getPerfil().getSenha();
		this.telefone = aluno.getTelefone();
		this.idCurso = aluno.getCurso().getId();
	}
	
	public static Page<AlunoDto> converter(Page<Aluno> alunos) {
		return alunos.map(AlunoDto::new);
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

	public Long getIdCurso() {
		return idCurso;
	}
	
}
