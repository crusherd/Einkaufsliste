package htwg.backend;


public class Shoppinglist {

	private int id = 0;
	private int userId = 0;
	private String name = "";

	public Shoppinglist(int id, String name, int userId) {
		this.id = id;
		this.userId = userId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getuserId() {
		return userId;
	}

	@Override
	public String toString() {
		return name;
	}
}
