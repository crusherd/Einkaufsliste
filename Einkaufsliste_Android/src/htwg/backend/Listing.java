package htwg.backend;

public class Listing {

	private int id = 0;
	private int shoppinglistId = 0;
	private int articleId = 0;
	private int amount = 0;

	public Listing(int id, int shoppinglist_id, int article_id, int amount) {
		this.id = id;
		this.shoppinglistId = shoppinglist_id;
		this.articleId = article_id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getShoppinglistId() {
		return shoppinglistId;
	}

	public int getArticleId() {
		return articleId;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return ("ShoppinglistId: " + shoppinglistId + ", ArticleId: " + articleId);
	}
}
