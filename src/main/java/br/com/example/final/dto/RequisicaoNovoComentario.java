package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.iHealth.modelo.Comentario;

public class RequisicaoNovoComentario {

	@NotBlank
	private String pergunta;
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Comentario toComentario() {
		
		Comentario duvida = new Comentario();
		duvida.setPergunta(pergunta);
		
		return duvida;
	}
	
}
