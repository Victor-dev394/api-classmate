package br.com.victor.classmate.controller.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.victor.classmate.model.Coordenador;
import br.com.victor.classmate.model.Perfil;

public class CoordenadorForm {
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
	
	public Coordenador converter(Perfil perfil) {
		return new Coordenador(nome,telefone,perfil);
	}

	public Coordenador atualizar(Coordenador coordenador) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		coordenador.setNome(this.nome);
		coordenador.getPerfil().setEmail(this.email);
		coordenador.getPerfil().setSenha(encoder.encode(this.senha));
		coordenador.setTelefone(this.telefone);

		return coordenador;
	}
	
}
