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
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class Posto implements Serializable{

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String name;
	
	@Column(nullable = false, unique = true, length = 200)
	private String address;

	@ManyToOne(optional = false)
	private Regiao regiao;

	@ManyToMany
	@JoinTable(
			name="postos_medicines",
			uniqueConstraints = @UniqueConstraint(columnNames = { "medicine_id", "posto_id" }),
			joinColumns = @JoinColumn(name = "posto_id"),
			inverseJoinColumns = @JoinColumn(name = "medicine_id")
			)	
	private List<Medicine> medicines;
	
	
	private byte[] urlImagemPosto;
	
	@Column(nullable = true, unique = true, length = 2000)
	private String urlMapa;

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

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	
	public List<Medicine> getMedicines() {
		return medicines;
	}
	
	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public byte[] getUrlImagemPosto() {
		return urlImagemPosto;
	}

	public void setUrlImagemPosto(byte[] urlImagemPosto) {
		this.urlImagemPosto = urlImagemPosto;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}
	

	public String getUrlMapa() {
		return urlMapa;
	}

	public void setUrlMapa(String urlMapa) {
		this.urlMapa = urlMapa;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
