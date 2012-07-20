package htwg.backend;

public class User {

	private int id = 0;
	private String username = "";
	
	public User(int id, String username) {
		this.id = id;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return username;
	}
}
