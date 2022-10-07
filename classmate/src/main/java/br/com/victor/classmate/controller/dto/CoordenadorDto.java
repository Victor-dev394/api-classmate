package br.com.victor.classmate.controller.dto;

import org.springframework.data.domain.Page;

import br.com.victor.classmate.model.Coordenador;

public class CoordenadorDto {
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	
	public CoordenadorDto(Coordenador coordenador) {
		this.id = coordenador.getId();
		this.nome = coordenador.getNome();
		this.email = coordenador.getPerfil().getEmail();
		this.senha = coordenador.getPerfil().getSenha();
		this.telefone = coordenador.getTelefone();
	}
	
	public static Page<CoordenadorDto> converter(Page<Coordenador> coordenadores) {
		return coordenadores.map(CoordenadorDto::new);
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
