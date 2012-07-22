package htwg.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnection extends SQLiteOpenHelper{

	private static final String DB_NAME = "project.db";
	private static final int DB_VERSION = 1;
	
	public static final String TABLE_USERS = "users";
	public static final String TABLE_SHOPPINGLISTS = "shoppinglists";
	public static final String TABLE_ARTICLES = "articles";
	public static final String TABLE_ADDRESSES = "addresses";
	public static final String TABLE_LISTINGS = "listings";
	public static final String TABLE_STORES = "stores";
	public static final String TABLE_STORES_ARTICLES = "stores_articles";
	public static final String TABLE_STORES_ADDRESSES = "stores_addresses";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_ARTICLE_ID = "article_id";
	public static final String COLUMN_STORE_ID = "store_id";
	public static final String COLUMN_SHOPPINGLIST_ID = "shoppinglist_id";
	public static final String COLUMN_ADDRESS_ID = "address_id";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_STREET = "street";
	public static final String COLUMN_ZIPCODE = "zipCode";
	public static final String COLUMN_CITY = "city";
	
	private static final String DATABASE_CREATE_USERS = "create table " + TABLE_USERS + "(" + 
														COLUMN_ID + " integer, " + 
														COLUMN_USERNAME + " text);";
	private static final String DATABASE_CREATE_SHOPPINGLISTS = "create table " + TABLE_SHOPPINGLISTS + "(" + 
														COLUMN_ID + " integer, " + 
														COLUMN_NAME + " text, " + 
														COLUMN_USER_ID + " integer);";
	private static final String DATABASE_CREATE_ARTICLES = "create table " + TABLE_ARTICLES + "(" + 
														COLUMN_ID + " integer, " + 
														COLUMN_NAME + " text, " + 
														COLUMN_PRICE + " decimal);";
	private static final String DATABASE_ADDRESSES = "create table " + TABLE_ADDRESSES + "(" +
														COLUMN_ID + " integer, " +
														COLUMN_STREET + " text, " + 
														COLUMN_ZIPCODE + " text, " +
														COLUMN_CITY + " text);";
	private static final String DATABASE_CREATE_LISTING = "create table " + TABLE_LISTINGS + "(" + 
														COLUMN_ID + " integer, " + 
														COLUMN_SHOPPINGLIST_ID + " integer, " + 
														COLUMN_ARTICLE_ID + " integer, " +
														COLUMN_AMOUNT + " integer);";
	private static final String DATABASE_CREATE_STORES = "create table " + TABLE_STORES + "(" +
														COLUMN_ID + " integer, " + 
														COLUMN_NAME + " text);";
	private static final String DATABASE_CREATE_STORES_ARTICLES = "create table " + TABLE_STORES_ARTICLES + "(" +
														COLUMN_ID + " integer, " + 
														COLUMN_ARTICLE_ID + " integer, " + 
														COLUMN_STORE_ID + " integer);";
	public static final String DATABASE_CREATE_STORES_ADDRESSES = "create table " + TABLE_STORES_ADDRESSES + "(" + 
														COLUMN_STORE_ID + " integer, " + 
														COLUMN_ADDRESS_ID + " integer);";
	
	public DatabaseConnection(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_USERS);
		db.execSQL(DATABASE_CREATE_SHOPPINGLISTS);
		db.execSQL(DATABASE_CREATE_ARTICLES);
		db.execSQL(DATABASE_CREATE_LISTING);
		db.execSQL(DATABASE_CREATE_STORES);
		db.execSQL(DATABASE_CREATE_STORES_ARTICLES);
		db.execSQL(DATABASE_ADDRESSES);
		db.execSQL(DATABASE_CREATE_STORES_ADDRESSES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseConnection.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTINGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPINGLISTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES_ADDRESSES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES_ARTICLES);
		onCreate(db);

	}


}
