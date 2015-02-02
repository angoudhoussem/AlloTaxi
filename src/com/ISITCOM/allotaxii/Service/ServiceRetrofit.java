package com.ISITCOM.allotaxii.Service;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import com.ISITCOM.allotaxii.Bean.Position;
import com.ISITCOM.allotaxii.Bean.Taxi;

public interface ServiceRetrofit {

	@GET("/ajout/{adresse}/{login}/{motdepasse}/{nom}/{prenom}/{telephone}")
	void createUser(@Path("adresse") String adresse,
			@Path("login") String login, @Path("motdepasse") String motdepasse,
			@Path("nom") String nom, @Path("prenom") String prenom,
			@Path("telephone") String telephone, Callback<String> cb);

	@GET("/authen/{login}/{password}")
	void authentification(@Path("login") String email,
			@Path("password") String password, Callback<String> cb);

	@GET("/recherche")
	void listTaxi(@Query("limit") int limit, @Query("offset") int offset,
			Callback<List<Taxi>> callback);

	@GET("/rechercheposition")
	void listPosition(@Query("limit") int limit, @Query("offset") int offset,
			Callback<List<Position>> callback);

	@GET("/ajoutfavoris/{idchau}/{login}/{pwd}/{idtaxi}")
	void ajoutfavoris(@Path("indchau") String indchau,
			@Path("login") String login, @Path("password") String password,
			@Path("idtaxi") String idtaxi, Callback<String> cb);

	@GET("/ajoutfidelite/{idchau}/{login}/{pwd}/{idtaxi}")
	void ajoutfidelite(@Path("indchau") String indchau,
			@Path("login") String login, @Path("password") String password,
			@Path("idtaxi") String idtaxi, Callback<String> cb);
	@GET("/recherche/{login}/{password}")
	void listTaxifavoris(@Path("login") String email,
			@Path("password") String password,
			Callback<List<Taxi>> callback);
	
}
