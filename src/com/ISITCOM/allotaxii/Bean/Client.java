package com.ISITCOM.allotaxii.Bean;

public class Client {

	private int idclient;
	
	private String adresse;
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String tel;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Client(String adresse, String login, String password, String nom,
			String prenom, String tel) {
		super();
		this.adresse = adresse;
		this.login = login;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
	}


	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
