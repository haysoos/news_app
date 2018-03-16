package com.chronicle.internet;

import com.chronicle.internet.injection.ApplicationComponent;
import com.chronicle.internet.injection.TestApplicationComponent;


public class TestNewsApplication extends NewsApplication {

    @Override
    public void onCreate() { }

    @Override
    public ApplicationComponent getComponent() {
        return new TestApplicationComponent();
    }
}
