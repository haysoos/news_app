package com.chronicle.internet.features.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.chronicle.internet.R;
import com.chronicle.internet.controller.Controller;
import com.chronicle.internet.injection.ApplicationComponent;
import com.chronicle.internet.injection.scope.ControllerScope;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.Provides;

public class DrawerController extends Controller implements DrawerView.Listener {

    @Inject DrawerView mView;

    private final DrawerHostController mHostController;

    public DrawerController(DrawerHostController hostController) {
        this(hostController, null);
    }

    DrawerController(DrawerHostController hostController, Component component) {
        super(hostController.getActivity());
        mHostController = hostController;
        if (component == null) {
            component = DaggerDrawerController_Component
                    .builder()
                    .module(new Module(hostController.getActivity(), this, hostController.getMenuId()))
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        component.inject(this);
    }

    @Override
    protected void initializeController(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        addView(mView);
        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        getActivity().setSupportActionBar(toolbar);
        ActionBar actionbar = getActivity().getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        mHostController.initialize((ViewGroup) mView.findViewById(R.id.content_view_group), savedInstanceState);
    }

    @Override
    public void onNavigationMenuItemSelected(MenuItem item) {
        mHostController.onMenuItemSelected(item.getItemId());
    }

    public void openDrawer() {
        mView.openDrawer();
    }

    @ControllerScope
    @dagger.Component(modules = Module.class, dependencies = ApplicationComponent.class)
    interface Component {
        void inject (DrawerController controller);
    }

    @dagger.Module
    static class Module {

        private final Activity mActivity;
        private final DrawerView.Listener mListener;
        @MenuRes private final int mMenuId;

        Module(Activity activity, DrawerView.Listener listener, @MenuRes int menuId) {
            mActivity = activity;
            mListener = listener;
            mMenuId = menuId;
        }

        @ControllerScope
        @Provides
        DrawerView provideView() {
            return new DrawerView(mActivity, mListener, mMenuId);
        }
    }
}
