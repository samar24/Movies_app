package com.example.samar.moviesapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Samar on 1/15/2016.
 */
public class MainFragment extends Fragment {
    private GridView mGridView;
   // private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData= new ArrayList<GridItem>();
    UserFunctions userFunctions=new UserFunctions();

    public MainFragment() {
    }
    public interface Callback {
        void onItemSelected(GridItem movie);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.menu_fragment_main, menu);

        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);
        MenuItem action_sort_by_favorite = menu.findItem(R.id.action_sort_by_favorite);


    }
    private void updateMovies(String sort_by) {
        if (!sort_by.contentEquals("Favourite")) {
            new AsyncTask1(sort_by).execute(sort_by);
        } else {
            DatabaseHandler1 db=new DatabaseHandler1(getActivity(),"Movies",null ,1);
            ArrayList <GridItem> Favmove=db.GetFavMovies();

            mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, Favmove);
            mGridView.setAdapter(mGridAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    GridItem item = (GridItem) parent.getItemAtPosition(position);
                    //Get item at position
          /*          GridItem item = (GridItem) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                    intent.putExtra("title", item.getTitle()).
                            putExtra("Movie_id",item.Get_movie_id()).
                            putExtra("image", item.getImage()).
                            putExtra("overview", item.Get_Overview()).
                            putExtra("Favstate", item.GetFavourte());
                    startActivity(intent);*/
                    ((Callback) getActivity()).onItemSelected(item);
                }
            });

            mGridAdapter.setGridData(Favmove);
            //getThe Favourites
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                updateMovies("popularity");
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                updateMovies("vote_average");
                return true;
            case R.id.action_sort_by_favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                updateMovies("Favourite");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
       // mProgressBar = (ProgressBar)view. findViewById(R.id.progressBar);

        //Initialize with empty data



        updateMovies("popularity");
      //  mProgressBar.setVisibility(View.VISIBLE);
        return view;

    }
    public class AsyncTask1 extends AsyncTask<String, Void, Integer> {
        JSONArray jsonArray;
        JSONObject jObj;
        String json;
        GridItem item;
        private ProgressDialog progressDialog;
        String SortCretriea="";
       public AsyncTask1(String SortBy){
            this.SortCretriea=SortBy;
        }
        @Override
        protected Integer doInBackground(String... params) {
            try {
                json = userFunctions.GetMoviesByPopyilarity(SortCretriea, "desc");

                jObj = new JSONObject(json);
                jsonArray = jObj.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        item = new GridItem();

                        JSONObject obj1 = jsonArray.getJSONObject(i);
                        String Movie_id=obj1.getString("id");
                        String Poster_thumb=obj1.getString("poster_path");
                        String OverView=obj1.getString("overview");
                        String Popuilirity=obj1.getString("popularity");
                        String Votecount=obj1.getString("vote_count");
                        String title=obj1.getString("original_title");
                        item.setTitle(title);
                        item.setImage(Poster_thumb);
                        item.SetMovie_id(Movie_id);
                        item.setOverview(OverView);
                        DatabaseHandler1 db=new DatabaseHandler1(getActivity(),"Movies",null ,1);
                        if(!db.CheckIsDataAlreadyInDBorNot(Movie_id)) { // in Favourites
                            item.SetFav("0");
                        }
                        else {
                            item.SetFav("1");
                        }
                        mGridData.add(item);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(progressDialog!=null)
                progressDialog.dismiss();

            mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
            mGridView.setAdapter(mGridAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    //Get item at position
                    GridItem item = (GridItem) parent.getItemAtPosition(position);

                  /*  Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);


                    int[] screenLocation = new int[2];
                    imageView.getLocationOnScreen(screenLocation);


                    intent.putExtra("left", screenLocation[0]).
                            putExtra("top", screenLocation[1]).
                            putExtra("width", imageView.getWidth()).
                            putExtra("height", imageView.getHeight()).
                            putExtra("title", item.getTitle()).
                            putExtra("Movie_id",item.Get_movie_id()).
                            putExtra("image", item.getImage()).
                            putExtra("overview", item.Get_Overview()).
                    putExtra("Favstate", item.GetFavourte());
                    startActivity(intent);*/
                    ((Callback) getActivity()).onItemSelected(item);
                }
            });

            mGridAdapter.setGridData(mGridData);


          //  mProgressBar.setVisibility(View.GONE);
        }
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(),R.style.NewDialog);
            progressDialog .setMessage("Loading...");

            progressDialog .setCancelable(false);
            progressDialog .show();
        }
    }

    }
