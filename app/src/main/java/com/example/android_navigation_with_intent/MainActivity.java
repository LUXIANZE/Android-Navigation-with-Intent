package com.example.android_navigation_with_intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                // Get Result from previous intent
                String userName = data.getStringExtra("userName");

                // Toast Presets
                Context context = getApplicationContext();
                CharSequence text = "Welcome Back, " + userName + "!";
                int duration = Toast.LENGTH_SHORT;

                // launch Toast
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    public void RelativeLayoutBtnClicked(View view) {
        Intent navigateToRelativeLayout = new Intent(this, RelativeLayout.class);
        startActivityForResult(navigateToRelativeLayout, 1);
    }
    public void ConstraintLayoutBtnClicked(View view) {
        Intent navigateToConstraintLayout = new Intent(this, ConstraintLayout.class);
        startActivity(navigateToConstraintLayout);
    }
    public void GridLayoutBtnClicked(View view) {
        Intent navigateToGridLayout = new Intent(this, GridLayout.class);
        startActivity(navigateToGridLayout);
    }
}