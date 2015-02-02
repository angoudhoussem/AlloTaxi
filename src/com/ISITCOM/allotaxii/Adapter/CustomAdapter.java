package com.ISITCOM.allotaxii.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Bean.Taxi;

public class CustomAdapter extends ArrayAdapter<Taxi> {

	Context context;
	List<Taxi> drawerItemList;
	int layoutResID;

	public CustomAdapter(Context context, int layoutResourceID,
			ArrayList<Taxi> dataList) {
		super(context, layoutResourceID, dataList);
		this.context = context;
		this.drawerItemList = dataList;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DrawerItemHolder drawerHolder;
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.list_image);
			drawerHolder.ItemName = (TextView) view.findViewById(R.id.title);
			view.setTag(drawerHolder);
		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		Taxi dItem = (Taxi) this.drawerItemList.get(position);
		drawerHolder.icon.setImageBitmap(ConvertImageFromStringToBitmap.convert(dItem.getPhoto()));
			drawerHolder.ItemName.setText(dItem.getNumerovehicule());
			Log.d("Getview","Passed5");
		
		return view;
			}

	private static class DrawerItemHolder {

		TextView ItemName;
		ImageView icon;
	}
}