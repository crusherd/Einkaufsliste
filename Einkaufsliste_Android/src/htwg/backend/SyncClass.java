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
import android.widget.Toast;

public class SyncClass {

	private HttpConnection connection = null;
	private Context context = null;
	private SQLiteDatabase db = null;
	private DatabaseConnection dbConnection = null;

	private ArrayList<User>usersArray = null;
	private ArrayList<Shoppinglist>shoppinglistsArray = null;
	private ArrayList<Article>articlesArray = null;
	private ArrayList<Address>addressesArray = null;
	private ArrayList<Store>storesArray = null;
	private ArrayList<Listing>listingsArray = null;

	private JSONArray allJsonUsers = null;
	private JSONArray allJsonShoppingLists = null;
	private JSONArray allJsonArticles = null;
	private JSONArray allListings = null;
	private JSONArray allJsonStores = null;
	private JSONArray allJsonAddresses = null;

	public SyncClass(String ipAddress, Context context) {
		usersArray = new ArrayList<User>();
        shoppinglistsArray = new ArrayList<Shoppinglist>();
        articlesArray = new ArrayList<Article>();
        addressesArray = new ArrayList<Address>();
        storesArray = new ArrayList<Store>();
        listingsArray = new ArrayList<Listing>();
        if(!"".equals(ipAddress)) {
			connection = new HttpConnection(ipAddress);
		}
        Log.i(UserListActivity.class.getName(), "Received: " + connection.getClass().getName());
        this.context = context;
        dbConnection = new DatabaseConnection(context);
	}

	public void synchronize() throws Exception {
		if(connection != null) {
			getAllJsonData();
			extractJsonData();
			db = dbConnection.getWritableDatabase();
//			clear db and set new up
			dbConnection.onUpgrade(db, 1, 1);
			saveAllDataToDB();
			dbConnection.close();
		}
	}

	private void getAllJsonData() {
		allJsonUsers = connection.getJsonFromRequest(RequestType.USERS);
		allJsonShoppingLists = connection.getJsonFromRequest(RequestType.SHOPPINGLISTS);
		allJsonArticles = connection.getJsonFromRequest(RequestType.ARTICLES);
		allJsonStores = connection.getJsonFromRequest(RequestType.STORES);
		allJsonAddresses = connection.getJsonFromRequest(RequestType.ADDRESSES);
		allListings = connection.getJsonFromRequest(RequestType.LISTINGS);
	}

	private void extractJsonData() {
		extractJsonUsers();
		extractJsonShoppinglists();
		extractJsonArticles();
		extractJsonAddresses();
		extractJsonStores();
		extractJsonListings();
	}

	private void extractJsonUsers() throws RuntimeException {
//      get JSON user objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allJsonUsers.length(); ++i) {
      	try {
      		JSONObject object = allJsonUsers.getJSONObject(i);
      		User user = new User(object.getInt(JsonNodeNames.TAG_ID),
      							 object.getString(JsonNodeNames.TAG_USERNAME));
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

	private void extractJsonShoppinglists() {
//      get JSON shoppinglists objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allJsonShoppingLists.length(); ++i) {
      	try {
      		JSONObject object = allJsonShoppingLists.getJSONObject(i);
      		Shoppinglist list = new Shoppinglist(object.getInt(JsonNodeNames.TAG_ID),
      											 object.getString(JsonNodeNames.TAG_NAME),
      											 object.getInt(JsonNodeNames.TAG_USER_ID));
      		shoppinglistsArray.add(list);
      		Log.i(UserListActivity.class.getName(),allJsonShoppingLists.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(shoppinglistsArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No shoppinglist found");
			Toast.makeText(context, R.string.toast_no_shoppinglist_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}

	private void extractJsonStores() {
//      get JSON store objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allJsonStores.length(); ++i) {
      	try {
      		JSONObject object = allJsonStores.getJSONObject(i);
      		Store store = new Store(object.getInt(JsonNodeNames.TAG_ID),
      								object.getString(JsonNodeNames.TAG_NAME));
      		JSONArray addresses = object.getJSONArray(JsonNodeNames.TAG_ADDRESSES);
      		if(addresses.length() > 0) {
      			for(int j = 0; j < addresses.length(); ++j) {
      				JSONObject jsonAddress = addresses.getJSONObject(j);
      				Address address = new Address(jsonAddress.getInt(JsonNodeNames.TAG_ID),
      											  jsonAddress.getString(JsonNodeNames.TAG_STREET),
      											  jsonAddress.getString(JsonNodeNames.TAG_ZIPCODE),
      											  jsonAddress.getString(JsonNodeNames.TAG_CITY));
      				store.addAddress(address);
      			}
      		}
      		storesArray.add(store);
      		Log.i(UserListActivity.class.getName(),allJsonStores.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(storesArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No Store found");
			Toast.makeText(context, R.string.toast_no_store_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}

	private void extractJsonArticles() {
//      get JSON article objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allJsonArticles.length(); ++i) {
      	try {
      		JSONObject object = allJsonArticles.getJSONObject(i);
      		Article article = new Article(object.getInt(JsonNodeNames.TAG_ID),
      									  object.getString(JsonNodeNames.TAG_NAME), object.getDouble(JsonNodeNames.TAG_PRICE));
      		JSONArray stores = object.getJSONArray(JsonNodeNames.TAG_STORES);
      		if(stores.length() > 0) {
      			for(int j = 0; j < stores.length(); ++j) {
      				JSONObject jsonStore = stores.getJSONObject(j);
      				Store store = new Store(jsonStore.getInt(JsonNodeNames.TAG_ID),
      										jsonStore.getString(JsonNodeNames.TAG_NAME));
      				article.addStore(store);
      			}
      		}
      		articlesArray.add(article);
      		Log.i(UserListActivity.class.getName(),allJsonArticles.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(articlesArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No Article found");
			Toast.makeText(context, R.string.toast_no_article_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}

	private void extractJsonListings() {
//      get JSON listings objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allListings.length(); ++i) {
      	try {
      		JSONObject object = allListings.getJSONObject(i);
      		Listing listing = new Listing(object.getInt(JsonNodeNames.TAG_ID),
      											 object.getInt(JsonNodeNames.TAG_SHOPPING_LIST_ID),
      											 object.getInt(JsonNodeNames.TAG_ARTICLE_ID),
      											 object.getInt(JsonNodeNames.TAG_AMOUNT));
      		listingsArray.add(listing);
      		Log.i(UserListActivity.class.getName(), allListings.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(listingsArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No listing found");
			Toast.makeText(context, R.string.toast_no_listing_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}

	private void extractJsonAddresses() {
//      get JSON address objects as String from JSON-Array an put them to an ArrayList
      for(int i = 0; i < allJsonAddresses.length(); ++i) {
      	try {
      		JSONObject object = allJsonAddresses.getJSONObject(i);
      		Address address = new Address(object.getInt(JsonNodeNames.TAG_ID),
      									  object.getString(JsonNodeNames.TAG_STREET),
      									  object.getString(JsonNodeNames.TAG_ZIPCODE),
      									  object.getString(JsonNodeNames.TAG_CITY));
      		addressesArray.add(address);
      		Log.i(UserListActivity.class.getName(),allJsonAddresses.getJSONObject(i).toString());
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//      		needed because bundle.getString() throws Exception with no message if key not found.
//      		This causes in Log.i() a NPE.
      	}
      }
      if(addressesArray.isEmpty()) {
			Log.e(UserListActivity.class.getName(), "No address found");
			Toast.makeText(context, R.string.toast_no_address_found, Toast.LENGTH_SHORT).show();
			throw new RuntimeException();
      }
	}

	private void saveAllDataToDB() {
		saveUsersToDB();
		saveShoppinglistsToDB();
		saveArticlesToDB();
		saveStoresToDB();
		saveAddressesToDB();
		saveListingsToDB();
		saveConnectionStoreArticleToDB();
		saveConnectionStoreAddressToDB();
	}

	private void saveUsersToDB() {
		for(User user: usersArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, user.getId());
			values.put(DatabaseConnection.COLUMN_USERNAME, user.getUsername());
			db.insert(DatabaseConnection.TABLE_USERS, null, values);
			Log.i(SyncClass.class.getName(), "Written user to db: " + user.getUsername());
		}
	}

	private void saveShoppinglistsToDB() {
		for(Shoppinglist list: shoppinglistsArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, list.getId());
			values.put(DatabaseConnection.COLUMN_NAME, list.getName());
			values.put(DatabaseConnection.COLUMN_USER_ID, list.getuserId());
			db.insert(DatabaseConnection.TABLE_SHOPPINGLISTS, null, values);
			Log.i(SyncClass.class.getName(), "Written shoppinglist to db: " + list.getName());
		}
	}

	private void saveArticlesToDB() {
		for(Article article: articlesArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, article.getId());
			values.put(DatabaseConnection.COLUMN_NAME, article.getName());
			values.put(DatabaseConnection.COLUMN_PRICE, article.getPrice());
			db.insert(DatabaseConnection.TABLE_ARTICLES, null, values);
			Log.i(SyncClass.class.getName(), "Written article to db: " + article.getName());
		}
	}

	private void saveStoresToDB() {
		for(Store store: storesArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, store.getId());
			values.put(DatabaseConnection.COLUMN_NAME, store.getName());
			db.insert(DatabaseConnection.TABLE_STORES, null, values);
			Log.i(SyncClass.class.getName(), "Written store to db: " + store.getName());
		}
	}

	private void saveAddressesToDB() {
		for(Address address: addressesArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_ID, address.getId());
			values.put(DatabaseConnection.COLUMN_STREET, address.getStreet());
			values.put(DatabaseConnection.COLUMN_ZIPCODE, address.getZipCode());
			values.put(DatabaseConnection.COLUMN_CITY, address.getCity());
			db.insert(DatabaseConnection.TABLE_ADDRESSES, null, values);
			Log.i(SyncClass.class.getName(), "Written address to db: " + address.toString());
		}
	}

//	connection between a shoppinglist and their articles with amount
	private void saveListingsToDB() {
		for(Listing listing: listingsArray) {
			ContentValues values = new ContentValues();
			values.put(DatabaseConnection.COLUMN_SHOPPINGLIST_ID, listing.getShoppinglistId());
			values.put(DatabaseConnection.COLUMN_ARTICLE_ID, listing.getArticleId());
			values.put(DatabaseConnection.COLUMN_AMOUNT, listing.getAmount());
			db.insert(DatabaseConnection.TABLE_LISTINGS, null, values);
			Log.i(SyncClass.class.getName(), "Written listing: " + listing.toString());
		}
	}

	private void saveConnectionStoreArticleToDB() {
		for(Article article: articlesArray) {
			for(Store articleStore: article.getStores()) {
				ContentValues values = new ContentValues();
				values.put(DatabaseConnection.COLUMN_ARTICLE_ID, article.getId());
				values.put(DatabaseConnection.COLUMN_STORE_ID, articleStore.getId());
				db.insert(DatabaseConnection.TABLE_STORES_ARTICLES, null, values);
				Log.i(SyncClass.class.getName(), "Written link: " + article.getName() + " and " + articleStore.toString());
			}
		}
	}

	private void saveConnectionStoreAddressToDB() {
		for(Store store: storesArray) {
			for(Address storeAddress: store.getAddresses()) {
				ContentValues values = new ContentValues();
				values.put(DatabaseConnection.COLUMN_STORE_ID, store.getId());
				values.put(DatabaseConnection.COLUMN_ADDRESS_ID, storeAddress.getId());
				db.insert(DatabaseConnection.TABLE_STORES_ADDRESSES, null, values);
				Log.i(SyncClass.class.getName(), "Written link: " + store.getName() + " and " + storeAddress.toString());
			}
		}
	}
}
