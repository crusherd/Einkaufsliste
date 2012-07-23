package htwg.project;

import htwg.connection.DatabaseConnection;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListingActivity extends ListActivity implements OnLongClickListener{

	private Bundle bundle = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

        bundle = this.getIntent().getExtras();
        int shoppinglistId = bundle.getInt("shoppinglist_id");

        ListView listView = getListView();
//      deactivate stand-by mode
        listView.setKeepScreenOn(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        db query
        requestDataFromDB(shoppinglistId, arrayAdapter);

        listView.setAdapter(arrayAdapter);
	}

//	TODO: onLongClickListener implementieren
	public boolean onLongClick(View v) {
		return false;
	}

//	TODO: onItemSelectedListener implementieren
	@Override
	public void onListItemClick(ListView parent, View view, int pos, long id) {

	}

	private void requestDataFromDB(int shoppinglistId, ArrayAdapter<String> arrayAdapter) {
//		String queryArticles = "SELECT articles.* FROM " + DatabaseConnection.TABLE_ARTICLES + " articles , " + DatabaseConnection.TABLE_LISTINGS + " listings " +
//					   "WHERE listings.shoppinglist_id = " + shoppinglistId +
//					   " AND listings.article_id = articles.id";
		String queryListing = "SELECT listings.* FROM " + DatabaseConnection.TABLE_LISTINGS + " listings " +
							  "WHERE listings.shoppinglist_id = " + shoppinglistId;
        DatabaseConnection dbConnection = new DatabaseConnection(this);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursorListing = db.rawQuery(queryListing, null);
        cursorListing.moveToFirst();
        while(!cursorListing.isAfterLast()) {
        	String mixedAmountArticle = "";
        	Integer amount = cursorListing.getInt(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_AMOUNT));
        	mixedAmountArticle += amount.toString();

//        	Article article = new Article(cursorListing.getString(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_NAME)),
//        								  cursorListing.getInt(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_ID)),
//        								  cursorListing.getDouble(cursorListing.getColumnIndex(DatabaseConnection.COLUMN_PRICE)));
//          add article and amount to listView
        	arrayAdapter.add(mixedAmountArticle);
        	Log.i(ShoppingListsActivity.class.getName(), "Added article: " + " to listview");
			cursorListing.moveToNext();
        }
        dbConnection.close();
	}
}
