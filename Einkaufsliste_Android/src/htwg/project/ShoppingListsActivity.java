package htwg.project;

import htwg.backend.JsonNodeNames;
import htwg.backend.Shoppinglist;
import htwg.backend.User;
import htwg.connection.HttpConnection;
import htwg.connection.HttpConnection.RequestType;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShoppingListsActivity extends Activity implements OnItemSelectedListener{
	
//	stores all shoppingLists from given user in UserListActivity
	private ArrayList<Shoppinglist>shoppingLists = null;
	private HttpConnection connection = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shopping_lists);
        
        shoppingLists = new ArrayList<Shoppinglist>();
        Bundle bundle = this.getIntent().getExtras();
//        get HttpConnection for receiving json data
        String ipAddress = (String) bundle.get("ipAddress");
        connection = new HttpConnection(ipAddress);
        Log.i(ShoppingListsActivity.class.getName(), "Received: " + connection.getClass().getName());
        
//      get Json Objects as String from Extras an put them to an ArrayList
      for(int i = 0; i < bundle.size(); ++i) {
      	try {
      		JSONObject object = new JSONObject(bundle.getString("json_shoppingLists" + i));
      		shoppingLists.add(new Shoppinglist(object.getInt(JsonNodeNames.TAG_ID), 
      				object.getInt(JsonNodeNames.TAG_USER_ID), 
      				object.getString(JsonNodeNames.TAG_SHOPPINGLIST_NAME)));
      		Log.i(UserListActivity.class.getName(), bundle.getString("json_shoppingLists" + i));
      	} catch (JSONException e) {
      		Log.i(UserListActivity.class.getName(), e.getMessage());
      	} catch (Exception e) {
//    		needed because bundle.getString() throws Exception with no message if key not found.
//    		This causes in Log.i() a NPE.
    	}
      }
      
      ListView listView = (ListView) findViewById(R.id.shoppingListsView);
      ArrayAdapter<Shoppinglist> arrayAdapter = new ArrayAdapter<Shoppinglist>(this, android.R.layout.simple_list_item_1, shoppingLists);
      listView.setAdapter(arrayAdapter);
      
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

//	react on item selection
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
//		get selected shoppingList
		Shoppinglist user = (Shoppinglist) parent.getItemAtPosition(pos);
		
//		retrieve all articles
		JSONArray allArticles = connection.getJsonFromRequest(RequestType.ARTICLES);
		
//		filter them
		JSONArray filterdArticles = new JSONArray();
		try {
			if(allArticles != null) {
				for(int i = 0; i < allArticles.length(); ++i) {
					JSONObject tmp = allArticles.getJSONObject(i);
					if(tmp.getInt(JsonNodeNames.TAG_ID) == user.getId())
						filterdArticles.put(allArticles.getJSONObject(i));
				}
				
//				start Activity to show Shoppinglists from selected user
				Intent intent = new Intent(this, ShoppingListsActivity.class);
				intent.putExtra("ipAddress", connection.getIpAddress());
				intent.putExtra("user", user.getId());
				for(int i = 0; i < filterdArticles.length(); ++i)
						intent.putExtra("json_articles" + i, filterdArticles.getJSONObject(i).toString());
				startActivity(intent);
			}
		} catch (Exception e) {
			Log.i(UserListActivity.class.getName(), e.getMessage());
		}
	}

//	callback which does nothing
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
