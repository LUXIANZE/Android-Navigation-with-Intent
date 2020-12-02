package com.example.android_navigation_with_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ConstraintLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
    }

    public void backToHomeBtnClicked(View view) {
        super.onBackPressed();
    }
}