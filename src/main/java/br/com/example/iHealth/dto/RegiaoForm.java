package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.iHealth.model.Regiao;

public class RegiaoForm {
	
	@NotBlank
	private String name;

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public Regiao converter() {
		Regiao regiao = new Regiao();
		regiao.setname(name);
		return regiao;
	}
}
