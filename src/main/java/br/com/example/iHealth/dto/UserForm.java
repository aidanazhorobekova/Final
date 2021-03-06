package br.com.example.iHealth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.RegiaoRepository;

public class UserForm {

	@NotBlank
	@Length(min = 2, max=30)
	private String name;
	
	@NotBlank
	@Length(min = 2, max=30)
	private String last_name;
	
	@NotBlank
	@Length(min = 15, max=15)
	private String codSus;
	
	@NotBlank
	@Length(min = 8, max=50)
	private String email;
	
	@NotBlank
	@Length(min = 11, max=11)
	private String cpf;
	
	@NotBlank
	@Length(min = 3, max=10)
	private String password;
	
	@NotBlank
	@Length(min = 10, max=100)
	private String address;
	
	@NotBlank @NotNull
	private String regiao;
	

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getlast_name() {
		return last_name;
	}

	public void setlast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCodSus() {
		return codSus;
	}

	public void setCodSus(String codSus) {
		this.codSus = codSus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public User converter(RegiaoRepository regiaoRepository) {
		User user = new User();
		user.setname(name);
		user.setlast_name(last_name);
		user.setCodSus(codSus);
		user.setCpf(cpf);
		user.setAtivo(true);
		user.setEmail(email);
		user.setaddress(address);
		user.setPassword(password);
		Regiao userRegiao = regiaoRepository.findByname(regiao);
		user.setRegiao(userRegiao);
		return user;
	}

}
