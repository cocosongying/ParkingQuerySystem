package com.cocosongying.android.parkingquery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ParkingQueryActivity extends Activity {
	private Button btn_simple;
	private Button btn_map;
	private Button btn_help;
	private Button btn_about;
	private Button btn_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_parking_query);
		btn_simple = (Button) findViewById(R.id.parking_query_simple);
		btn_map = (Button) findViewById(R.id.parking_query_map);
		btn_help = (Button) findViewById(R.id.parking_query_help);
		btn_about = (Button) findViewById(R.id.parking_query_about);
		btn_exit = (Button) findViewById(R.id.parking_query_exit);

		btn_simple.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParkingQueryActivity.this,
						SimpleModel.class);
				startActivity(intent);
			}
		});
		btn_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParkingQueryActivity.this,
						MapModel.class);
				startActivity(intent);
			}
		});
		btn_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParkingQueryActivity.this,
						Help.class);
				startActivity(intent);
			}
		});
		btn_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParkingQueryActivity.this,
						About.class);
				startActivity(intent);
			}
		});
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}
