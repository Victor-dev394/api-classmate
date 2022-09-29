package br.com.victor.classmate.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Professor extends Usuario {
	@OneToMany
	private List<Curso> cursos = new ArrayList<Curso>();
	
	@OneToOne
	private Perfil perfil;
	
	public Professor() {
		
	}
	
	public Professor(String nome, String telefone, Perfil perfil) {
		super(nome, telefone);
		this.perfil = perfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	
}
