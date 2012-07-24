package htwg.project;

import htwg.backend.Store;
import htwg.connection.DatabaseConnection;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowStoresActivity extends ListActivity {

	private Bundle bundle = null;
	private Resources res = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		res = getResources();
        bundle = this.getIntent().getExtras();
        int shoppinglistId = bundle.getInt("shoppinglist_id");
        ListView listView = getListView();
//      deactivate standby
        listView.setKeepScreenOn(true);
        ArrayAdapter<Store> arrayAdapter = new ArrayAdapter<Store>(this, android.R.layout.simple_list_item_1);
//        db query
        requestDataFromDB(shoppinglistId, arrayAdapter);
        listView.setAdapter(arrayAdapter);
	}

	/** Called when the activity is paused or shutdowned */
	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * react on item selection
	 */
	@Override
	public void onListItemClick(ListView parent, View view, int pos, long id) {
//		get selected store
		Store store = (Store) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, ListingActivity.class);
		intent.putExtra("user_id", bundle.getInt("user_id"));
		intent.putExtra("shoppinglist_id", bundle.getInt("shoppinglist_id"));
//		if pos == 0 we have to show all articles in this shoppinglist
		if(pos == 0) {
			intent.putExtra("show_all_articles", true);
		}else {
			intent.putExtra("show_all_articles", false);
			intent.putExtra("store_id", store.getId());
		}
//		start Activity to show Shoppinglists from selected user
		startActivity(intent);
	}

	/**
	 * get all shoppinglists from db which meet userId
	 * @param userId - filter shoppinglists with userId
	 * @param arrayAdapter - adpater to add shoppinglists
	 */
	private void requestDataFromDB(int shoppinglistId, ArrayAdapter<Store> arrayAdapter) {
//		get all stores in this shoppinglist
		String queryStoresInList = "SELECT stores.* FROM " + DatabaseConnection.TABLE_STORES + " stores, " + DatabaseConnection.TABLE_LISTINGS + " listings, " +
		           					DatabaseConnection.TABLE_ARTICLES + " articles, " + DatabaseConnection.TABLE_STORES_ARTICLES + " stores_articles " +
		           					"WHERE listings." + DatabaseConnection.COLUMN_SHOPPINGLIST_ID + " = " + shoppinglistId + " " +
		           					"AND listings." + DatabaseConnection.COLUMN_ARTICLE_ID + " = articles." + DatabaseConnection.COLUMN_ID + " " +
		           					"AND articles." + DatabaseConnection.COLUMN_ID + " = stores_articles." + DatabaseConnection.COLUMN_STORE_ID + " " +
		           					"AND stores." + DatabaseConnection.COLUMN_ID + " = stores_articles." + DatabaseConnection.COLUMN_STORE_ID + " " +
		           					"GROUP BY stores." + DatabaseConnection.COLUMN_NAME;
        DatabaseConnection dbConnection = new DatabaseConnection(this);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursorStores = db.rawQuery(queryStoresInList, null);
//        add dummy store if user wants to see all articles in this shoppinglist
        arrayAdapter.add(new Store(-1, res.getString(R.string.show_articles)));
        cursorStores.moveToFirst();
        while(!cursorStores.isAfterLast()) {
        	Store store = new Store(cursorStores.getInt(cursorStores.getColumnIndex(DatabaseConnection.COLUMN_ID)),
        							cursorStores.getString(cursorStores.getColumnIndex(DatabaseConnection.COLUMN_NAME)));
//          add store to listView
        	arrayAdapter.add(store);
        	Log.i(ShoppingListsActivity.class.getName(), "Added store: " + store.getName() + " to listview");
			cursorStores.moveToNext();
        }
        dbConnection.close();
	}
}
