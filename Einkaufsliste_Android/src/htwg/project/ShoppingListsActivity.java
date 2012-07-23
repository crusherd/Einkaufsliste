package htwg.project;

import htwg.backend.Shoppinglist;
import htwg.connection.DatabaseConnection;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShoppingListsActivity extends ListActivity{

	private Bundle bundle = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        bundle = this.getIntent().getExtras();
        int userId = bundle.getInt("user_id");
        ListView listView = getListView();
//      deactivate standby
        listView.setKeepScreenOn(true);
        ArrayAdapter<Shoppinglist> arrayAdapter = new ArrayAdapter<Shoppinglist>(this, android.R.layout.simple_list_item_1);
//        db query
        requestDataFromDB(userId, arrayAdapter);
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
//		get selected shoppingList
		Shoppinglist shoppingList = (Shoppinglist) parent.getItemAtPosition(pos);

//		start Activity to show Shoppinglists from selected user
		Intent intent = new Intent(this, ListingActivity.class);
		intent.putExtra("user_id", bundle.getInt("user_id"));
		intent.putExtra("shoppinglist_id", shoppingList.getId());
		startActivity(intent);
	}

	/**
	 * get all shoppinglists from db which meet userId
	 * @param userId - filter shoppinglists with userId
	 * @param arrayAdapter - adpater to add shoppinglists
	 */
	private void requestDataFromDB(int userId, ArrayAdapter<Shoppinglist> arrayAdapter) {
		String[] columns = {DatabaseConnection.COLUMN_ID, DatabaseConnection.COLUMN_NAME, DatabaseConnection.COLUMN_USER_ID};
        String selection = DatabaseConnection.COLUMN_USER_ID + " = " + userId;
        DatabaseConnection dbConnection = new DatabaseConnection(this);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConnection.TABLE_SHOPPINGLISTS, columns, selection, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
        	Shoppinglist list = new Shoppinglist(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
//          add shoppingLists to listView
        	arrayAdapter.add(list);
        	Log.i(ShoppingListsActivity.class.getName(), "Added shoppinglist: " + list.getName() + " to listview");
			cursor.moveToNext();
        }
        dbConnection.close();
	}

}
