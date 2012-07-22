package htwg.project;

import htwg.backend.Article;
import htwg.backend.JsonNodeNames;
import htwg.connection.HttpConnection;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListingActivity extends Activity {

	private ArrayList<Article>articles = null;
	HttpConnection connection = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listing_article);

        articles = new ArrayList<Article>();
        Bundle bundle = this.getIntent().getExtras();
//        get HttpConnection for receiving json data
        String ipAddress = (String) bundle.get("ipAddress");
        connection = new HttpConnection(ipAddress);
        Log.i(UserListActivity.class.getName(), "Received: " + connection.getClass().getName());

//        get Json user objects as String from Extras an put them to an ArrayList
        for(int i = 0; i < bundle.size(); ++i) {
        	try {
        		JSONObject object = new JSONObject(bundle.getString("json_users" + i));
        		articles.add(new Article(object.getString(JsonNodeNames.TAG_NAME),
        				object.getInt(JsonNodeNames.TAG_ID),
        				object.getDouble(JsonNodeNames.TAG_PRICE)));
        		Log.i(UserListActivity.class.getName(), bundle.getString("json_articles" + i));
        	} catch (JSONException e) {
        		Log.i(UserListActivity.class.getName(), e.getMessage());
        	} catch (Exception e) {
//        		needed because bundle.getString() throws Exception with no message if key not found.
//        		This causes in Log.i() a NPE.
        	}
        }

//        add articles to listView
        ListView listView = (ListView) findViewById(R.id.listingArticleView);
        ArrayAdapter<Article> arrayAdapter = new ArrayAdapter<Article>(this, android.R.layout.simple_list_item_1, articles);
        listView.setAdapter(arrayAdapter);
	}
}
