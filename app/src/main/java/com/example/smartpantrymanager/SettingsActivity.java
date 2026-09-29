package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Toast.makeText(
                this,
                "Settings opened",
                Toast.LENGTH_SHORT
        ).show();
    }
}