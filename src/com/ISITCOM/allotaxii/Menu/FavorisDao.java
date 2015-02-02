package com.ISITCOM.allotaxii.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Adapter.CustomAdapter;
import com.ISITCOM.allotaxii.Bean.Taxi;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FavorisDao extends Fragment {
	ArrayList<Taxi> dataList = new ArrayList<Taxi>();
	ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.listtaxi, container, false);
		lv = (ListView) rootView.findViewById(R.id.listView1);
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				Constant.URL + "/entity.favoris").build();
		final ServiceRetrofit rest = restAdapter.create(ServiceRetrofit.class);

		rest.listTaxifavoris(Constant.Email, Constant.Password,
				new Callback<List<Taxi>>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						System.out.println("failer"
								+ arg0.getLocalizedMessage());
					}

					@Override
					public void success(List<Taxi> ListMgs, Response arg1) {
						// TODO Auto-generated method stub
						System.out.println("successFavoris");
						System.out.println("Numerovehicule"+ListMgs.get(0).getNumerovehicule());
						System.out.println("list" + ListMgs.size());

						dataList.addAll(ListMgs);
						ListAdapter listinfoadapter = new CustomAdapter(
								getActivity(), R.layout.list_row, dataList);
						lv.setAdapter(listinfoadapter);

					}

				});
		return rootView;
	}

}
