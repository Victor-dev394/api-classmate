package br.com.victor.classmate.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	
	@OneToMany
	private List<Aluno> alunos = new ArrayList<Aluno>();
	@ManyToOne
	private Coordenador coordenador;
	@ManyToOne
	private Professor professor;
	
	public Curso() {
		
	}
	
	public Curso(String nome, String descricao, Coordenador coordenador, Professor professor) {
		this.nome = nome;
		this.descricao = descricao;
		this.coordenador = coordenador;
		this.professor = professor;
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

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
}
