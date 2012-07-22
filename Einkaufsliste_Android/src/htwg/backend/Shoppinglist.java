package htwg.backend;

import java.util.HashMap;

public class Shoppinglist {

	private HashMap<Article, Integer>articles = null;
	private int id = 0;
	private int userId = 0;
	private String name = "";

	public Shoppinglist(int id, int userId, String name) {
		articles = new HashMap<Article, Integer>();
		this.id = id;
		this.userId = userId;
		this.name = name;
	}

	public void addArticle(Article article, int amount) {
		articles.put(article, amount);
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
