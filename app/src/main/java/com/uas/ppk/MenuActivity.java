package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView kesehatan, kecantikan, dokter, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        kesehatan = (CardView) findViewById(R.id.kesehatan);
        kecantikan = (CardView) findViewById(R.id.kecantikan);
        dokter = (CardView) findViewById(R.id.dokter);
        status  = (CardView) findViewById(R.id.status);

        kesehatan.setOnClickListener(this);
        kecantikan.setOnClickListener(this);
        dokter.setOnClickListener(this);
        status.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == kesehatan) {
            Intent intent = new Intent(MenuActivity.this, KesehatanFormActivity.class);
            startActivity(intent);
        } else if(view == kecantikan) {
            Intent intent = new Intent(MenuActivity.this, KecantikanFormActivity.class);
            startActivity(intent);
        } else if(view == dokter) {
            Intent intent = new Intent(MenuActivity.this, DokterActivity.class);
            startActivity(intent);
        } else if(view == status) {
            Intent intent = new Intent(MenuActivity.this, StatusActivity.class);
            startActivity(intent);
        }
    }
}