package com.chronicle.internet.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.chronicle.internet.controller.Controller;

public abstract class BaseActivity extends AppCompatActivity {

    private Controller mController;

    protected abstract Controller buildController();

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = buildController();
        ViewGroup rootViewGroup = findViewById(android.R.id.content);
        mController.initialize(rootViewGroup, savedInstanceState);
    }

    protected Controller getController() {
        return mController;
    }
}
