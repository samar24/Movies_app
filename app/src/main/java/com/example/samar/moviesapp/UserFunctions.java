package com.example.samar.moviesapp;
/**
 * Created by Samar on 12/18/2015.
 */
import java.io.IOException;
import java.util.HashMap;

public class UserFunctions {
     
   
    private static String Movies ="https://api.themoviedb.org/3/movie/popular?";
	private static String Movies_voteAverage ="https://api.themoviedb.org/3/movie/top_rated?";
	private static String Movies_videos ="http://api.themoviedb.org/3/movie/";

    private String  apiSecret = BuildConfig.API_KEY;

    // constructor
    public UserFunctions(){
      
    }
    public String GetMoviesByPopyilarity() throws IOException{
   	 String json;
   	   JSONParser jsonParser = new JSONParser();

		HashMap<String, String>  params = new HashMap<String, String>();
   	  json = jsonParser.makeHttpRequest(Movies+"&api_key="+apiSecret, "GET", params);
   	 return json;
   	
   }
	public String GetMoviesByVoteAverage() throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();
		HashMap<String, String>  params = new HashMap<String, String>();
		json = jsonParser.makeHttpRequest(Movies_voteAverage+"&api_key="+apiSecret, "GET", params);
		return json;

	}
	public String GetMovieVideos(String MovieId ) throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();

		// Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();
		json = jsonParser.makeHttpRequest(Movies_videos+MovieId+"/videos?api_key="+apiSecret, "GET", params);
		return json;

	}
	public String GetMoviesReviews(String MovieId ) throws IOException{
		String json;
		JSONParser jsonParser = new JSONParser();

		// Building Parameters ( you can pass as many parameters as you want)
		HashMap<String, String>  params = new HashMap<String, String>();
		json = jsonParser.makeHttpRequest(Movies_videos+MovieId+"/reviews?api_key="+apiSecret, "GET", params);
		return json;

	}
}
