package htwg.backend;

import htwg.connection.DatabaseConnection;
import htwg.connection.HttpConnection;
import htwg.connection.HttpConnection.RequestType;
import htwg.project.R;
import htwg.project.UserListActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SyncClass {

	private HttpConnection connection = null;
	private ArrayAdapter<User>spinnerAdapter = null;
	private Context context = null;
	private SQLiteDatabase db = null;
	private DatabaseConnection dbConnection = null;
	
	private ArrayList<User>usersArray = null;
	private ArrayList<Shoppinglist>shoppinglistsArray = null;
	private ArrayList<Article>articlesArray = null;
	
	private JSONArray allJsonUsers = null;
	private JSONArray allJsonShoppingLists = null;
	private JSONArray allJsonArticles = null;
	
	public SyncClass(String ipAddress, ArrayAdapter<User> spinnerAdapter, Context context) {
		usersArray = new ArrayList<User>();
        shoppinglistsArray = new ArrayList<Shoppinglist>();
        articlesArray = new ArrayList<Article>();
        this.spinnerAdapter = spinnerAdapter;
        connection = new HttpConnection(ipAddress);
        Log.i(UserListActivity.class.getName(), "Received: " + connection.getClass().getName());
        this.context = context;
        dbConnection = new DatabaseConnection(context);
	}
	
	public void synchronize() throws Exception {
		getAllJsonData();
		db = dbConnection.getWritableDatabase();
//		clear db and set new up
		dbConnection.onUpgrade(db, 1, 1);
		
		extractJsonUsers();
		saveUsersToDB();
		
		updateSpinner();
//		if(dbHasTableEntries())
//			;
			
		dbConnection.close();
	}

	private void updateSpinner() {
		spinnerAdapter.clear();
		spinnerAdapter.add(new User(-1, ""));
		for(User user: usersArray) {
			spinnerAdapter.add(user);
		}
		spinnerAdapter.notifyDataSetChanged();
	}
	
	private void getAllJsonData() {
		allJsonUsers = connection.getJsonFromRequest(RequestType.USERS);
		allJsonShoppingLists = connection.getJsonFromRequest(RequestType.SHOPPINGLISTS);
		allJsonArticles = connection.getJsonFromRequest(RequestType.ARTICLES);
	}
	
	private void extractJsonUsers() throws RuntimeException {
//      get Json user objects as String from Extras an put them to an ArrayList
      for(int i = 0; i < allJsonUsers.length(); ++i) {
      	try {
      		JSONObject object = allJsonUsers.getJSONObject(i);
      		User user = new User(object.getInt(JsonNodeNames.TAG_ID), object.getString(JsonNodeNames.TAG_USERNAME));
      		usersArray.add(user);
      		Log.i(UserListActivity.class.getName(),allJsonUsers.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(usersArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No User found");
			Toast.makeText(context, R.string.toast_no_user_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}
	
	private void saveUsersToDB() {
		for(User user: usersArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, user.getId());
			values.put(DatabaseConnection.COLUMN_USERNAME, user.getUsername());
			db.insert(DatabaseConnection.TABLE_USERS, null, values);
			Log.i(SyncClass.class.getName(), "Written to db: " + user.getUsername());
		}
	}
}
