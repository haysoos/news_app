package com.chronicle.internet.features.drawer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.chronicle.internet.R;
import com.chronicle.internet.activity.BaseActivity;

public class DrawerView extends FrameLayout implements NavigationView.OnNavigationItemSelectedListener {

    private final ActionBarDrawerToggle mDrawerToggle;
    private final Toolbar mToolbar;
    private final Listener mListener;
    private DrawerLayout mDrawerLayout;

    public DrawerView(Context context, Listener listener, @MenuRes int menu) {
        super(context);
        inflate(context, R.layout.drawer_layout, this);
        mListener = listener;
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mDrawerToggle = new ActionBarDrawerToggle((Activity) context, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_open) {
            public void onDrawerClosed(View view) {
                ((BaseActivity)getContext()).supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                ((BaseActivity)getContext()).supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateMenu(menu);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        mListener.onNavigationMenuItemSelected(item);

        return true;
    }

    interface Listener {
        void onNavigationMenuItemSelected(MenuItem item);
    }
}
