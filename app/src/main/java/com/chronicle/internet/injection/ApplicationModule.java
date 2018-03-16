package com.chronicle.internet.injection;

import com.chronicle.internet.injection.scope.ApplicationScope;
import com.chronicle.internet.models.NewsApiModels;
import com.chronicle.internet.service.NewsApiService;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chronicle.internet.configuration.RemoteConfigurations.FEED_ITEM_NEW_DESIGN_ENABLED;
import static com.chronicle.internet.configuration.RemoteConfigurations.IN_APP_ARTICLE_DETAILS;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    protected static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @ApplicationScope
    static Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @ApplicationScope
    static NewsApiService provideNewsApiClient(Retrofit retrofit) {
        return retrofit.create(NewsApiService.class);
    }

    @Provides
    @ApplicationScope
    static NewsApiModels provideNewsApiModels() {
        return NewsApiModels.create();
    }

    @Provides
    @ApplicationScope
    static Map<String, Object> provideDefaultRemoteConfigurations() {
        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(IN_APP_ARTICLE_DETAILS, false);
        defaultConfigMap.put(FEED_ITEM_NEW_DESIGN_ENABLED, false);

        return Collections.unmodifiableMap(defaultConfigMap);
    }

    @Provides
    @ApplicationScope
    static FirebaseRemoteConfig provideFirebaseRemoteConfig(
            Map<String, Object> defaultConfigurations) {
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Apply config settings and default values.
        firebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        firebaseRemoteConfig.setDefaults(defaultConfigurations);

        return firebaseRemoteConfig;
    }
}
