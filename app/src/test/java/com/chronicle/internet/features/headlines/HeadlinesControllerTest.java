package com.chronicle.internet.features.headlines;

import android.content.Intent;

import com.chronicle.internet.BuildConfig;
import com.chronicle.internet.TestNewsApplication;
import com.chronicle.internet.activity.TestActivity;
import com.chronicle.internet.features.details.ArticleDetailsActivity;
import com.chronicle.internet.models.Article;
import com.chronicle.internet.models.NewsApiModels;
import com.chronicle.internet.service.NewsApiService;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import retrofit2.Call;

import static com.chronicle.internet.configuration.RemoteConfigurations.IN_APP_ARTICLE_DETAILS;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestNewsApplication.class)
public class HeadlinesControllerTest {

    private HeadlinesController mController;
    private TestActivity mActivity;

    @Mock private FirebaseRemoteConfig mFirebaseRemoteConfig;
    @Mock private NewsApiModels mModels;
    @Mock private NewsApiService mNewsApiService;
    @Mock private HeadlinesView mView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mActivity = Robolectric.setupActivity(TestActivity.class);

        mController = new HeadlinesController(mActivity, new HeadlinesController.Component() {
            @Override
            public void inject(HeadlinesController controller) {
                controller.mFirebaseRemoteConfig = mFirebaseRemoteConfig;
                controller.mModels = mModels;
                controller.mNewsApiService = mNewsApiService;
                controller.mView = mView;
            }
        });

        when(mNewsApiService.headlines("us")).thenReturn(mock(Call.class));
    }

    @Test
    public void onArticleTapped_whenConfigEnabled_shouldLaunchArticleDetailActivity() throws Exception {
        when(mFirebaseRemoteConfig.getBoolean(IN_APP_ARTICLE_DETAILS)).thenReturn(true);
        Article article = new Article();
        article.setUrl("someUrl");

        mController.onArticleTapped(article);

        Intent expectedIntent = new Intent(mActivity, ArticleDetailsActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void onArticleTapped_whenConfigDisabled_shouldLaunchIntent() throws Exception {
        when(mFirebaseRemoteConfig.getBoolean(IN_APP_ARTICLE_DETAILS)).thenReturn(false);
        Article article = new Article();
        article.setUrl("someUrl");

        mController.onArticleTapped(article);

        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}