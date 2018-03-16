package com.chronicle.internet.features.headlines;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chronicle.internet.R;
import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.features.details.ArticleDetailsActivity;
import com.chronicle.internet.features.drawer.DrawerHostController;
import com.chronicle.internet.injection.ApplicationComponent;
import com.chronicle.internet.injection.scope.ControllerScope;
import com.chronicle.internet.models.Article;
import com.chronicle.internet.models.Headlines;
import com.chronicle.internet.models.NewsApiModels;
import com.chronicle.internet.service.NewsApiService;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.Provides;
import io.reactivex.Notification;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chronicle.internet.configuration.RemoteConfigurations.IN_APP_ARTICLE_DETAILS;

public class HeadlinesController extends DrawerHostController implements HeadlinesView.Listener {

    @Inject FirebaseRemoteConfig mFirebaseRemoteConfig;
    @Inject HeadlinesView mView;
    @Inject NewsApiService mNewsApiService;
    @Inject NewsApiModels mModels;

    public HeadlinesController(BaseActivity activity) {
        this(activity, null);
    }

    HeadlinesController(BaseActivity activity, Component component) {
        super(activity);
        if (component == null) {
            component = DaggerHeadlinesController_Component
                    .builder()
                    .module(new Module(activity, this))
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        component.inject(this);
    }

    @Override
    protected void initializeController(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        addView(mView);

        queryNewsApi(null);

        mModels.headlines()
                .subscribe(new Consumer<Headlines>() {
                    @Override
                    public void accept(Headlines headlines) throws Exception {
                        mView.bindModel(headlines);
                    }
        });
    }

    @Override
    protected void onMenuItemSelected(int menuItemId) {
        String category = null;
        switch (menuItemId) {
            case R.id.drawer_menu_item_business:
                category = "business";
                break;
            case R.id.drawer_menu_item_entertainment:
                category = "entertainment";
                break;
            case R.id.drawer_menu_item_general:
                category = "general";
                break;
            case R.id.drawer_menu_item_health:
                category = "health";
                break;
            case R.id.drawer_menu_item_science:
                category = "science";
                break;
            case R.id.drawer_menu_item_sports:
                category = "sports";
                break;
            case R.id.drawer_menu_item_technology:
                category = "technology";
                break;
            default:
                category = null;
        }
        queryNewsApi(category);
    }

    @Override
    public int getMenuId() {
        return R.menu.drawer_view;
    }

    @Override
    public void onArticleTapped(Article article) {
        if (mFirebaseRemoteConfig.getBoolean(IN_APP_ARTICLE_DETAILS)) {
            ArticleDetailsActivity.launchActivity(getActivity(), article.getUrl());
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(article.getUrl()));
            getActivity().startActivity(intent);
        }
    }

    private void queryNewsApi(@Nullable String category) {
        // Category choices
        // business entertainment general health science sports technology
        Call<Headlines> headlinesCallback = mNewsApiService.headlines("us", category);
        mView.showProgressBar();
        headlinesCallback.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                mView.hideProgressBar();
                Headlines headlines = response.body();
                mModels.emitHeadlines(headlines);
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                mView.hideProgressBar();
                Toast.makeText(getActivity(), "Failed network call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @ControllerScope
    @dagger.Component(modules = Module.class, dependencies = ApplicationComponent.class)
    interface Component {
        void inject (HeadlinesController controller);
    }

    @dagger.Module
    static class Module {

        private final Activity mActivity;
        private final HeadlinesView.Listener mListener;

        Module(Activity activity, HeadlinesView.Listener listener) {
            mActivity = activity;
            mListener = listener;
        }

        @ControllerScope
        @Provides
        HeadlinesView provideView(HeadlinesAdapter headlinesAdapter) {
            return new HeadlinesView(mActivity, headlinesAdapter);
        }

        @ControllerScope
        @Provides
        HeadlinesAdapter provideHomeAdapter(FirebaseRemoteConfig remoteConfig) {
            return new HeadlinesAdapter(mActivity, mListener, remoteConfig);
        }
    }
}
