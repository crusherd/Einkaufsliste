package htwg.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class UserListActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_user);
	}

	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	
}
