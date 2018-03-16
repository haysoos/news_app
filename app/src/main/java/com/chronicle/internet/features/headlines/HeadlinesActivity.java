package com.chronicle.internet.features.headlines;

import com.chronicle.internet.activity.DrawerActivity;
import com.chronicle.internet.features.drawer.DrawerHostController;

public class HeadlinesActivity extends DrawerActivity {

    @Override
    protected DrawerHostController buildChildController() {
        return new HeadlinesController(this);
    }
}
