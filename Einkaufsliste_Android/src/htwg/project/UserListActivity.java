package htwg.project;

import htwg.backend.JsonNodeNames;
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
import android.widget.Spinner;

public class UserListActivity extends Activity implements OnItemSelectedListener{

	private ArrayList<User>users = null;
	private HttpConnection connection = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_user);
        users = new ArrayList<User>();
        Bundle bundle = this.getIntent().getExtras();
//        get HttpConnection for receiving json data
        String ipAddress = (String) bundle.get("ipAddress");
        connection = new HttpConnection(ipAddress);
        Log.i(UserListActivity.class.getName(), "Received: " + connection.getClass().getName());
        
//        get Json user objects as String from Extras an put them to an ArrayList
        for(int i = 0; i < bundle.size(); ++i) {
        	try {
        		JSONObject object = new JSONObject(bundle.getString("json_users" + i));
        		users.add(new User(object.getInt(JsonNodeNames.TAG_ID), object.getString(JsonNodeNames.TAG_USERNAME)));
        		Log.i(UserListActivity.class.getName(), bundle.getString("json_users" + i));
        	} catch (JSONException e) {
        		Log.i(UserListActivity.class.getName(), e.getMessage());
        	} catch (Exception e) {
//        		needed because bundle.getString() throws Exception with no message if key not found.
//        		This causes in Log.i() a NPE.
        	}
        }

//        make chooseable users visible in a spinner
        Spinner spinner = (Spinner) findViewById(R.id.UserChooseSpinner);
        ArrayAdapter<User> spinnerAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_dropdown_item_1line, users);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
	}

	
	@Override
	public void onPause() {
		super.onPause();
	}

//	react on item selection
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//		get selected User
		User user = (User) parent.getItemAtPosition(pos);
		
//		retrieve all shopping Lists
		JSONArray allUserShoppingLists = connection.getJsonFromRequest(RequestType.SHOPPINGLISTS);
		
//		filter them
		JSONArray filterdShoppingLists = new JSONArray();
		try {
			if(allUserShoppingLists != null) {
				for(int i = 0; i < allUserShoppingLists.length(); ++i) {
					JSONObject tmp = allUserShoppingLists.getJSONObject(i);
					if(tmp.getInt(JsonNodeNames.TAG_USER_ID) == user.getId())
						filterdShoppingLists.put(allUserShoppingLists.getJSONObject(i));
				}
				
//				start Activity to show Shoppinglists from selected user
				Intent intent = new Intent(this, ShoppingListsActivity.class);
				intent.putExtra("ipAddress", connection.getIpAddress());
				intent.putExtra("user", user.getId());
				for(int i = 0; i < filterdShoppingLists.length(); ++i)
						intent.putExtra("json_shoppingLists" + i, filterdShoppingLists.getJSONObject(i).toString());
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
