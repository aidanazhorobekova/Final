package br.com.example.iHealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;


@Entity
public class Medicine implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 40)
	private String name;
	
	private byte[] imagem;

	
	@ManyToMany
	@JoinTable(
			name="postos_medicines",
			uniqueConstraints = @UniqueConstraint(columnNames = { "medicine_id", "posto_id" }),
			joinColumns = @JoinColumn(name = "medicine_id"),
			inverseJoinColumns = @JoinColumn(name = "posto_id")
			)	
	private List<Posto> postos;
	
	@ManyToMany
	@JoinTable(
			name="requests_medicines",
			uniqueConstraints = @UniqueConstraint(columnNames = { "medicine_id", "request_id" }),
			joinColumns = @JoinColumn(name = "medicine_id"),
			inverseJoinColumns = @JoinColumn(name = "request_id")
			)	
	private List<Request> requests;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		this.postos = postos;
	}


	
	
	
}
