package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class KesehatanFormActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;

    private EditText namaPasien_et, umur_et, tanggal_et, keluhan_et;
    private Button otomatis_b, simpan_b;
    private RadioButton laki_laki_rb, perempuan_rb, tht_rb, mata_rb, gigi_rb, umum_rb;
    private RadioGroup jk_rg;
    private ProgressBar loading;

    private User user;
    private String URL = "http://192.168.43.94/ppk-api/public/kesehatan";
    public static int lastId;
    private String jk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesehatan_form);

        namaPasien_et = (EditText) findViewById(R.id.namaPasien);
        umur_et = (EditText) findViewById(R.id.umur);
        tanggal_et = (EditText) findViewById(R.id.tanggal);
        keluhan_et = (EditText) findViewById(R.id.keluhan);
        otomatis_b = (Button) findViewById(R.id.otomatis);
        simpan_b = (Button) findViewById(R.id.simpan);
        laki_laki_rb = (RadioButton) findViewById(R.id.laki_laki);
        perempuan_rb = (RadioButton) findViewById(R.id.perempuan);
        tht_rb = (RadioButton) findViewById(R.id.tht);
        mata_rb = (RadioButton) findViewById(R.id.mata);
        gigi_rb = (RadioButton) findViewById(R.id.gigi);
        umum_rb = (RadioButton) findViewById(R.id.umum);
        user = LoginActivity.user;
        jk_rg = (RadioGroup) findViewById(R.id.jk);
        loading = (ProgressBar) findViewById(R.id.loading);

        otomatis_b.setOnClickListener(this);
        simpan_b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == otomatis_b) {
            namaPasien_et.setText(user.getNama());
        } else if(view == simpan_b) {
            String username = user.getUsername();
            String namaPasien = namaPasien_et.getText().toString();
            String umur = umur_et.getText().toString();
            String tanggal = tanggal_et.getText().toString();
            String keluhan = keluhan_et.getText().toString();
            String kategori = "null";
            if(tht_rb.isChecked()) {
                kategori = "THT";
            } else if(mata_rb.isChecked()) {
                kategori = "Mata";
            } else if(gigi_rb.isChecked()) {
                kategori = "Gigi";
            } else if(umum_rb.isChecked()) {
                kategori = "Umum";
            }
            jk = "null";
            if(laki_laki_rb.isChecked()) {
                jk = "L";
            } else if(perempuan_rb.isChecked()) {
                jk = "P";
            }

            if(!namaPasien.isEmpty() && !umur.isEmpty() && !kategori.equals("null") && !jk.equals("null") && !tanggal.isEmpty() && !keluhan.isEmpty()) {
                SimpanKesehatan();
            } else {
                namaPasien_et.setError("Mohon masukkan nama pasien");
                umur_et.setError("Mohon masukkan umur pasien");
                autoCompleteTextView.setError("Mohon pilih kategori");
                laki_laki_rb.setError("Mohon pilih jenis kelamin");
                perempuan_rb.setError("Mohon pilih jenis kelamin");
                tanggal_et.setError("Mohon masukkan tanggal konsulatasi");
                keluhan_et.setError("Mohon masukkan keluhan Anda");
            }

        }
    }

    private void SimpanKesehatan() {
        loading.setVisibility(View.VISIBLE);
        simpan_b.setVisibility(View.GONE);

        final String namaPasien = this.namaPasien_et.getText().toString().trim();
        final String umur = this.umur_et.getText().toString().trim();
        String kat = "";
        if(tht_rb.isChecked()) {
            kat = "THT";
        } else if(mata_rb.isChecked()) {
            kat = "Mata";
        } else if(gigi_rb.isChecked()) {
            kat = "Gigi";
        } else if(umum_rb.isChecked()) {
            kat = "Umum";
        }
        if(laki_laki_rb.isChecked()) {
            jk = "L";
        } else if(perempuan_rb.isChecked()) {
            jk = "P";
        }
        final String jenis_kelamin = jk.trim();
        final String kategori = kat.trim();
        final String tanggal = this.tanggal_et.getText().toString().trim();
        final String keluhan = this.keluhan_et.getText().toString().trim();
        final String username = this.user.getUsername().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("OK")) {
                                Toast.makeText(KesehatanFormActivity.this, "Tiket berhasil dibuat!", Toast.LENGTH_SHORT).show();
                                lastId = jsonObject.getInt("id");
                                Intent intent = new Intent(KesehatanFormActivity.this,MenuActivity.class);
                                startActivity(intent);
                                loading.setVisibility(View.GONE);
                                simpan_b.setVisibility(View.VISIBLE);
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(KesehatanFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                simpan_b.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KesehatanFormActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            simpan_b.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KesehatanFormActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        simpan_b.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nama_pasien",namaPasien);
                params.put("jk",jenis_kelamin);
                params.put("umur",umur);
                params.put("kategori",kategori);
                params.put("tanggal",tanggal);
                params.put("keluhan",keluhan);
                params.put("username", username);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}