package br.com.example.medicine.model;

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
	
	private byte[] image;

	
	@ManyToMany
	@JoinTable(
			name="posts_medicines",
			uniqueConstraints = @UniqueConstraint(columnNames = { "medicine_id", "post_id" }),
			joinColumns = @JoinColumn(name = "medicine_id"),
			inverseJoinColumns = @JoinColumn(name = "post_id")
			)	
	private List<Post> posts;
	
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
		return image;
	}

	public void setImagem(byte[] image) {
		this.image = image;
	}

	public List<Post> getposts() {
		return posts;
	}

	public void setposts(List<Post> posts) {
		this.posts = posts;
	}


	
	
	
}
