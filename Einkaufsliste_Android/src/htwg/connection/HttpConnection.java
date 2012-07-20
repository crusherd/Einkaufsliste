package htwg.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

public class HttpConnection implements Serializable{
	
	private static final long serialVersionUID = -8584137779048953802L;

	private static final int PORT = 3000;
	
	private String ipAddress = "";
	private HttpURLConnection connection = null;
	private URL url = null;
	private InputStream inStream = null;
	
	public enum RequestType{
		USERS,
		SHOPPINGLISTS,
		LISTINGS,
		ARTICLES
	}
	
	public HttpConnection(String ipAddress, Context context) {
		if(ipAddress.equals("") || ipAddress == null) {
			Log.e("HttpConnection Create", "no IP-Address");
			throw new RuntimeException("no IP-Address");
		}
		if(context == null) {
			Log.e("HttpConnection Create", "no context given");
			throw new RuntimeException("no context given");
		}
		this.ipAddress = ipAddress;
	}
	
	private void initConnection(String urlAddress) throws IOException {
		url = new URL(urlAddress);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		inStream = connection.getInputStream();
	}
	
	private void closeConnection() {
		if(connection != null) {
			connection.disconnect();
		}
	}
	
	public JSONArray getJsonFromRequest(RequestType type) {
		String response = "";
		String url = "http://" + ipAddress + ":" + PORT + "/";
		JSONArray jsonArray = null;

		try {
//			make a HTTP Request
			switch (type) {
			case USERS:
				url += "users.json";
				break;
			case SHOPPINGLISTS:
				url += "shoppinglists.json";
				break;
			case LISTINGS:
				url += "listings.json";
				break;
			case ARTICLES:
				url += "articles.json";
				break;
			}
			
			initConnection(url);
//			receive response and make it a string
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			response = buffer.toString();
			
			closeConnection();
//			convert response to Json Array
			jsonArray = new JSONArray(response);
			
			} catch (Exception e) {
				Log.e(HttpConnection.class.getSimpleName(), e.getMessage());
			}
			return jsonArray;
	}
	
}
