package com.chronicle.internet.injection;

import com.chronicle.internet.NewsApplication;
import com.chronicle.internet.models.NewsApiModels;
import com.chronicle.internet.service.NewsApiService;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(NewsApplication newsApplication);

    FirebaseRemoteConfig firebaseRemoteConfig();

    NewsApiService newsApiService();

    NewsApiModels newsApiModels();

    Retrofit retrofit();

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
