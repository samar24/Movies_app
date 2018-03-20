package com.example.samar.moviesapp;
/**
 * Created by Samar on 12/18/2015.
 */
import java.io.IOException;
import java.util.HashMap;

public class UserFunctions {
     
   
    private static String Movies ="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
	private static String Movies_voteAverage ="http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc";
	private static String Movies_videos ="http://api.themoviedb.org/3/movie/";

    private String  apiSecret = "";

    // constructor
    public UserFunctions(){
      
    }
    public String GetMoviesByPopyilarity(String ShowType ,String SortType) throws IOException{
   	 String json;
   	   JSONParser jsonParser = new JSONParser();

   	 // Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();
   	 params.put("apiSecret", "");
   	  json = jsonParser.makeHttpRequest(Movies+"&api_key="+apiSecret, "POST", params);
   	 return json;
   	
   }
	public String GetMoviesByVoteAverage(String ShowType ,String SortType) throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();

		// Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();
		params.put("apiSecret", "");
		json = jsonParser.makeHttpRequest(Movies_voteAverage+"&api_key="+apiSecret, "POST", params);
		return json;

	}
	public String GetMovieVideos(String MovieId ) throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();
//		Log.d("YARAB222", MovieId);
		// Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();

		//params.put("id",MovieId );
		//params.put("apiSecret", "");
		json = jsonParser.makeHttpRequest(Movies_videos+MovieId+"/videos?api_key="+apiSecret, "GET", params);
		return json;

	}
	public String GetMoviesReviews(String MovieId ) throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();

		// Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();

		//params.put("id",MovieId );
		//params.put("apiSecret", "");
		json = jsonParser.makeHttpRequest(Movies_videos+MovieId+"/reviews?api_key="+apiSecret, "GET", params);
		return json;

	}
}
