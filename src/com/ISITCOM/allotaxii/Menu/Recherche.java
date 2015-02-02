package com.ISITCOM.allotaxii.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ISITCOM.allotaxii.DetailActivity;
import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Adapter.CustomAdapter;
import com.ISITCOM.allotaxii.Bean.Taxi;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;


public class Recherche extends Fragment {
	ArrayList<Taxi> dataList = new ArrayList<Taxi>();
	ListView lv;

	public Recherche() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.listtaxi, container, false);
		lv = (ListView) rootView.findViewById(R.id.listView1);
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				Constant.URL + "/entity.taxi").build();
		final ServiceRetrofit rest = restAdapter.create(ServiceRetrofit.class);

		rest.listTaxi(100, 100, new Callback<List<Taxi>>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				System.out.println("failer" + arg0.getLocalizedMessage());
			}

			@Override
			public void success(List<Taxi> ListMgs, Response arg1) {
				// TODO Auto-generated method stub
				System.out.println("success");

				System.out.println("list" + ListMgs.size());

				dataList.addAll(ListMgs);
				ListAdapter listinfoadapter = new CustomAdapter(getActivity(),
						R.layout.list_row, dataList);
				lv.setAdapter(listinfoadapter);

			}

		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Taxi t = (Taxi) lv.getItemAtPosition(position);
				Constant.t = t;
				Intent intent = new Intent(getActivity(),
						DetailActivity.class);
				intent.putExtra("valeur", "bonjour" + position);

				startActivity(intent);
			}
		});

		return rootView;
	}
}
