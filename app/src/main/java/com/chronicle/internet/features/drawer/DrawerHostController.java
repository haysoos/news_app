package com.chronicle.internet.features.drawer;

import android.support.annotation.MenuRes;

import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.controller.Controller;

public abstract class DrawerHostController extends Controller {

    public DrawerHostController(BaseActivity activity) {
        super(activity);
    }

    protected abstract void onMenuItemSelected(int menuItemId);

    @MenuRes
    public abstract int getMenuId();
}
