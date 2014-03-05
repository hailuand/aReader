package com.example.ahailu.blogreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainListActivity extends ListActivity {
	
	protected String[] mBlogPostTitles;
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		
		if(isNetworkAvailable()){
		// Checks network availability
			getBlogPostsTask GetBlogPostsTask = new getBlogPostsTask();
			GetBlogPostsTask.execute();
		}
		else{
			//If the network is not available, produced toast message notifying user
			Toast.makeText(this, "Network is unavailable", Toast.LENGTH_LONG).show();
		}
		
		//Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = 
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// The ConnectivityManager class answers queries about the state of the network connectivity
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		// The NetworkInfo class describes the status of a network interface
		// The getActiveNetworkInfo() method returns details about the currently active data network. Returns a
		// NetworkInfo object.
		
		boolean isAvailable = false; 
		if(networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}
		return isAvailable;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

	private class getBlogPostsTask extends AsyncTask<Object, Void, String> {
	// Connects to URL and retrieves data from it
	// An AsyncTasks does work in parallel with the main UI thread, so that the UI remains responsive to user
	// interface actions, and doesn't stop everything to do one task
		int responseCode = -1;
		@Override
		protected String doInBackground(Object... arg0) {
			try{
				URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + 
			NUMBER_OF_POSTS);
				HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
				connection.connect();
				
				responseCode = connection.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK){
					InputStream inputStream = connection.getInputStream();
					// An InputStream is data stored as bytes.
					Reader reader = new InputStreamReader(inputStream);
					// Reader reads the data from the InputStream as we know that we are dealing with strings
					int contentLength = connection.getContentLength(); 
					// Connection knows how many chars we're dealing with
					char[] charArray = new char[contentLength];
					reader.read(charArray); // Store data in our char array. The read method changes the data in char
					String responseData = new String(charArray); // Convert charArray to strings
					Log.v(TAG, responseData); // Write this data to the log (JSON)
				}
				else{
					Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
				}
				Log.i(TAG, "Code: " + responseCode);
			}
			catch(MalformedURLException e){
				Log.e(TAG, "Exception caught: ", e);
			}
			catch(IOException e){
				Log.e(TAG, "Exception caught: ", e);
			}
			catch(Exception e){
				Log.e(TAG, "Exception caught: ", e);
			}
			return "Code :" + responseCode;
		}
		
	}
}
