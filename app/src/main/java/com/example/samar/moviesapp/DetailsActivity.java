package com.example.samar.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Samar on 12/18/2015.
 */
public class DetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = getIntent().getExtras();
          /*  arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE,
                    getIntent().getParcelableExtra(DetailActivityFragment.DETAIL_MOVIE));
*/
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }

    }

    }

