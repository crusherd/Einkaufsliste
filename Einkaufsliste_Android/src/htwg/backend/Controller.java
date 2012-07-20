package htwg.backend;

import java.util.List;

import org.json.JSONArray;

import htwg.backend.User;
import htwg.connection.HttpConnection;

public class Controller {
	private List<User> users;
	private HttpConnection connection = null;
	
	public Controller(HttpConnection connection) {
		this.connection = connection;
	}
	
	public List<User> createUsers() {
		return null;
	}
	
	
}
