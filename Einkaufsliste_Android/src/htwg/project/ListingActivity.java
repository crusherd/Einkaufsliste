package htwg.project;

import htwg.backend.Article;
import htwg.connection.DatabaseConnection;

import java.util.Comparator;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListingActivity extends ListActivity {

	private Bundle bundle = null;
	ArrayAdapter<String> arrayAdapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

        bundle = this.getIntent().getExtras();
        int shoppinglistId = bundle.getInt("shoppinglist_id");
        boolean showAllArticles = bundle.getBoolean("show_all_articles");
        ListView listView = getListView();
//      deactivate stand-by mode
        listView.setKeepScreenOn(true);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        db query
        requestDataFromDB(shoppinglistId, showAllArticles);
//        sort strings
        arrayAdapter.sort(new Comparator<String>() {
        	public int compare(String obj1, String obj2) {
        		return obj1.compareTo(obj2);
        	}
		});

        listView.setAdapter(arrayAdapter);
	}

	/** Called when the activity is paused or shutdowned */
	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * (un)marks an item if selected
	 * Strikethrough does not work because listView uses Strings
	 */
	@Override
	public void onListItemClick(ListView parent, View view, int pos, long id) {
		super.onListItemClick(parent, view, pos, id);
		Log.i("test", "item selected: " + pos + " id: " + id);
		String item = (String) parent.getItemAtPosition(pos);
		arrayAdapter.remove(item);
		if(item.contains("XX")) {
			String[] split = item.split("XX");
			item = split[1];
		}else {
			item = "XX" + item + "XX";
		}
		arrayAdapter.insert(item, pos);
		arrayAdapter.notifyDataSetChanged();
	}

	/**
	 * query db for all articles which have the given shoppinglistId
	 * @param shoppinglistId -
	 * @param simpleAdapter
	 */
	private void requestDataFromDB(int shoppinglistId, boolean showAllArticles) {
		String queryListing = "SELECT listings.* FROM " + DatabaseConnection.TABLE_LISTINGS + " listings " +
							  "WHERE listings.shoppinglist_id = " + shoppinglistId;
        DatabaseConnection dbConnection = new DatabaseConnection(this);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursorListing = db.rawQuery(queryListing, null);
        cursorListing.moveToFirst();
        while(!cursorListing.isAfterLast()) {
        	String mixedAmountArticle = "";
        	Integer amount = cursorListing.getInt(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_AMOUNT));
        	mixedAmountArticle += amount.toString() + " x ";
        	Article article = getCorrespondingArticle(db, cursorListing.getInt(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_ARTICLE_ID)), showAllArticles);
        	if(article != null) {
	        	mixedAmountArticle += article.getName();
//	          	add article and amount to listView
	        	arrayAdapter.add(mixedAmountArticle);
	        	Log.i(ShoppingListsActivity.class.getName(), "Added article: " + mixedAmountArticle + " to arraylist");
        	}
			cursorListing.moveToNext();
        }
        dbConnection.close();
	}

	/**
	 * search for specified article
	 * if storeId is given, we return only articles which are in this store
	 * @param db - db to search through
	 * @param articleId - article identifier
	 * @return article with the specified articleId
	 */
	private Article getCorrespondingArticle(SQLiteDatabase db, int articleId, boolean showAllArticles) {
		Cursor cursorArticle = null;
		if(showAllArticles) {
			String queryAllArticles = "SELECT articles.* FROM " + DatabaseConnection.TABLE_ARTICLES + " articles " +
					  "WHERE articles." + DatabaseConnection.COLUMN_ID + " = " + articleId;
			cursorArticle = db.rawQuery(queryAllArticles, null);
		} else {
			int storeId = bundle.getInt("store_id");
			String queryArticlesInStore = "SELECT articles.* FROM " + DatabaseConnection.TABLE_ARTICLES + " articles, " +
										   DatabaseConnection.TABLE_STORES_ARTICLES + " stores_articles, " + DatabaseConnection.TABLE_STORES + " stores " +
										   "WHERE articles." + DatabaseConnection.COLUMN_ID + " = " + articleId + " " +
										   "AND stores_articles." + DatabaseConnection.COLUMN_ARTICLE_ID + " = " + articleId + " " +
										   "AND stores_articles." + DatabaseConnection.COLUMN_STORE_ID + " = " + storeId;
			cursorArticle = db.rawQuery(queryArticlesInStore, null);
		}
		cursorArticle.moveToFirst();
		if(cursorArticle != null && !cursorArticle.isAfterLast()) {
			Article article = new Article(cursorArticle.getInt(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_ID)),
										  cursorArticle.getString(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_NAME)),
										  cursorArticle.getDouble(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_PRICE)));
			Log.i(ListingActivity.class.getName(), "Found article: " + article.getName());
			return article;
		} else {
			return null;
		}
	}
}
