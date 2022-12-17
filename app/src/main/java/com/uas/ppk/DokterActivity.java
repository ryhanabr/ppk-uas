package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DokterActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView senin, selasa, rabu, kamis, jumat, sabtu;
    public static String hari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);

        senin = (CardView) findViewById(R.id.senin);
        selasa = (CardView) findViewById(R.id.selasa);
        rabu = (CardView) findViewById(R.id.rabu);
        kamis = (CardView) findViewById(R.id.kamis);
        jumat = (CardView) findViewById(R.id.jumat);
        sabtu = (CardView) findViewById(R.id.sabtu);

        senin.setOnClickListener(this);
        selasa.setOnClickListener(this);
        rabu.setOnClickListener(this);
        kamis.setOnClickListener(this);
        jumat.setOnClickListener(this);
        sabtu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == senin) {
            hari = "Senin";
        } else if(view == selasa) {
            hari = "Selasa";
        } else if(view == rabu) {
            hari = "Rabu";
        } else if(view == kamis) {
            hari = "Kamis";
        } else if(view == jumat) {
            hari = "Jumat";
        } else if(view == sabtu) {
            hari = "Sabtu;";
        }
        Intent intent = new Intent(DokterActivity.this, tampilJadwal.class);
        startActivity(intent);
    }
}