package com.example.android2doyunyapimi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android2doyunyapimi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(t.getRoot());

        t.buttonBasla.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,OyunEkraniActivity.class));
            finish();
        });
    }
}