package com.llucena.springboot.backend.apirestb.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="customers")
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	@Size(min=4, max=55, message= "Size must be between 4 and 55")
	@NotEmpty(message="Cannot be empty")
	private String name;
	
	@NotEmpty
	private String lastname;
	@NotEmpty(message="Cannot be empty")
	@Email(message="Please enter a correct format EJ 'email@hotmail.com' ")
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotNull
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	
	private String photo;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Region region; 
	
	
	/*@PrePersist
	public void prePersist() {
		createAt = new Date();
	}*/
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	

	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}



	private static final long serialVersionUID = 1L;	
}
