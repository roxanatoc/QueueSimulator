package assignment2;

import java.io.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class Manager implements Runnable {

	public int numarClienti;
	public int numarCozi;
	public int timpSimulare;
	public int timpMaximSosire;
	public int timpMinimSosire;
	public int timpMaximDeServire;
	public int timpMinimDeServire;

	public String fisierIntrare;
	public String fisierIesire;

	private Procesare procesare;
	private List<Client> listaClienti;
	public static boolean firDeExecutie = true;
	private float timpAsteptareMediu = 0.0f;

	public Manager() {
	}

	public void readFromFile(String cale) {
		try {
			FileInputStream f = new FileInputStream(cale);
			InputStreamReader fchar = new InputStreamReader(f);
			BufferedReader buf = new BufferedReader(fchar);

			this.numarClienti = Integer.parseInt(buf.readLine());
			this.numarCozi = Integer.parseInt(buf.readLine());
			this.timpSimulare = Integer.parseInt(buf.readLine());

			String l1, l2;
			l1 = buf.readLine();
			l2 = buf.readLine();
			
			String[] rez1 = l1.split(",");
			this.timpMinimSosire = Integer.parseInt(rez1[0]);
			this.timpMaximSosire = Integer.parseInt(rez1[1]);

			String[] rez2 = l2.split(",");
			this.timpMinimDeServire = Integer.parseInt(rez2[0]);
			this.timpMaximDeServire = Integer.parseInt(rez2[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateRandom() {
		Random rnd = new Random();

		for (int i = 0; i < numarClienti; i++) {
			int sosire = rnd.nextInt(timpMaximSosire - timpMinimSosire) + timpMinimSosire;
			int servire = rnd.nextInt(timpMaximDeServire - timpMinimDeServire) + timpMinimDeServire;
			listaClienti.add(new Client(i + 1, sosire, servire));
		}
		Collections.sort(listaClienti);
	}

	@Override
	public void run() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.fisierIesire, false));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int currentTime = 1;
		while (currentTime <= timpSimulare) {
			List<Integer> l = new ArrayList<>();
			int it = 0;

			for (Client i : listaClienti) {
				if (i.getSosire() == currentTime) {
					l.add(it);
					this.timpAsteptareMediu = this.timpAsteptareMediu + procesare.repartizareClient(i);
				}
				it++;
			}

			for (int i = l.size() - 1; i >= 0; i--) {
				listaClienti.remove(listaClienti.get(l.get(i)));
			}

			writeOutput(this.fisierIesire, currentTime, this.listaClienti, this.procesare);

			currentTime++;

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedWriter bw = null;
			bw = new BufferedWriter(new FileWriter(this.fisierIesire, true));
			bw.newLine();
			bw.write("Avarange waiting time: " + (this.timpAsteptareMediu / this.numarClienti));
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.firDeExecutie = false;

	}

	public void writeOutput(String s, int timp, List<Client> lClienti, Procesare p) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(s, true));
			bw.newLine();
			bw.write("Time " + timp);
			bw.newLine();
			bw.write("Waiting clients: ");

			for (int i = 0; i < lClienti.size(); i++) {
				bw.write(lClienti.get(i).toString());
			}

			for (int i = 0; i < this.numarCozi; i++) {
				bw.newLine();
				if (p.getCozi().get(0) != null) {
					bw.write("Queue " + (i + 1) + ": ");
					Coada c = p.getCozi().get(i);
					bw.write(c.toString());
				}
			}

			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Manager m = new Manager();

		m.fisierIntrare = args[0];
		m.fisierIesire = args[1];

		m.readFromFile(m.fisierIntrare);
		m.procesare = new Procesare(m.numarCozi);
		m.listaClienti = new ArrayList<>();
		m.generateRandom();

		Thread t = new Thread(m);
		t.start();
	}
}
