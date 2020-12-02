package com.example.android_navigation_with_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RelativeLayout extends AppCompatActivity {

    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_layout);
        input = (EditText) findViewById(R.id.input);
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    private void setResult(){
        String name = input.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("userName", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void backToHomeBtnClicked(View view) {
        setResult();
        super.onBackPressed();
    }
}