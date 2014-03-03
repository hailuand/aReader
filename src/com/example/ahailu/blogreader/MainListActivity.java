package com.example.ahailu.blogreader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainListActivity extends ListActivity {
	
	protected String[] mAndroidNames = {
			"Android Beta",
			"Android 1.0",
			"Android 1.1",
			"Cupcake",
			"Donut",
			"Eclair",
			"Froyo",
			"Gingerbread",
			"Honeycomb",
			"Ice Cream Sandwhich",
			"Jelly Bean"
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAndroidNames);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

}
