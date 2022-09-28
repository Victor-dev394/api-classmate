package br.com.victor.classmate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Nota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private int nota;
	private String descricao;
	
	@ManyToOne
	private Aluno aluno;
	
	public Nota() {
		
	}

	public Nota(String titulo, int numero, String descricao, Aluno aluno) {
		this.titulo = titulo;
		this.nota = numero;
		this.descricao = descricao;
		this.aluno = aluno;
	}

	public Long getId() {
		return id;
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

	public Aluno getAluno() {
		return aluno;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
}
