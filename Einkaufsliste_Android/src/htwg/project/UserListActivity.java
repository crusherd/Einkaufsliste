package htwg.project;

import org.json.JSONArray;
import org.json.JSONObject;

import htwg.backend.JsonNodeNames;
import htwg.backend.SyncClass;
import htwg.backend.User;
import htwg.connection.HttpConnection;
import htwg.connection.HttpConnection.RequestType;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class UserListActivity extends Activity implements OnItemSelectedListener{

	private ArrayAdapter<User>spinnerAdapter = null;
	private String ipAddress = "";
	HttpConnection connection = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_user);
//        usersArray = new ArrayList<User>();
        Bundle bundle = this.getIntent().getExtras();
//        get HttpConnection for receiving json data
        ipAddress = (String) bundle.get("ipAddress");
        connection = new HttpConnection(ipAddress);
//        make chooseable users visible in a spinner
        Spinner spinner = (Spinner) findViewById(R.id.UserChooseSpinner);
        spinnerAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
	}

	
	@Override
	public void onPause() {
		super.onPause();
	}

//	react on item selection
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//		no user chosen
		if(pos == 0)
			return;
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
	
//	logic for synchronize
	public void synchronize(View view) {
		try {
			SyncClass sync = new SyncClass(ipAddress, spinnerAdapter, this);
			sync.synchronize();
		} catch(SQLException e) {
			Log.i(UserListActivity.class.getName(), e.getMessage());
		} catch(RuntimeException e) {
			Toast.makeText(this, R.string.toast_sync_aborted, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
