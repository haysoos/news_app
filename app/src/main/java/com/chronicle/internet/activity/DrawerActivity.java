package com.chronicle.internet.activity;

import android.view.MenuItem;

import com.chronicle.internet.controller.Controller;
import com.chronicle.internet.features.drawer.DrawerController;
import com.chronicle.internet.features.drawer.DrawerHostController;

public abstract class DrawerActivity extends BaseActivity {
    private DrawerController mController;

    protected abstract DrawerHostController buildChildController();

    @Override
    protected Controller buildController() {
        mController = new DrawerController(buildChildController());
        return mController;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mController.openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
