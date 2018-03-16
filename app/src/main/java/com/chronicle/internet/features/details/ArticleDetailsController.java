package com.chronicle.internet.features.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.controller.Controller;

import javax.annotation.Nullable;

class ArticleDetailsController extends Controller {

    private ArticleDetailsLayout mView;

    public ArticleDetailsController(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initializeController(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        Uri url = intent.getData();
        mView = new ArticleDetailsLayout(getActivity());
        addView(mView);
        mView.setUrl(url.toString());
    }
}
