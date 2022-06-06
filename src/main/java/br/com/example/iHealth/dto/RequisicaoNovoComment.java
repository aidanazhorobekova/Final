package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.iHealth.model.Comment;

public class RequisicaoNovoComment {

	@NotBlank
	private String pergunta;
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Comment toComment() {
		
		Comment duvida = new Comment();
		duvida.setPergunta(pergunta);
		
		return duvida;
	}
	
}
