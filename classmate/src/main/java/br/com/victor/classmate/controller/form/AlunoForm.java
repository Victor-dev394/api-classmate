package br.com.victor.classmate.controller.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.victor.classmate.model.Aluno;
import br.com.victor.classmate.model.Curso;
import br.com.victor.classmate.model.Perfil;

public class AlunoForm {
	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotBlank
	private String senha;
	@NotBlank
	private String telefone;
	
	public AlunoForm(String nome, String email, String senha, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
	}
	
	public Aluno converter(Curso curso, Perfil perfil) {
		return new Aluno(nome, telefone, curso, perfil);
	}

	public Aluno atualizar(Aluno aluno, Curso curso) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		aluno.setNome(this.nome);
		aluno.getPerfil().setEmail(this.email);
		aluno.getPerfil().setSenha(encoder.encode(this.senha));
		aluno.setTelefone(this.telefone);
		aluno.setCurso(curso);
		
		return aluno;
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
