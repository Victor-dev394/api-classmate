package br.com.victor.classmate.controller.form;

import br.com.victor.classmate.model.Coordenador;
import br.com.victor.classmate.model.Curso;
import br.com.victor.classmate.model.Professor;

public class CursoForm {
	private String nome;
	private String descricao;
	
	public Curso converter(Coordenador coordenador, Professor professor) {
		return new Curso(nome, descricao, coordenador, professor);
	}
	
	public Curso atualizar(Curso curso, Coordenador coordenador, Professor professor) {
		curso.setNome(nome);
		curso.setDescricao(descricao);
		curso.setCoordenador(coordenador);
		curso.setProfessor(professor);
		
		return curso;
	}
	
	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	
}
