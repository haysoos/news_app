package com.chronicle.internet.features.headlines;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chronicle.internet.R;
import com.chronicle.internet.models.Article;
import com.chronicle.internet.models.Headlines;

public class HeadlinesView extends FrameLayout {

    private final ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private HeadlinesAdapter mHeadlinesAdapter;

    public HeadlinesView(Context context, HeadlinesAdapter headlinesAdapter) {
        super(context);
        inflate(context, R.layout.view_headlines, this);
        mProgressBar = findViewById(R.id.headlines_view_progress_bar);
        mRecyclerView = findViewById(R.id.home_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mHeadlinesAdapter = headlinesAdapter;
        mRecyclerView.setAdapter(mHeadlinesAdapter);
    }

    public void bindModel(Headlines headlines) {
        mHeadlinesAdapter.bind(headlines);
    }

    void showProgressBar() {
        mProgressBar.setVisibility(VISIBLE);
    }

    void hideProgressBar() {
        mProgressBar.setVisibility(GONE);
    }

    interface Listener {
        void onArticleTapped(Article article);
    }
}
