package it.polito.tdp.meteo.model;

import java.util.Date;

public class Rilevamento {
	
	private String localita;
	private Date data;
	private double umidita;

	public Rilevamento(String localita, Date data, double umidita) {
		this.localita = localita;
		this.data = data;
		this.umidita = umidita;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}

	// @Override
	// public String toString() {
	// return localita + " " + data + " " + umidita;
	// }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-8s ",this.localita));
		sb.append(String.format("%-12f ",this.umidita));
		sb.append("\n");
		return sb.toString();
		
	}

	

}
