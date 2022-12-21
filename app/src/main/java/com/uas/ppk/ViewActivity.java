package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;
    private Button hapus_b, update_b;
    private Boolean kec, kes = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);

        hapus_b = (Button) findViewById(R.id.batal);
        update_b = (Button) findViewById(R.id.update);

        hapus_b.setOnClickListener(this);
        update_b.setOnClickListener(this);

        if(tampilJadwal.kes) {
            Kesehatan();
        } else if (tampilJadwal.kec) {
            Kecantikan();
        } else if (tampilJadwal.dok) {
            DokterView();
        }
    }

    private void DokterView() {
        Dokter dokter = tampilJadwal.dokter;
        tv_1.setText(dokter.getNamaLengkap());
        tv_2.setText("(Jenis Kelamin) " +dokter.getJk());
        tv_3.setText("(Kategori) "+dokter.getKategori());
        tv_6.setText("(Jadwal Praktik) "+dokter.getJadwal());
        tv_5.setVisibility(View.GONE);
        tv_4.setVisibility(View.GONE);
        hapus_b.setVisibility(View.GONE);
        update_b.setVisibility(View.GONE);
    }

    private void Kecantikan() {
        kec = true;
        kes = false;
        KecantikanTicket ticket = tampilJadwal.ticket_kec;
        tv_1.setText(ticket.getNamaPasien());
        tv_2.setText("(Umur) "+ticket.getUmur());
        tv_3.setText("(Jenis Kelamin) " +ticket.getJk());
        tv_4.setText("(Catatan) "+ticket.getCatatan());
        tv_6.setText("(Tanggal Perawatan) "+ ticket.getTanggal());
        tv_5.setVisibility(View.GONE);
    }

    private void Kesehatan() {
        kec = false;
        kec = true;
        KesehatanTicket ticket = tampilJadwal.ticket_kes;
        tv_1.setText(ticket.getNamaPasien());
        tv_2.setText("(Umur) "+ticket.getUmur());
        tv_3.setText("(Jenis Kelamin) " +ticket.getJk());
        tv_4.setText("(Kategori) "+ ticket.getKategori());
        tv_5.setText("(Keluhan) "+ticket.getKeluhan());
        tv_6.setText("(Tanggal Konsultasi) "+ ticket.getTanggal());
    }

    @Override
    public void onClick(View view) {

    }


}