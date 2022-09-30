package br.com.victor.classmate.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.victor.classmate.model.Aluno;
import br.com.victor.classmate.model.Nota;


public class NotaForm {
	@NotBlank
	private String titulo;
	@NotBlank
	private int nota;
	@NotBlank
	private String descricao;
	
	public Nota converter(Aluno aluno) {
		return new Nota(titulo, nota, descricao, aluno);
	}
	
	public Nota atualizar(Nota nota, Aluno aluno) {
		nota.setTitulo(titulo);
		nota.setNota(this.nota);
		nota.setDescricao(descricao);
		nota.setAluno(aluno);

		return nota;
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
	
}
