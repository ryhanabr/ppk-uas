package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    public static String type;
    public static Boolean before = true;
    private CardView kesehatan,kecantikan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        before = true;
        DokterActivity.before = false;

        kesehatan = (CardView) findViewById(R.id.kesehatan);
        kecantikan = (CardView) findViewById(R.id.kecantikan);

        kesehatan.setOnClickListener(this);
        kecantikan.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == kesehatan) {
            type = "Konsultasi Kesehatan";
        } else if(view == kecantikan) {
            type = "Perawatan Kecantikan";
        }
        Intent intent = new Intent(StatusActivity.this, tampilJadwal.class);
        startActivity(intent);
    }
}