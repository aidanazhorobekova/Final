package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.iHealth.model.Posto;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.repository.RegiaoRepository;

public class PostoForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	@NotBlank
	private String urlMapa;
	
	@NotBlank
	private String nameRegiao;

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public String getUrlMapa() {
		return urlMapa;
	}

	public void setUrlMapa(String urlMapa) {
		this.urlMapa = urlMapa;
	}

	public String getnameRegiao() {
		return nameRegiao;
	}

	public void setnameRegiao(String nameRegiao) {
		this.nameRegiao = nameRegiao;
	}

	public Posto converter(RegiaoRepository regiaoRepository) {
		Posto posto = new Posto();
		posto.setaddress(address);
		posto.setname(name);
		posto.setUrlMapa(urlMapa);
		Regiao regiao = regiaoRepository.findByname(nameRegiao);
		posto.setRegiao(regiao);
		return posto;
	}
}
