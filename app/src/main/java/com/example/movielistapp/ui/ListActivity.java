package com.example.movielistapp.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.R;
import com.example.movielistapp.data.server.model.Film;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListMVPView, MovieRowAdapter.ItemClickListener{

    public final int PAGINATION_MARGIN = 2;
    public final String LANG = "en-US";
    @BindView(R.id.listFilm)
    RecyclerView mRecyclerView;
    ListPresenter mPresenter;
    MovieRowAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        mPresenter = new ListPresenter(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieRowAdapter(ListActivity.this, new ArrayList<Film>());

        mAdapter.setClickListener(ListActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - PAGINATION_MARGIN
                        && firstVisibleItemPosition >= 0) {
                    mPresenter.getMovies(LANG);
                }
            }
        });

        mPresenter.getMovies("en-US");
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("ListActivity", "onItemClick: " + position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onSucceedGetResult(ArrayList<Film> movies, int page) {
        Toast.makeText(ListActivity.this,"PAGE : "+ page, Toast.LENGTH_SHORT).show();
        mAdapter.addList(movies);
    }

    @Override
    public void viewToast(String message) {
        Toast.makeText(ListActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
