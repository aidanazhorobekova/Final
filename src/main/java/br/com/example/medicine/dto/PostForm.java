package br.com.example.medicine.dto;

import javax.validation.constraints.NotBlank;

import br.com.example.medicine.model.Post;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.repository.RegionRepository;

public class PostForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	@NotBlank
	private String urlMapa;
	
	@NotBlank
	private String nameRegion;

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

	public String getnameRegion() {
		return nameRegion;
	}

	public void setnameRegion(String nameRegion) {
		this.nameRegion = nameRegion;
	}

	public Post converter(RegionRepository regionRepository) {
		Post post = new Post();
		post.setaddress(address);
		post.setname(name);
		post.setUrlMapa(urlMapa);
		Region region = regionRepository.findByname(nameRegion);
		post.setRegion(region);
		return post;
	}
}
