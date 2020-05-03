package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.print(m.getUmiditaMedia(12).toString());
		

		List<Citta> citta =new LinkedList<Citta>(m.trovaSequenza(5));
		for(Citta c:citta) {
			
			System.out.println(c.getNome());
			
			
		}

	}

}
