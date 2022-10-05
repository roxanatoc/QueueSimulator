package assignment2;

public class Client implements Comparable<Client> {
	
	private int ID;
	private int timpSosire;
	private int timpServire;

	Client(int id, int sosire, int servire) {
		this.ID = id;
		this.timpSosire = sosire;
		this.timpServire = servire;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public int getSosire() {
		return timpSosire;
	}

	public void setSosire(int sosire) {
		this.timpSosire = sosire;
	}

	public int getServire() {
		return timpServire;
	}

	public void setServire(int servire) {
		this.timpServire = servire;
	}

	public int compareTo(Client client) {
		if (timpSosire > client.timpSosire) {
			return 1;
		} else {
			if (timpSosire == client.timpSosire) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	@Override
	public String toString() {
		return "(" + this.ID + "," + this.timpSosire + "," + this.timpServire + "); ";
	}
}

