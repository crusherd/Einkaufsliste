package htwg.project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

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

	private final String filename = "ipAddress";
	private static final String IPV4_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        some gimmicks
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

//        deactivate stand-by mode
        View view = findViewById(R.id.MainLayout);
        view.setKeepScreenOn(true);

//        check if wifi is on
        if(isWifiOn()) {
//        if file is present we use this
        	if(fileIsPresent()) {
				String ipText = readFromFile();
				if(!"".equals(ipText)) {
					if(pingHost(ipText)) {
						startUserSelection(ipText);
					}
				}
			}
        } else {
        	Intent intent = new Intent(this, UserListActivity.class);
        	intent.putExtra("wifiOn", false);
        	startActivityForResult(intent, 0);
        }
    }

    /** Called when the activity is paused or shutdowned */
    @Override
    public void onPause( ) {
    	super.onPause();
    }

    private boolean isWifiOn() {
    	ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    	NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	if(wifi.isConnected()) {
			return true;
		}
    	return false;
    }

    /**
     * if we are offline we don't want to see the input IP view
     */
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == 0) {
			finish();
		}
    }

    /**
     * Logic if Connect button is pressed
     * @param view
     */
    public void connect(View view) {
    	if(isWifiOn()) {
    		String ipText = ((TextView) findViewById(R.id.IPTextField)).getText().toString();
    		Pattern pattern = Pattern.compile(IPV4_PATTERN);
    		Log.i("TextView Input", ipText);
    		if(!ipText.matches(pattern.pattern())){
    			Toast.makeText(this, R.string.toast_not_an_ip, Toast.LENGTH_SHORT).show();
    		} else {
    			if(pingHost(ipText)) {
    				writeToFile(ipText);
    				startUserSelection(ipText);
    			} else {
    				Toast.makeText(this, R.string.host_not_reachable, Toast.LENGTH_SHORT).show();
    			}
    		}
    	} else {
    		Toast.makeText(this, R.string.wifi_is_off, Toast.LENGTH_LONG).show();
    	}
    }

    /**
     * check if file is present
     * @return true if file is present
     */
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

    /**
     * write the ip address from input to file
     * @param text - ip address to write
     */
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

    /**
     * read ip from file
     * @return ip Address
     */
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

    /**
     * check if host on given ip is reachable
     * @param ip - Host
     * @return true if reachable
     */
    private boolean pingHost(String ip) {
		try {
			InetAddress inAddress = InetAddress.getByName(ip);
			if(inAddress.isReachable(500)) {
				return true;
			}
		} catch (UnknownHostException e) {
			Log.i(MainStarterActivity.class.getName(),e.getMessage());
		} catch (IOException e) {
			Log.i(MainStarterActivity.class.getName(),e.getMessage());
		}

    	return false;
    }

    /**
     * start view for user selection
     * @param ipText - ip address
     */
    private void startUserSelection(String ipText) {
    	Intent intent = new Intent(this, UserListActivity.class);
    	intent.putExtra("ipAddress", ipText);
    	intent.putExtra("wifiOn", true);
    	startActivity(intent);
    }
}