package htwg.project;

import htwg.backend.JsonNodeNames;
import htwg.backend.User;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_user);
        users = new ArrayList<User>();
        Bundle bundle = this.getIntent().getExtras();
        for(int i = 0; i < bundle.size(); ++i) {
        	try {
        		JSONObject object = new JSONObject(bundle.getString("json_users" + i));
        		users.add(new User(object.getInt(JsonNodeNames.TAG_ID), object.getString(JsonNodeNames.TAG_USERNAME)));
        		Log.i("test", bundle.getString("json_users" + i));
        	} catch (Exception e) {
        		Log.i(UserListActivity.class.getName(), e.getMessage());
        	}
        }
        
        Spinner spinner = (Spinner) findViewById(R.id.UserChooseSpinner);
        
        ArrayAdapter<User> spinnerAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, users);
        spinner.setAdapter(spinnerAdapter);
	}

	
	@Override
	public void onPause() {
		super.onPause();
	}


	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		
	}


	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}
