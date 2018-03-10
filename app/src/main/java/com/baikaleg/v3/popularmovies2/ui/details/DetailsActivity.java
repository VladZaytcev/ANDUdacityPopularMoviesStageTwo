package com.baikaleg.v3.popularmovies2.ui.details;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.baikaleg.v3.popularmovies2.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailsActivity extends DaggerAppCompatActivity implements DetailsNavigator {

    public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    @Inject
    DetailsViewModel viewModel;

    @Inject
    DetailsActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        DetailsActivityFragment detailsFragment =
                (DetailsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (detailsFragment == null) {
            detailsFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, detailsFragment);
            transaction.commit();
        }
        viewModel.setNavigator(this);
    }

    @Override
    protected void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMovieMarked(boolean isFavorite) {
        viewModel.setFavorite(isFavorite);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
