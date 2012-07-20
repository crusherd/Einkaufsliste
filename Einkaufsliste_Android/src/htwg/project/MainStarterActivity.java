package htwg.project;

import htwg.connection.HttpConnection;
import htwg.connection.HttpConnection.RequestType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainStarterActivity extends Activity {
	
	private View view = null;
	private HttpConnection connection = null;
	private boolean wifiOn = false;
	private String filename = "ipAddress";
	private static String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
//        deactivate stand-by mode
        view = findViewById(R.id.MainLayout);
        view.setKeepScreenOn(true);
        
//        check if wifi is on
        wifiOn = checkWifi();
        
//        if file is present we use this
        if(fileIsPresent()) {
			String ipText = readFromFile();
			if(!ipText.equals("")) {
				if(pingHost(ipText))
					startConnection(ipText);
			}
		}
    }
    
    /** Called when the activity is paused or shutdowned */
    @Override
    public void onPause( ) {
    	super.onPause();
    }
    
    private boolean checkWifi() {
    	ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    	NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	if(wifi.isConnected())
    		return true;
    	return false;
    }
    
    /**
     * Logic if Connect button is pressed
     * @param view
     */
    public void connect(View view) {
    	if(wifiOn) {
    		String ipText = ((TextView) findViewById(R.id.IPTextField)).getText().toString();
    		Pattern pattern = Pattern.compile(IP_PATTERN);
    		Log.i("TextView Input", ipText);
    		if(!ipText.matches(pattern.pattern())){
    			Toast.makeText(this, R.string.toast_not_an_ip, Toast.LENGTH_SHORT).show();
    		} else {
    			if(pingHost(ipText)) {
    				writeToFile(ipText);
    				startConnection(ipText);
    			} else {
    				Toast.makeText(this, R.string.host_not_reachable, Toast.LENGTH_SHORT).show();
    			}
    		}
    	} else {
    		Toast.makeText(this, R.string.wifi_is_off, Toast.LENGTH_LONG).show();
    	}
    }
    
    private boolean fileIsPresent() {
    	try {
			FileInputStream fis = openFileInput(filename);
			fis.close();
			return true;
		} catch (FileNotFoundException e) {
			Log.i(MainStarterActivity.class.getName(), e.getMessage());
		} catch (IOException e) {
			Log.i(MainStarterActivity.class.getName(),e.getMessage());
		}
    	return false;
    	
    }
    
    private void writeToFile(String text) {
    	try {
			FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(text.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			Log.i(MainStarterActivity.class.getName(), e.getMessage());
		} catch (IOException e) {
			Log.i(MainStarterActivity.class.getName(), e.getMessage());
		}
    }
    
    private String readFromFile() {
    	FileInputStream fos;
		try {
			fos = openFileInput(filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fos));
	    	String res = reader.readLine();
	    	Log.i(MainStarterActivity.class.getName(), res);
	    	return res;
		} catch (FileNotFoundException e) {
			Log.i(MainStarterActivity.class.getName(), e.getMessage());
		} catch (IOException e) {
			Log.i(MainStarterActivity.class.getName(), e.getMessage());
		}
    	return "";
    }
    
    private boolean pingHost(String ip) {
		try {
			InetAddress inAddress = InetAddress.getByName(ip);
			if(inAddress.isReachable(500))
				return true;
		} catch (UnknownHostException e) {
			Log.i(MainStarterActivity.class.getName(),e.getMessage());
		} catch (IOException e) {
			Log.i(MainStarterActivity.class.getName(),e.getMessage());
		} 
    	
    	return false;
    }
    
    private void startConnection(String ipAddress) {
    	connection = new HttpConnection(ipAddress, this.getApplicationContext());
		JSONArray jsonArray = connection.getJsonFromRequest(RequestType.USERS);
//    			connection successful and retrieved data
		if(jsonArray != null) {
			//start user selection screen
			startUserSelection(jsonArray);
		}
    }
    
    private void startUserSelection(JSONArray usernames) {
    	Intent intent = new Intent(this, UserListActivity.class);
    	for(int i = 0; i < usernames.length(); ++i)
			try {
				intent.putExtra("json_users" + i, usernames.getJSONObject(i).toString());
			} catch (JSONException e) {
				Log.i(MainStarterActivity.class.getName(),e.getMessage());
			}
    	startActivity(intent);
    }
}