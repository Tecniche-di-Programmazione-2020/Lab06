package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	MeteoDAO meteodao = new MeteoDAO();
	List<Citta> visite ;
	List<Citta> citta;
	double costo = Double.MAX_VALUE;

	public Model() {

	}

	public List<Rilevamento> getUmiditaMedia(int mese) {

		return meteodao.getUmiditaMese(mese);
	}

	public List<Citta> trovaSequenza(int mese) {
		citta = new LinkedList<Citta>(meteodao.getRilevazioniUmiditaMeseCitta(mese));
		List<Citta> parziale = new ArrayList<Citta>();
		calcola(0, parziale);

		return visite;
	}

	public void calcola(int passo, List<Citta> parziale) {

		if (passo == this.NUMERO_GIORNI_TOTALI) {
			double costotentativo = this.calcoloCosto(parziale);
			if (costotentativo < costo) {
				costo = costotentativo;
				visite = new LinkedList<Citta>(parziale); 
			}
		} else {
			for (Citta c : citta) {
				if(sceltavalida(parziale,c)) {
				parziale.add(c);
				calcola(passo + 1, parziale);
				parziale.remove(parziale.size() - 1);
			}
			}
		}

	}

	private double calcoloCosto(List<Citta> parziale) {
		double costo = 0.0;
		for (int i = 1; i < NUMERO_GIORNI_TOTALI; i++) {
			Citta c = parziale.get(i - 1);
			double umid = c.getRilevamenti().get(i - 1).getUmidita();
			costo = costo + umid;
		}

		for (int i = 2; i < NUMERO_GIORNI_TOTALI; i++) {
			if (!parziale.get(i - 1).equals(parziale.get(i - 2))) {
				costo = costo + COST;

			}

		}
		return costo;

	}
	private boolean sceltavalida(List<Citta>parziale, Citta daTestare) {
		//giorni massimi
		int contatoremax=0;
		for (Citta precedente:parziale) {
			if (precedente.equals(daTestare))
				contatoremax++; 
		}
		if (contatoremax >=NUMERO_GIORNI_CITTA_MAX)
			return false;
		if(parziale.size()==0)return true;
		if (parziale.size()==1 || parziale.size()==2) {
			//siamo al secondo o terzo giorno, non posso cambiare
			//quindi l'aggiunta è valida solo se la città di prova coincide con la sua precedente
			return parziale.get(parziale.size()-1).equals(daTestare); 
		}
		//nel caso generale, se ho già passato i controlli sopra, non c'è nulla che mi vieta di rimanere nella stessa città
		//quindi per i giorni successivi ai primi tre posso sempre rimanere
		if (parziale.get(parziale.size()-1).equals(daTestare))
			return true; 
		// se cambio città mi devo assicurare che nei tre giorni precedenti sono rimasto fermo 
		if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
		&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
			return true;
		
		return false;
	}
}
