package com.chronicle.internet.injection;

import com.chronicle.internet.NewsApplication;
import com.chronicle.internet.models.NewsApiModels;
import com.chronicle.internet.service.NewsApiService;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dagger.Component;
import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

@Component
public class TestApplicationComponent implements ApplicationComponent {
    @Override
    public void inject(NewsApplication newsApplication) { }

    @Override
    public FirebaseRemoteConfig firebaseRemoteConfig() {
        return mock(FirebaseRemoteConfig.class);
    }

    @Override
    public NewsApiService newsApiService() {
        return mock(NewsApiService.class);
    }

    @Override
    public NewsApiModels newsApiModels() {
        return mock(NewsApiModels.class);
    }

    @Override
    public Retrofit retrofit() {
        return mock(Retrofit.class);
    }
}
