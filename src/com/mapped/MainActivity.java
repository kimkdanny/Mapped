package com.mapped;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//import android.view.View;
//import android.webkit.WebSettings;

//import com.metaio.Example.BuildConfig;
//import com.metaio.Example.MainActivity.WebViewHandler;
import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

public class MainActivity extends Activity implements OnClickListener {

	AssetsExtracter task;
	Button way;
	Button navigate;
	Intent wayActivity;
	Intent navigateActivity;
	EditText input;
	Button submit;
	Dialog dialog;
	SharedPreferences data;
	SharedPreferences.Editor editor;
	String strData;
	private static final String key = "MySharedData";
	LocationManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		way = (Button) findViewById(R.id.b1);
		navigate = (Button) findViewById(R.id.b2);

		way.setOnClickListener(this);
		navigate.setOnClickListener(this);

		navigateActivity = new Intent(this, LocationList.class);
		
		MetaioDebug.enableLogging(BuildConfig.DEBUG);
		
		task = new AssetsExtracter();
		task.execute(0);
		
		Intent intent = new Intent(getApplicationContext(), ARActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.b1:
			dialog = new Dialog(this, R.style.myBackgroundStyle);
			dialog.setContentView(R.layout.dialog);
			dialog.show();
			input = (EditText) dialog.findViewById(R.id.input);
			submit = (Button) dialog.findViewById(R.id.confirm);
			submit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (input.getText().toString() != null
							&& !input.getText().toString().equals("")) {
						manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
						Location location = manager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);

						data = getSharedPreferences(key, 0);
						strData = data.getString("data", null);

						String title = input.getText().toString();

						editor = data.edit();
						String append = "name:" + title + ";lat:" + location.getLatitude()
								+ ";long:" + location.getLongitude() + ";";
						editor.putString("data", strData +append);
						editor.commit();
					}
					dialog.hide();
				}
			});
			break;
		case R.id.b2:
			startActivity(navigateActivity);
			break;
		}
	}


	/**
	 * This task extracts all the assets to an external or internal location
	 * to make them accessible to metaio SDK
	 */
	private class AssetsExtracter extends AsyncTask<Integer, Void, Boolean>
	{		
		@Override
		protected Boolean doInBackground(Integer... params) 
		{
			try 
			{
				// Extract all assets and overwrite existing files if debug build
				AssetsManager.extractAllAssets(getApplicationContext(), true);
			} 
			catch (IOException e) 
			{
				MetaioDebug.printStackTrace(Log.ERROR, e);
				return false;
			}

			return true;
		}
	}
}