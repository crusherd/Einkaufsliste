package htwg.project;

import htwg.backend.Article;
import htwg.connection.DatabaseConnection;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListingActivity extends ListActivity {

	private Bundle bundle = null;
	private SimpleAdapter simpleAdapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

        bundle = this.getIntent().getExtras();
        int shoppinglistId = bundle.getInt("shoppinglist_id");

        ListView listView = getListView();
//      deactivate stand-by mode
        listView.setKeepScreenOn(true);

//        db query
        ArrayList<HashMap<String, TextView>> data = requestDataFromDB(shoppinglistId);
//        sort strings
//        arrayAdapter.sort(new Comparator<String>() {
//        	public int compare(String obj1, String obj2) {
//        		return obj1.compareTo(obj2);
//        	}
//		});
        simpleAdapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[] {"content"}, new int[] {android.R.layout.simple_list_item_1});
        listView.setAdapter(simpleAdapter);
	}

	/** Called when the activity is paused or shutdowned */
	@Override
	public void onPause() {
		super.onPause();
	}

//	TODO: Does not work!!
	/**
	 * stikes an item if clicked
	 */
	@Override
	public void onListItemClick(ListView parent, View view, int pos, long id) {
		super.onListItemClick(parent, view, pos, id);
		Log.i("test", "item selected: " + pos + " id: " + id);
//		CustomTextView item = (CustomTextView) parent.getItemAtPosition(pos);
//		arrayAdapter.remove(item);
//		TextView strikedText = new TextView(this);
//		strikedText.setText(item);
//		item.setPaintFlags(item.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
//		item = (String) strikedText.getText();
//		arrayAdapter.insert(item, pos);
//		simpleAdapter.notifyDataSetChanged();
	}

	/**
	 *
	 * @param shoppinglistId
	 * @param simpleAdapter
	 */
	private ArrayList<HashMap<String, TextView>> requestDataFromDB(int shoppinglistId) {
//		String queryArticles = "SELECT articles.* FROM " + DatabaseConnection.TABLE_ARTICLES + " articles , " + DatabaseConnection.TABLE_LISTINGS + " listings " +
//					   "WHERE listings.shoppinglist_id = " + shoppinglistId +
//					   " AND listings.article_id = articles.id";
		ArrayList<HashMap<String, TextView>> data = new ArrayList<HashMap<String,TextView>>();
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
        	Article article = getCorrespondingArticle(db, cursorListing.getInt(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_ARTICLE_ID)));
        	mixedAmountArticle += article.getName();
//          add article and amount to listView
        	TextView textView = new TextView(this);
        	textView.setText(mixedAmountArticle);
        	HashMap<String, TextView> item = new HashMap<String, TextView>();
        	item.put("content", textView);
        	data.add(item);
        	Log.i(ShoppingListsActivity.class.getName(), "Added article: " + article.getName() + " to hashmap");
			cursorListing.moveToNext();
        }
        dbConnection.close();
        return data;
	}

	/**
	 * search for specified article
	 * @param db - db to search through
	 * @param articleId - article identifier
	 * @return article with the specified articleId
	 */
	private Article getCorrespondingArticle(SQLiteDatabase db, int articleId) {
		String[] columnsArticle = {DatabaseConnection.COLUMN_ID, DatabaseConnection.COLUMN_NAME, DatabaseConnection.COLUMN_PRICE};
		String selection = DatabaseConnection.COLUMN_ID + " = " + articleId;
		Cursor cursorArticle = db.query(DatabaseConnection.TABLE_ARTICLES, columnsArticle, selection, null, null, null, null);
		cursorArticle.moveToFirst();
		Article article = new Article(cursorArticle.getInt(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_ID)),
									  cursorArticle.getString(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_NAME)),
									  cursorArticle.getDouble(cursorArticle.getColumnIndex(DatabaseConnection.COLUMN_PRICE)));
		return article;
	}
}
