package com.ISITCOM.allotaxii.Bean;

public class Favoris {

	private int idfavoris;
	private int idchau;
	private int idclient;
	private int idtaxi;

	public Favoris() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Favoris(int idchau, int idclient, int idtaxi) {
		super();
		this.idchau = idchau;
		this.idclient = idclient;
		this.idtaxi = idtaxi;
	}

	public int getIdfavoris() {
		return idfavoris;
	}

	public void setIdfavoris(int idfavoris) {
		this.idfavoris = idfavoris;
	}

	public int getIdchau() {
		return idchau;
	}

	public void setIdchau(int idchau) {
		this.idchau = idchau;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public int getIdtaxi() {
		return idtaxi;
	}

	public void setIdtaxi(int idtaxi) {
		this.idtaxi = idtaxi;
	}

	@Override
	public String toString() {
		return "Favoris [idfavoris=" + idfavoris + ", idchau=" + idchau
				+ ", idclient=" + idclient + ", idtaxi=" + idtaxi + "]";
	}

}
