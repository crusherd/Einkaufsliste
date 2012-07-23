package htwg.project;

import htwg.backend.SyncClass;
import htwg.backend.User;
import htwg.connection.DatabaseConnection;
import htwg.connection.HttpConnection;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
//        deactivate standby
        View view = findViewById(R.id.ChooseUserLayout);
        view.setKeepScreenOn(true);

        Bundle bundle = this.getIntent().getExtras();
//        make chooseable users visible in a spinner
        Spinner spinner = (Spinner) findViewById(R.id.UserChooseSpinner);
        spinnerAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        boolean wifiOn = (Boolean) bundle.get("wifiOn");
        if(wifiOn) {
//          get HttpConnection for receiving json data
            ipAddress = (String) bundle.get("ipAddress");
            connection = new HttpConnection(ipAddress);
        } else {
//        	deactivate sync button
        	Button syncButton = (Button) findViewById(R.id.SyncButton);
        	syncButton.setVisibility(Button.GONE);
        	updateSpinner();
        }

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
		if(pos == 0) {
			return;
		}
//		get selected User
		User user = (User) parent.getItemAtPosition(pos);

//		start Activity to show Shoppinglists from selected user
		Intent intent = new Intent(this, ShoppingListsActivity.class);
		intent.putExtra("user_id", user.getId());
		startActivity(intent);
	}

//	callback which does nothing
	public void onNothingSelected(AdapterView<?> parent) {

	}

//	logic for synchronize
	public void synchronize(View view) {
		try {
			Toast.makeText(this, R.string.start_sync, Toast.LENGTH_SHORT).show();
			SyncClass sync = new SyncClass(ipAddress, this);
			sync.synchronize();
			updateSpinner();
			Toast.makeText(this, R.string.end_sync, Toast.LENGTH_SHORT).show();
		} catch(SQLException e) {
			Log.i(UserListActivity.class.getName(), e.getMessage());
		} catch(RuntimeException e) {
			Toast.makeText(this, R.string.toast_sync_aborted, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSpinner() {
		spinnerAdapter.clear();
		spinnerAdapter.add(new User(-1, ""));
//		query db for all users
		String[] columns = {DatabaseConnection.COLUMN_ID , DatabaseConnection.COLUMN_USERNAME};
		DatabaseConnection dbConnection = new DatabaseConnection(this);
		SQLiteDatabase db = dbConnection.getReadableDatabase();
		Cursor cursor = db.query(DatabaseConnection.TABLE_USERS, columns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			User user = new User(cursor.getInt(0), cursor.getString(1));
			spinnerAdapter.add(user);
			Log.i(UserListActivity.class.getName(), "Added user: " + user.getUsername() + " to spinner");
			cursor.moveToNext();
		}
		dbConnection.close();
		spinnerAdapter.notifyDataSetChanged();
	}
}
