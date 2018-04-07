package com.sust.gpstracker;

import android.app.Application;

public class GPSTracker extends Application {

	public static String url = "http://172.27.131.149/vtsraheeg/add_position.php";
	LocationService loc = LocationService.getInstance();

	@Override
	public void onCreate() {
		super.onCreate();
		loc.init(this);
	}

}
