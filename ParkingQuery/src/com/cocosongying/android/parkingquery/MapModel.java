package com.cocosongying.android.parkingquery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MapModel extends MapActivity {
	private MapView mapView;
	private Button mRefresh;
	private static final double mlatitude = 32.197625999999985;
	private static final double mlongitude = 119.46573654232968;
	private GeoPoint mPoint;
	private String resultStr;
	private String explainStr;
	private Drawable drawable;
	private List<Overlay> mapOverlays;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.parking_query_map);
		mapView = (MapView) findViewById(R.id.Map_View);
		mRefresh = (Button) findViewById(R.id.refresh);
		mRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				beginQuery();
			}
		});
		mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.elem_target);
		beginQuery();
		mPoint = new GeoPoint((int) (mlatitude * 1E6), (int) (mlongitude * 1E6));
		MapController mapController = mapView.getController();
		mapController.animateTo(mPoint);
		mapView.setTraffic(true);
		mapView.setEnabled(true);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapController.setZoom(16);
	}

	private void beginQuery() {
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://10.0.2.2/getAllRequest.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			resultStr = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			JSONArray jArray = new JSONArray(resultStr);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				double latitude = json_data.getDouble("latitude");
				double longitude = json_data.getDouble("longitude");
				explainStr = "剩余车位数：" + json_data.getInt("remain") + "\n备注："
						+ json_data.getString("remarks");
				CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
						drawable, this);
				GeoPoint point = new GeoPoint((int) (latitude * 1E6),
						(int) (longitude * 1E6));
				OverlayItem overlayitem = new OverlayItem(point,
						json_data.getString("name"), explainStr);
				itemizedOverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedOverlay);
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
