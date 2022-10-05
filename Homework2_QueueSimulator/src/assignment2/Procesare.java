package assignment2;
import java.util.ArrayList;
import java.util.List;

//Clasa Procesare

public class Procesare {
	
	private List<Coada> cozi;
	private int numarCozi;
	
	public Procesare(int numarCozi) {
		this.cozi = new ArrayList<>();
		this.numarCozi = numarCozi;
		
		for (int i=0; i< numarCozi; i++) {
			Coada c = new Coada();
			this.cozi.add(c);
			Thread t = new Thread(c);
			t.start();
		}
		
	}

	public List<Coada> getCozi() {
		return cozi;
	}

	public void setCozi(List<Coada> cozi) {
		this.cozi = cozi;
	}

	public int getNumarCozi() {
		return numarCozi;
	}

	public void setNumarCozi(int numarCozi) {
		this.numarCozi = numarCozi;
	}
	
	public int repartizareClient(Client c) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		int coadaMinima = -1;
		for (Coada i: cozi) {
			if (i.getWaitingPeriod().get() < min) {
				min = i.getWaitingPeriod().get();
				coadaMinima = index;
			}
			index++;
		}
		cozi.get(coadaMinima).adaugareClient(c);
		return cozi.get(coadaMinima).getWaitingPeriod().get();
	}
}
