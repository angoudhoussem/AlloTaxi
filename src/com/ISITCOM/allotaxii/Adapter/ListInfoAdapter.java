package com.ISITCOM.allotaxii.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Bean.Taxi;

public class ListInfoAdapter extends BaseAdapter {

	private List<Taxi> listData;

	private LayoutInflater layoutInflater;

	public ListInfoAdapter(Context context,List<Taxi> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int position) {
		return listData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.list_row, null);

			holder = new ViewHolder();

			holder.textView = (TextView) convertView.findViewById(R.id.title);
			holder.textView2 = (TextView) convertView.findViewById(R.id.artist);
			holder.image = (ImageView) convertView.findViewById(R.id.list_image);
			

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Taxi newsItem = (Taxi) listData.get(position);
		holder.textView.setText(newsItem.getNumerovehicule());
		//holder.textView2.setText(newsItem.get);
	//	holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(newsItem.getLogo()));
		return convertView;
	}
	static class ViewHolder {
		TextView textView;
		TextView textView2;
		ImageView image;

	}
}
