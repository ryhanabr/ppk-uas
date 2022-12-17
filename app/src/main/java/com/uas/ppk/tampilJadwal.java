package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

public class tampilJadwal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DokterAdapter adapter;
    private ArrayList<Dokter> dokterArrayList;
    private String jadwal = DokterActivity.hari;
    private TextView judul_tv;
    private ProgressBar loading;
    private RequestQueue mQueue;

    private String URL = "http://192.168.100.227/ppk-api/public/dokter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jadwal);
        loading = (ProgressBar) findViewById(R.id.loading);
        judul_tv = (TextView) findViewById(R.id.judul);
        judul_tv.setText("Jadwal Hari "+jadwal);

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new DokterAdapter(dokterArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tampilJadwal.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    void addData(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("dokter");
                    for (int i = 0;i<jsonArray.length(); i++) {
                        Dokter a_dokter = new Dokter();
                        JSONObject dokter = jsonArray.getJSONObject(i);
                        if(dokter.getString("jadwal").equals(jadwal)) {
                            a_dokter.setId(dokter.getInt("id"));
                            a_dokter.setNamaLengkap(dokter.getString("nama_lengkap"));
                            a_dokter.setJk(dokter.getString("jk"));
                            a_dokter.setKategori(dokter.getString("kategori"));
                            a_dokter.setJadwal(dokter.getString("jadwal"));
                            dokterArrayList.add(a_dokter);
                        }
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

        mQueue.add(request);
    }
}