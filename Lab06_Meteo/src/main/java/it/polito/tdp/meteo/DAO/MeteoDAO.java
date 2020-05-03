package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {
		
		//Query
		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			//Estrapolazione risultati
			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getUmiditaMese(int mese) {
		final String sql = "SELECT Localita, AVG(Umidita) AS Umidita FROM situazione WHERE MONTH(DATA)=? GROUP BY Localita";
		
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			
			//Estrapolazione risultati
			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), null, rs.getDouble("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Citta> getCitta(){
		final String sql = "SELECT Distinct Localita FROM situazione;";
		List<Citta> citta = new ArrayList<Citta>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			//Estrapolazione risultati
			while (rs.next()) {

				Citta c = new Citta(rs.getString("Localita"));
				citta.add(c);
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	public List<Citta> getRilevazioniUmiditaMeseCitta(int mese) {
		final String sql = "SELECT Umidita, DATA FROM situazione WHERE MONTH(DATA)=? AND DAY(DATA)<16  AND Localita=?";
		
		
		List<Citta> citta = new ArrayList<Citta>(this.getCitta());
		
		for(Citta c: citta) {
			List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2,c.getNome());
			ResultSet rs = st.executeQuery();
			
			//Estrapolazione risultati
			while (rs.next()) {

				Rilevamento r = new Rilevamento(c.getNome(), rs.getDate("data"), rs.getDouble("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			c.setRilevamenti(rilevamenti);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
		return citta;
	}	
}



