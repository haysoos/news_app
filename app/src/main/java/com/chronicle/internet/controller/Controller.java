package com.chronicle.internet.controller;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chronicle.internet.NewsApplication;
import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.injection.ApplicationComponent;

import javax.annotation.Nullable;

public abstract class Controller<T extends View> {

    private final BaseActivity mActivity;
    private View mView;
    private ViewGroup mParent;

    public Controller(BaseActivity activity) {
        mActivity = activity;
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public ApplicationComponent getApplicationComponent() {
        return ((NewsApplication) mActivity.getApplication()).getComponent();
    }

    public void initialize(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        setParent(parent);
        initializeController(parent, savedInstanceState);
    }

    public void addView(View view) {
        mView = view;
        mParent.addView(view);
    }

    protected void setParent(ViewGroup parent) {
        mParent = parent;
    }

    protected abstract void initializeController(ViewGroup parent, @Nullable Bundle savedInstanceState);
}
