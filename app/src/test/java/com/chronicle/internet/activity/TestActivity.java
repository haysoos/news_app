package com.chronicle.internet.activity;

import com.chronicle.internet.activity.BaseActivity;
import com.chronicle.internet.controller.Controller;

import static org.mockito.Mockito.mock;

public class TestActivity extends BaseActivity {
    @Override
    protected Controller buildController() {
        return mock(Controller.class);
    }
}
