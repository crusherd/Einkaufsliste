package htwg.backend;

public class Article {

	private String name = "";
	private double price = 0.0;
	private int amount = 0;
	private int id = 0;
	private Store store = null;
	private boolean isVisible = false;
	
	public Article(String name, int id, int amount, Double price) {
		this.name = name;
		this.id = id;
		this.amount = amount;
		this.price = price;
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
	
	public int getAmount() {
		return amount;
	}

	public int getId() {
		return id;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
