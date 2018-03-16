package com.chronicle.internet.features.headlines;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chronicle.internet.R;
import com.chronicle.internet.models.Article;

import javax.annotation.Nullable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

class HeadlinesViewHolder extends RecyclerView.ViewHolder {

    private final TextView mArticleAuthor;
    private final TextView mArticleDescription;
    private final ImageView mArticleImage;
    private final TextView mArticlePublishedAt;
    private final TextView mArticleSource;
    private final TextView mArticleTitle;

    @Nullable private Article mArticle;

    public HeadlinesViewHolder(View itemView, final HeadlinesView.Listener listener) {
        super(itemView);
        mArticleTitle = itemView.findViewById(R.id.home_view_holder_article_title);
        mArticleAuthor = itemView.findViewById(R.id.home_view_holder_article_author);
        mArticleDescription = itemView.findViewById(R.id.home_view_holder_article_description);
        mArticlePublishedAt = itemView.findViewById(R.id.home_view_holder_article_published_at);
        mArticleSource = itemView.findViewById(R.id.home_view_holder_article_source);
        mArticleImage = itemView.findViewById(R.id.home_view_holder_article_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArticle != null) {
                    listener.onArticleTapped(mArticle);
                }
            }
        });
    }

    public void bind(Article article) {
        mArticle = article;
        mArticleTitle.setText(article.getTitle());
        mArticleAuthor.setText(article.getAuthor());

        if (!TextUtils.isEmpty(article.getDescription())) {
            mArticleDescription.setVisibility(VISIBLE);
            mArticleDescription.setText(article.getDescription());
        } else {
            mArticleDescription.setVisibility(GONE);
        }

        mArticlePublishedAt.setText(article.getPublishedAt());
        mArticleSource.setText(article.getSource().getName());
        Glide.with(itemView)
                .load(article.getUrlToImage())
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.errorOf(R.drawable.newspaper_icon))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mArticleImage);
    }
}
