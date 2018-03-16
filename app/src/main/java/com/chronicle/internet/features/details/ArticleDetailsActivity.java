package com.chronicle.internet.features.details;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.controller.Controller;

public class ArticleDetailsActivity extends BaseActivity {
    @Override
    protected Controller buildController() {
        return new ArticleDetailsController(this);
    }

    public static void launchActivity(Activity activity, String url) {
        Intent intent = new Intent(activity, ArticleDetailsActivity.class);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }
}
