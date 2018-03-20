package com.example.samar.moviesapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Samar on 1/16/2016.
 */
public class DetailActivityFragment extends Fragment {
    public static final String TAG = DetailActivityFragment.class.getSimpleName();
    private TextView titleTextView;
    TextView detailsMovie;
    private ImageView imageView;
    private ListView list1;
    String MovieId;
    String json="";
    JSONObject jObj;
    JSONArray Jarray;
    TrailerViewsAdapter mTrailersReviewsAdapter;
    UserFunctions userFunctions=new UserFunctions();
    private ArrayList<Trailer_Review_Item> mReviews_trailers_item=new ArrayList<Trailer_Review_Item>();
    private String title1="";
    private String Overview1;
    private String image;
    private String Fav;
    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

         inflater.inflate(R.menu.menu_fragment_detail, menu);

         final MenuItem action_favorite = menu.findItem(R.id.action_favorite);
        // MenuItem action_share = menu.findItem(R.id.action_share);
        if(!title1.equals("")) {
            if (Fav.equals("1")) {

                action_favorite.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
            } else {
                action_favorite.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_favorite:


                if (!title1.equals("")) {
                    DatabaseHandler1 db=new DatabaseHandler1(getActivity(),"Movies",null ,1);

                    if(db.CheckIsDataAlreadyInDBorNot(MovieId)) {
                        if (db.GetMovie(MovieId).GetFavourte().equals("1")) {
                            db.Update(MovieId); // will update it to 0
                            item.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
                            Toast.makeText(getActivity(), "Removes from favourites", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            GridItem Item=new GridItem();
                            Item.SetMovie_id(MovieId);
                            Item.SetFav("1");
                            Item.setTitle(title1);
                            Item.setOverview(Overview1);
                            Item.setImage(image);
                            db.addMovie(Item);
                            item.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
                        }
                    }
                    else{
                        GridItem Item=new GridItem();
                        Item.SetMovie_id(MovieId);
                        Item.SetFav("1");
                        Item.setTitle(title1);
                        Item.setOverview(Overview1);
                        Item.setImage(image);
                        db.addMovie(Item);
                        item.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
                    }


                }
        }
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if (bundle != null) {
            title1 = bundle.getString("title");

            image = bundle.getString("image");
            MovieId = bundle.getString("Movie_id");
            Overview1 = bundle.getString("overview");
            Fav = bundle.getString("Favstate");

        }
        View rootView = inflater.inflate(R.layout.activity_details_view, container, false);
        list1=(ListView)rootView.findViewById(R.id.List_movies_reviews_trailers);
        new ProgressTask().execute();
        // ActionBar actionBar = getSupportActionBar();
      //  actionBar.hide();
        return  rootView;
    }
    class ProgressTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;
        private String json3;
        private JSONObject jObj3;
        Trailer_Review_Item item;
        int k=0;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(),R.style.NewDialog);
            progressDialog .setMessage("Loading...");

            progressDialog .setCancelable(false);
            progressDialog .show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                json = userFunctions.GetMovieVideos(MovieId);

                jObj = new JSONObject(json);
                Jarray = jObj.getJSONArray("results");
                for (int i = 0; i < Jarray.length(); i++) {
                    try {
                        item = new Trailer_Review_Item();

                        JSONObject obj1 = Jarray.getJSONObject(i);
                        String Video_id=obj1.getString("id");
                        String Iso=obj1.getString("iso_639_1");
                        String key=obj1.getString("key");
                        String name=obj1.getString("name");
                        String site=obj1.getString("site");
                        String type=obj1.getString("type");
                        item.Set_title_videos(name);
                        item.Set_ID_videos(Video_id);
                        item.Set_type("0");
                        // item.setImage(Poster_thumb);
                        // item.SetMovie_id(Movie_id);
                        mReviews_trailers_item.add(item);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                }


            });
            return"done";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            new   ProgressTask1().execute();
            if(progressDialog!=null)
                progressDialog.dismiss();
        }
    }
    class ProgressTask1 extends AsyncTask<String, Integer, String> { //togetThereviews

        private ProgressDialog progressDialog;
        private String json3;
        private JSONObject jObj3;
        Trailer_Review_Item item;
        int k=0;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(),R.style.NewDialog);
            progressDialog .setMessage("Loading...");

            progressDialog .setCancelable(false);
            progressDialog .show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                json = userFunctions.GetMoviesReviews(MovieId);

                jObj = new JSONObject(json);
                Jarray = jObj.getJSONArray("results");
                for (int i = 0; i < Jarray.length(); i++) {
                    try {
                        item = new Trailer_Review_Item();

                        JSONObject obj1 = Jarray.getJSONObject(i);
                        String review_id=obj1.getString("id");
                        String Author=obj1.getString("author");
                        String Content=obj1.getString("content");

                        item.Set_review_author_reviews(Author);
                        item.Set_review_content_reviews(Content);
                        item.Set_type("1");
                        // item.setImage(Poster_thumb);
                        // item.SetMovie_id(Movie_id);
                        mReviews_trailers_item.add(item);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                }


            });
            return"done";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            View header = getActivity().getLayoutInflater().inflate(R.layout.headerview,null);
            TextView title =(TextView)header.findViewById(R.id.Movie_title);
            TextView Overview =(TextView)header.findViewById(R.id.Movie_descryption);
            ImageView ImageMov=(ImageView)header.findViewById(R.id.Movie_photo);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//"+image).into(ImageMov);
            title.setText(title1);
            Overview.setText(Overview1);

            list1.addHeaderView(header);
            mTrailersReviewsAdapter = new TrailerViewsAdapter(getActivity(), R.layout.review_item_view,mReviews_trailers_item );
            list1.setAdapter(mTrailersReviewsAdapter);

            if(progressDialog!=null)
                progressDialog.dismiss();
        }
    }
}
