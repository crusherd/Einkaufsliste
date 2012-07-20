package htwg.backend;

public class Article {

	private String name = "";
	private double price = 0.0;
	private Store store = null;
	private boolean isVisible = false;
	
	public Article(String name, Double price, Store store) {
		this.name = name;
		this.price = price;
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Store getStore() {
		return store;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
