package htwg.backend;

import java.util.ArrayList;

public class Article {

	private String name = "";
	private double price = 0.0;
	private int id = 0;
	private ArrayList<Store>stores = null;
	private boolean isVisible = false;

	public Article(String name, int id, Double price) {
		this.name = name;
		this.id = id;
		this.price = price;
		stores = new ArrayList<Store>();
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public int getId() {
		return id;
	}

	public void addStore(Store store) {
		stores.add(store);
	}

	public ArrayList<Store> getStores() {
		return stores;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
