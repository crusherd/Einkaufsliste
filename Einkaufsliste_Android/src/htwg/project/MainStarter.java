package htwg.project;

import htwg.connection.HttpConnection;
import htwg.connection.HttpConnection.RequestType;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainStarter extends Activity {
	
	private View view = null;
	private HttpConnection connection = null;
	private boolean wifiOn = false;
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
        checkWifi();
    }
    
    /** Called when the activity is paused or shutdowned */
    @Override
    public void onPause( ) {
    	super.onPause();
    }
    
    public void checkWifi() {
    	ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    	NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	if(wifi.isConnected())
    		wifiOn = true;
    }
    
    /**
     * Logic if Connect button is pressed
     * @param view
     */
    public void connect(View view) {
    	if(wifiOn) {
    		String ipText = ((TextView) findViewById(R.id.IPTextField)).getText().toString();
    		Pattern pattern = Pattern.compile(IP_PATTERN);
    		Log.i("input", ipText);
    		if(!ipText.matches(pattern.pattern())){
    			Toast.makeText(this, R.string.toast_not_an_ip, Toast.LENGTH_SHORT).show();
    		} else {
    			connection = new HttpConnection(ipText, this.getApplicationContext());
    			JSONArray jsonArray = connection.getJsonFromRequest(RequestType.USERS);
//    			connection successful and retrieved data
    			if(jsonArray != null) {
    				//start user selection screen
    			}
    		}
    	} else {
    		Toast.makeText(this, R.string.wifi_is_off, Toast.LENGTH_LONG).show();
    	}
    }
}