package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class tampilJadwal extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recyclerView;

    private ArrayList<Dokter> dokterArrayList,temp;
    private ProgressBar loading;
    private TextView judul,warn;

    private String URL_DOKTER = "http://192.168.43.94/ppk-api/public/dokter";
    private String URL_KESEHATAN = "http://192.168.43.94/ppk-api/public/kesehatan";
    private String URL_KECANTIKAN = "http://192.168.43.94/ppk-api/public/kecantikan";

    public static KesehatanTicket ticket_kes;
    public static KecantikanTicket ticket_kec;
    public static Dokter dokter;
    public static int code=0;
    public static Boolean kes, kec, dok = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jadwal);
        loading = (ProgressBar) findViewById(R.id.loading);
        judul = (TextView) findViewById(R.id.judul);
        warn = (TextView) findViewById(R.id.warn);
        if(DokterActivity.before) {
            jadwalDokter();
        } else {
            tiketAktif();
        }
    }

    private void tiketAktif() {
        judul.setText("Tiket "+ StatusActivity.type);
        warn.setText("Tidak ada tiket aktif. Silakan daftar terlebih dahulu");
        if(StatusActivity.type.equals("Konsultasi Kesehatan")) {
            kesehatan();
        } else if(StatusActivity.type.equals("Perawatan Kecantikan")) {
            kecantikan();
        }
    }

    private void kesehatan() {
        ArrayList<KesehatanTicket> kesehatanTicketArrayList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, URL_KESEHATAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("ticket");
                    for (int i = 0;i<jsonArray.length(); i++) {
                        KesehatanTicket ticket = new KesehatanTicket();
                        JSONObject json = jsonArray.getJSONObject(i);
                        if(json.getString("status").equals("Aktif")) {
                            ticket.setId(json.getInt("id"));
                            ticket.setNamaPasien(json.getString("nama_pasien"));
                            ticket.setJk(json.getString("jk"));
                            ticket.setKategori(json.getString("kategori"));
                            ticket.setStatus(json.getString("status"));
                            ticket.setTanggal(json.getString("tanggal"));
                            ticket.setUmur(json.getInt("umur"));
                            ticket.setKeluhan(json.getString("keluhan"));
                            kesehatanTicketArrayList.add(ticket);
                        }
                    }
                    if(!kesehatanTicketArrayList.isEmpty()) {
                        recyclerView = (RecyclerView) findViewById(R.id.list);
                        KesehatanAdapter adapter = new KesehatanAdapter(kesehatanTicketArrayList, tampilJadwal.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tampilJadwal.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        warn.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(tampilJadwal.this, e.toString(), Toast.LENGTH_SHORT);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(tampilJadwal.this, error.toString(), Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        loading.setVisibility(View.GONE);

    }

    private void kecantikan() {
        ArrayList<KecantikanTicket> kecantikanTicketArrayList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, URL_KECANTIKAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("ticket");
                    for (int i = 0;i<jsonArray.length(); i++) {
                        KecantikanTicket ticket = new KecantikanTicket();
                        JSONObject json = jsonArray.getJSONObject(i);
                        if(json.getString("status").equals("Aktif")) {
                            ticket.setId(json.getInt("id"));
                            ticket.setNamaPasien(json.getString("nama_pasien"));
                            ticket.setJk(json.getString("jk"));
                            ticket.setStatus(json.getString("status"));
                            ticket.setTanggal(json.getString("tanggal"));
                            ticket.setUmur(json.getInt("umur"));
                            ticket.setCatatan(json.getString("catatan"));
                            kecantikanTicketArrayList.add(ticket);
                        }
                    }
                    if(!kecantikanTicketArrayList.isEmpty()) {
                        recyclerView = (RecyclerView) findViewById(R.id.list);
                        KecantikanAdapter adapter = new KecantikanAdapter(kecantikanTicketArrayList,tampilJadwal.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tampilJadwal.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        warn.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(tampilJadwal.this, e.toString(), Toast.LENGTH_SHORT);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(tampilJadwal.this, error.toString(), Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        loading.setVisibility(View.GONE);
    }

    void jadwalDokter() {
        judul.setText("Jadwal Hari "+DokterActivity.hari);
        loading.setVisibility(View.VISIBLE);
        dokterArrayList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, URL_DOKTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("dokter");
                    for (int i = 0;i<jsonArray.length(); i++) {
                        Dokter dokter = new Dokter();
                        JSONObject json = jsonArray.getJSONObject(i);
                        Log.e("Test bool", json.getString("jadwal") + " vs "+DokterActivity.hari+" = "+json.getString("jadwal").equals(DokterActivity.hari));
                        if(json.getString("jadwal").equals(DokterActivity.hari)) {
                            dokter.setId(json.getInt("id"));
                            dokter.setNamaLengkap(json.getString("nama_lengkap"));
                            dokter.setJk(json.getString("jk"));
                            dokter.setKategori(json.getString("kategori"));
                            dokter.setJadwal(json.getString("jadwal"));
                            dokterArrayList.add(dokter);
                        }
                    }
                    if(!dokterArrayList.isEmpty()) {
                        recyclerView = (RecyclerView) findViewById(R.id.list);
                        DokterAdapter adapter = new DokterAdapter(dokterArrayList, tampilJadwal.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tampilJadwal.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        warn.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(tampilJadwal.this, e.toString(), Toast.LENGTH_SHORT);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(tampilJadwal.this, error.toString(), Toast.LENGTH_SHORT);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {
        if(code==1) {
            ticket_kes = KesehatanAdapter.temp.get(position);
            kes = true;kec = false;dok = false;
        } else if(code==2) {
            ticket_kec = KecantikanAdapter.temp.get(position);
            kes = false;kec = true;dok = false;
        } else if(code==3) {
            dokter = DokterAdapter.temp.get(position);
            kes = false;kec = false;dok = true;
        }
        Intent intent = new Intent(tampilJadwal.this, ViewActivity.class);
        startActivity(intent);
    }
}