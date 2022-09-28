package br.com.victor.classmate.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Aluno extends Usuario {
	@ManyToOne
	private Curso curso;
	
	@OneToMany
	private List<Nota> notas = new ArrayList<Nota>();
	
	@OneToOne
	private Perfil perfil;
	
	public Aluno() {
		
	}
	
	public Aluno(String nome, String telefone, Curso curso, Perfil perfil) {
		super(nome, telefone);
		this.curso = curso;
		this.perfil = perfil;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Perfil getPerfil() {
		return perfil;
	}

}
