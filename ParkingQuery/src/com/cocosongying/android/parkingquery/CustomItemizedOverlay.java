package com.cocosongying.android.parkingquery;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	private Context context;

	public CustomItemizedOverlay(Drawable arg0) {
		super(boundCenterBottom(arg0));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int index) {
		// TODO Auto-generated method stub
		return (OverlayItem) mapOverlays.get(index);
	}

	public CustomItemizedOverlay(Drawable arg0, Context context) {
		this(arg0);
		this.context = context;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mapOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		OverlayItem item = (OverlayItem) mapOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	public void addOverlay(OverlayItem overlay) {
		mapOverlays.add(overlay);
		this.populate();
	}
}
