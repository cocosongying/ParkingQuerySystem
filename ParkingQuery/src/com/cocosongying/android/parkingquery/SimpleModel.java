package com.cocosongying.android.parkingquery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleModel extends Activity {
	private Button btn_ok;
	private EditText et_input;
	private TextView tv_result;
	private String inputStr;
	private String resultStr;
	private String explainStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.parking_query_simple);
		btn_ok = (Button) findViewById(R.id.btn_parking_query_simple);
		et_input = (EditText) findViewById(R.id.et_parking_query_simple);
		tv_result = (TextView) findViewById(R.id.tv_parking_query_simple);

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inputStr = et_input.getText().toString();
				tv_result.setText(R.string.parking_simple_result);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("namestr", inputStr));
				InputStream is = null;
				try {
					HttpParams parms = new BasicHttpParams();
					parms.setParameter("charset", HTTP.UTF_8);
					HttpClient httpclient = new DefaultHttpClient(parms);
					HttpPost httppost = new HttpPost(
							"http://10.0.2.2/getRequestByName.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
				} catch (Exception e) {
					Log.e("log_tag", "Error in http connection " + e.toString());
				}
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "utf-8"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					is.close();
					resultStr = sb.toString().trim();
				} catch (Exception e) {
					Log.e("log_tag", "Error converting result " + e.toString());
				}
				try {
					JSONArray jArray = new JSONArray(resultStr);
					explainStr = "";
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						explainStr += "============\n停车场名："
								+ json_data.getString("name") + "\n剩余车位数："
								+ json_data.getInt("remain") + "\n地址："
								+ json_data.getString("address") + "\n备注："
								+ json_data.getString("remarks")
								+ "\n============\n";
						tv_result.setText(explainStr);
					}
				} catch (JSONException e) {
					tv_result.setText("未查询到相关记录！");
					Log.e("log_tag", "Error parsing data " + e.toString());
				}
			}
		});
	}
}
