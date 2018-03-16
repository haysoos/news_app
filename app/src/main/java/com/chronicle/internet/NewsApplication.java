package com.chronicle.internet;

import android.app.Application;
import android.util.Log;

import com.chronicle.internet.injection.ApplicationComponent;
import com.chronicle.internet.injection.DaggerApplicationComponent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

public class NewsApplication extends Application {

    @Inject FirebaseRemoteConfig mRemoteConfig;

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder().build();
        mComponent.inject(this);

        fetchConfig();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

    private void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via
                        // FirebaseRemoteConfig get<type> calls.
                        mRemoteConfig.activateFetched();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // There has been an error fetching the config
                        Log.w("NewsApplication", "Error fetching config: " +
                                e.getMessage());
                    }
                });
    }
}
