package assignment2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Coada implements Runnable{

	private BlockingQueue<Client> client;
	private AtomicInteger waitingPeriod;
	
	public Coada() {
		this.client = new LinkedBlockingQueue<Client>();
		this.waitingPeriod = new AtomicInteger(0);
	}

	public void adaugareClient(Client c) {
		client.add(c);
		this.waitingPeriod.set(this.waitingPeriod.get() + c.getServire());
	}
	
	@Override
	public void run() {
		while(true) {
			if(client.isEmpty() != true) {
				try {
				while(this.client.element().getServire() > 0) {
					sleep(1000);
					this.client.element().setServire(this.client.element().getServire() - 1);
					this.waitingPeriod.set(this.waitingPeriod.get() - 1);
				}
				Client c = this.client.take();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (Manager.firDeExecutie == false) {
				break;
			}
		}
		Manager.firDeExecutie = false;
		
	}

	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	@Override
	public String toString() {
		if (this.client.isEmpty()) {
			return "closed";
		}

		String string = "";
		for (Client c : client) {
			string = string + c.toString() + " ";
		}

		return string;
	}


}

