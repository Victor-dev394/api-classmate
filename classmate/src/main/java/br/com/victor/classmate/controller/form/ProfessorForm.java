package br.com.victor.classmate.controller.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.victor.classmate.model.Perfil;
import br.com.victor.classmate.model.Professor;

public class ProfessorForm {
	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotBlank
	private String senha;
	@NotBlank
	private String telefone;
	
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
	
	public Professor converter(Perfil perfil) {
		return new Professor(nome,telefone,perfil);
	}

	public Professor atualizar(Professor professor) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		professor.setNome(this.nome);
		professor.getPerfil().setEmail(this.email);
		professor.getPerfil().setSenha(encoder.encode(this.senha));
		professor.setTelefone(this.telefone);

		return professor;
	}
	
}
