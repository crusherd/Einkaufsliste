package htwg.backend;

import java.util.ArrayList;

public class Shoppinglist {
	
	private ArrayList<Article>articles = null;
	private int id = 0;
	private int userId = 0;
	private String name = "";

	public Shoppinglist(int id, int userId, String name) {
		articles = new ArrayList<Article>();
		this.id = id;
		this.userId = userId;
		this.name = name;
	}
	
	public void addArticle(Article article) {
		articles.add(article);
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
	
	public String toString() {
		return name;
	}
}
