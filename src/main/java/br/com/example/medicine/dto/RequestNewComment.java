package br.com.example.medicine.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.medicine.model.Comment;

public class RequestNewComment {

	@NotBlank
	private String pergunta;
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Comment toComment() {
		
		Comment doubt = new Comment();
		doubt.setPergunta(pergunta);
		
		return doubt;
	}
	
}
