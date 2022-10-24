package com.example.android2doyunyapimi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android2doyunyapimi.databinding.ActivityOyunEkraniBinding;

import java.util.Timer;
import java.util.TimerTask;

public class OyunEkraniActivity extends AppCompatActivity {

    private ActivityOyunEkraniBinding t;

    // Pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int saridaireX;
    private int saridaireY;
    private int siyahkareX;
    private int siyahkareY;
    private int kirmiziucgenX;
    private int kirmiziucgenY;

    // Kontroller
    private boolean dokunmaKontrol = false;
    private boolean baslangicKontrol = false;

    private int skor = 0;

    // boyutlar
    private int ekranGenisligi;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;

    // Hızlar
    private int anakarakterHiz;
    private int saridaireHiz;
    private int siyahkareHiz;
    private int kirmiziucgenHiz;


    private Timer timer = new Timer();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = ActivityOyunEkraniBinding.inflate(getLayoutInflater());
        setContentView(t.getRoot());

        t.siyahkare.setX(-80);
        t.siyahkare.setY(-80);
        t.kirmiziucgen.setX(-80);
        t.kirmiziucgen.setY(-80);
        t.saridaire.setX(-80);
        t.saridaire.setY(-80);
        // cisimleri ekranın dışına çıkardım.

        t.cl.setOnTouchListener((view, motionEvent) -> {

            if (baslangicKontrol){

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Log.e("MotionEvent","Ekrana dokunuldu");
                    dokunmaKontrol = true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Log.e("MotionEvent","Ekranı bıraktı");
                    dokunmaKontrol = false;
                }

            }else {

                baslangicKontrol = true;

                t.textViewOyunaBasla.setVisibility(View.INVISIBLE);

                anakarakterX = (int) t.anakarakter.getX();
                anakarakterY = (int) t.anakarakter.getY();

                anakarakterGenisligi = t.anakarakter.getWidth();
                anakarakterYuksekligi = t.anakarakter.getHeight();
                // görsel nesneler ile ilgili bilgiler öğrenmek istiyorsak
                // dokunma özelliğinin içinde öğrenmemiz gerekiyor.

                ekranGenisligi = t.cl.getWidth();
                ekranYuksekligi = t.cl.getHeight();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        handler.post(new Runnable() {
                            // bu arayüzü tetikleyecek.
                            @Override
                            public void run() {

                                anakarakterHareketEttirme();
                                cisimleriHareketEttir();
                                carpismaKontrol();
                            }
                        });

                    }
                },0,20);
                // 20 ms aralıklarla çalışsın.
            }
            return true;
        });

    }

    public void anakarakterHareketEttirme(){

        anakarakterHiz = Math.round(ekranYuksekligi/60); // 1280 / 60 = 20

        if (dokunmaKontrol){
            anakarakterY -= anakarakterHiz;
            // üste çıksın.
        }else {
            anakarakterY += 20;
        }

        if (anakarakterY <= 0){
            // ana karakterin ekranın içinde kalma durumu.
            anakarakterY = 0;
        }
        if (anakarakterY >= ekranYuksekligi - anakarakterYuksekligi){
            // ana karakterin ekranın içinde kalma durumu.
            anakarakterY = ekranYuksekligi - anakarakterYuksekligi;
        }
        t.anakarakter.setY(anakarakterY);
    }

    public void cisimleriHareketEttir(){

        siyahkareHiz = Math.round(ekranGenisligi/60); // 760 / 60 = 13
        saridaireHiz = Math.round(ekranGenisligi/60); // 760 / 60 = 13
        kirmiziucgenHiz = Math.round(ekranGenisligi/30); // 760 / 30 = 26


        siyahkareX -= siyahkareHiz;
        // hızı bu belirler ve sağdan sola hareket verir.

        if (siyahkareX < 0){
            siyahkareX = ekranGenisligi + 20;
            // siyah kare ekranın dışına ekranGenisliginin + 20 fazlasından
            // başlasın.
            siyahkareY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        t.siyahkare.setX(siyahkareX);
        t.siyahkare.setY(siyahkareY);

        saridaireX -= saridaireHiz;
        // hızı bu belirler ve sağdan sola hareket verir.

        if (saridaireX < 0){
            saridaireX = ekranGenisligi + 20;
            // siyah kare ekranın dışına ekranGenisliginin + 20 fazlasından
            // başlasın.
            saridaireY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        t.saridaire.setX(saridaireX);
        t.saridaire.setY(saridaireY);

        kirmiziucgenX -= kirmiziucgenHiz;
        // hızı bu belirler ve sağdan sola hareket verir.

        if (kirmiziucgenX < 0){
            kirmiziucgenX = ekranGenisligi + 20;
            // siyah kare ekranın dışına ekranGenisliginin + 20 fazlasından
            // başlasın.
            kirmiziucgenY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        t.kirmiziucgen.setX(kirmiziucgenX);
        t.kirmiziucgen.setY(kirmiziucgenY);
    }

    public void carpismaKontrol(){

        int sariDaireMerkezX = saridaireX + t.saridaire.getWidth()/2;
        int sariDaireMerkezY = saridaireY + t.saridaire.getHeight()/2;

        if (0 <= sariDaireMerkezX
                && sariDaireMerkezX <= anakarakterGenisligi
                && anakarakterY <= sariDaireMerkezY
                && sariDaireMerkezY <= anakarakterY + anakarakterYuksekligi){
            // çarpışma olacak.

            skor += 20;
            saridaireX = -10;
            // çarpıştığı anda ekrandan kaybolsun.
        }

        int kirmiziUcgenMerkezX = kirmiziucgenX + t.kirmiziucgen.getWidth()/2;
        int kirmiziUcgenMerkezY = kirmiziucgenY + t.kirmiziucgen.getHeight()/2;

        if (0 <= kirmiziUcgenMerkezX
                && kirmiziUcgenMerkezX <= anakarakterGenisligi
                && anakarakterY <= kirmiziUcgenMerkezY
                && kirmiziUcgenMerkezY <= anakarakterY + anakarakterYuksekligi){
            // çarpışma olacak.

            skor += 50;
            kirmiziucgenX = -10;
            // çarpıştığı anda ekrandan kaybolsun.
        }

        int siyahKareMerkezX = siyahkareX + t.siyahkare.getWidth()/2;
        int siyahKareMerkezY = siyahkareY + t.siyahkare.getHeight()/2;

        if (0 <= siyahKareMerkezX
                && siyahKareMerkezX <= anakarakterGenisligi
                && anakarakterY <= siyahKareMerkezY
                && siyahKareMerkezY <= anakarakterY + anakarakterYuksekligi){
            // çarpışma olacak.

            siyahkareX = -10;
            // çarpıştığı anda ekrandan kaybolsun.

            timer.cancel();
            timer = null;
            // timer durdur.

            Intent intent = new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class);
            intent.putExtra("skor",skor);
            startActivity(intent);
        }
        t.textViewSkor.setText(String.valueOf(skor));
    }
}