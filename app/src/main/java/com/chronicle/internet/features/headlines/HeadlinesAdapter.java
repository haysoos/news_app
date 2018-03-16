package com.chronicle.internet.features.headlines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chronicle.internet.R;
import com.chronicle.internet.models.Article;
import com.chronicle.internet.models.Headlines;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;

import static com.chronicle.internet.configuration.RemoteConfigurations.FEED_ITEM_NEW_DESIGN_ENABLED;

class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesViewHolder> {

    private final FirebaseRemoteConfig mRemoteConfig;
    private final HeadlinesView.Listener mListener;
    private final LayoutInflater mLayoutInflater;
    private final List<Article> mArticles;

    public HeadlinesAdapter(
            Context context,
            HeadlinesView.Listener listener,
            FirebaseRemoteConfig remoteConfig) {
        mLayoutInflater = LayoutInflater.from(context);
        mArticles = new ArrayList<>();
        mListener = listener;
        mRemoteConfig = remoteConfig;
    }

    @Override
    public HeadlinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mRemoteConfig.getBoolean(FEED_ITEM_NEW_DESIGN_ENABLED)) {
            return new HeadlinesViewHolder(
                    mLayoutInflater.inflate(R.layout.home_view_holder_v2, parent, false),
                    mListener);
        } else {
            return new HeadlinesViewHolder(
                    mLayoutInflater.inflate(R.layout.home_view_holder, parent, false),
                    mListener);
        }
    }

    @Override
    public void onBindViewHolder(HeadlinesViewHolder holder, int position) {
        holder.bind(mArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void bind(Headlines headlines) {
        mArticles.clear();
        mArticles.addAll(headlines.getArticles());
        notifyDataSetChanged();
    }
}
