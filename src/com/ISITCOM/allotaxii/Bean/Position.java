package com.ISITCOM.allotaxii.Bean;

public class Position {
	private int idpos;
	private String langitude;
	private String latitude;
	private Taxi taxiId;

	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Position(String langitude, String latitude, Taxi taxiId) {
		super();
		this.langitude = langitude;
		this.latitude = latitude;
		this.taxiId = taxiId;
	}

	public int getIdpos() {
		return idpos;
	}

	public void setIdpos(int idpos) {
		this.idpos = idpos;
	}

	public String getLangitude() {
		return langitude;
	}

	public void setLangitude(String langitude) {
		this.langitude = langitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Taxi getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(Taxi taxiId) {
		this.taxiId = taxiId;
	}

}
