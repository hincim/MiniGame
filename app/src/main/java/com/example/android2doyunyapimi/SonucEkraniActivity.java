package com.example.android2doyunyapimi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.android2doyunyapimi.databinding.ActivitySonucEkraniBinding;

public class SonucEkraniActivity extends AppCompatActivity {

    private ActivitySonucEkraniBinding t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = ActivitySonucEkraniBinding.inflate(getLayoutInflater());
        setContentView(t.getRoot());

        int skor = getIntent().getIntExtra("skor",0);
        t.textViewToplamSkor.setText(String.valueOf(skor));

        SharedPreferences sp = getSharedPreferences("Sonuc",MODE_PRIVATE);
        int enYuksekSkor = sp.getInt("enYuksekSkor",0);
        // ilk açıldığı anda kayıt yoksa ilk değer 0 olsun.

        if (skor > enYuksekSkor){

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("enYuksekSkor",skor);
            editor.commit();

            t.textViewEnYuksekSkor.setText(String.valueOf(skor));

        }else {

            t.textViewEnYuksekSkor.setText(String.valueOf(enYuksekSkor));

        }

        t.buttonTekrarDene.setOnClickListener(view -> {
            startActivity(new Intent(SonucEkraniActivity.this,MainActivity.class));
            finish();
        });
    }
}