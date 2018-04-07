package com.sust.gpstracker;

import java.io.IOException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationService implements LocationListener {

	private static LocationService instance = new LocationService();

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 2; // in
																		// Meters

	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000 * 60; // in
																		// Milliseconds
	private Context context;
	private static boolean gpsEnabled;
	private static Location currentLocation;

	public void init(Context context) {
		this.context = context;
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);

	}

	private LocationService() {
	}

	public static LocationService getInstance() {
		return instance;
	}

	@Override
	public void onLocationChanged(Location loc) {
		gpsEnabled = true;

		currentLocation = loc;
		if (loc != null) {
			HttpRmi con = new HttpRmi(GPSTracker.url);
			con.add("gps_id", "1");
			con.add("latitude", loc.getLatitude() + "");
			con.add("longitude", loc.getLongitude() + "");
			try {
				Toast.makeText(context, con.execute(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
			System.out.println("Send   Loc : "+loc.getLatitude()+" , "+loc.getLongitude());
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(context, "Gps Disabled", Toast.LENGTH_LONG).show();
		gpsEnabled = false;
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(context, "Gps Enabled", Toast.LENGTH_LONG).show();
		gpsEnabled = true;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public static boolean isGpsEnabled() {
		return gpsEnabled;
	}

	public static Location getCurrentLocation() {
		return currentLocation;

	}

}/* End of Class MyLocationListener */