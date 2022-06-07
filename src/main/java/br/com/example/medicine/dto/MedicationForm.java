package br.com.example.medicine.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.medicine.model.Medicine;

public class MedicationForm {

	@NotBlank
	private String name;
	

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Medicine converter() {
		Medicine medicine = new Medicine();
		medicine.setname(name);
		return medicine;
	}
	
	
}
