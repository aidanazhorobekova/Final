package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.iHealth.modelo.Remedio;

public class MedicamentoForm {

	@NotBlank
	private String name;
	

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Remedio converter() {
		Remedio remedio = new Remedio();
		remedio.setname(name);
		return remedio;
	}
	
	
}
