package com.ISITCOM.allotaxii.Bean;

public class Taxi {

	private Long id; // Identifiant formation (Clé primaire)
	
	private String numvh;
	private String photo;

	
	public Taxi(String numvh, String photo) {
		super();
		this.numvh = numvh;
		this.photo = photo;
	}

	public Taxi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setNumerovehicule(String numerovehicule) {
		this.numvh = numerovehicule;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNumerovehicule() {
		return numvh;
	}

}
