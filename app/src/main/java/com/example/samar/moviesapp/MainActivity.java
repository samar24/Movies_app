package com.example.samar.moviesapp;
/**
 * Created by Samar on 12/18/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity  implements MainFragment.Callback {
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailActivityFragment(),
                                DetailActivityFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
         /*   getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_movies, new MainFragment(),
                            DetailActivityFragment.TAG)
                    .commit();*/
        }
    }

    @Override
    public void onItemSelected(GridItem Movie) {
      if (mTwoPane) {
           Bundle arguments = new Bundle();
           arguments.putCharSequence("title", Movie.getTitle());
          arguments.putCharSequence("image", Movie.getImage());
          arguments.putCharSequence("Movie_id", Movie.Get_movie_id());
          arguments.putCharSequence("overview", Movie.Get_Overview());
          arguments.putCharSequence("release_date", Movie.GetReleaseDate());
          arguments.putCharSequence("vote_average", Movie.GetVoteAverage());
          arguments.putCharSequence("Favstate", Movie.GetFavourte());

          DetailActivityFragment fragment = new DetailActivityFragment();
           fragment.setArguments(arguments);

           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.movie_detail_container, fragment, DetailActivityFragment.TAG)
                   .commit();
        } else {
           Intent intent = new Intent(this, DetailsActivity.class)
                   .putExtra("title", Movie.getTitle())
                   .putExtra("image", Movie.getImage())
                   .putExtra("Movie_id", Movie.Get_movie_id())
                   .putExtra("overview", Movie.Get_Overview())
                   .putExtra("release_date", Movie.GetReleaseDate())
                   .putExtra("vote_average", Movie.GetVoteAverage())
                   .putExtra("Favstate", Movie.GetFavourte());
          startActivity(intent);
        }

    }


    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */

}
