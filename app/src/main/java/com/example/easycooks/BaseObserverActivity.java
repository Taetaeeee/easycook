package com.example.easycooks;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseObserverActivity extends AppCompatActivity implements SmartRefrigerator.FridgeObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmartRefrigerator.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartRefrigerator.removeObserver(this);
    }
}