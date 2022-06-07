package br.com.example.medicine.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.medicine.model.Region;

public class RegionForm {
	
	@NotBlank
	private String name;

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public Region converter() {
		Region region = new Region();
		region.setname(name);
		return region;
	}
}
