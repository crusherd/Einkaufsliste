package htwg.backend;

import java.util.ArrayList;

public class Store {

	private int id = 0;
	private String name = "";
	private ArrayList<Address>addresses = null;

	public Store(int id, String name) {
		this.id = id;
		this.name = name;
		addresses = new ArrayList<Address>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addAddress(Address address) {
		addresses.add(address);
	}

	public ArrayList<Address> getAddresses(){
		return addresses;
	}

	@Override
	public String toString() {
		return name;
	}
}
