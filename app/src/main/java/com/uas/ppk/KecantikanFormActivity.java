package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class KecantikanFormActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namaPasien_et, umur_et, tanggal_et, catatan_et;
    private Button otomatis_b, simpan_b;
    private RadioButton laki_laki_rb, perempuan_rb;
    private ProgressBar loading;

    private User user;
    private String URL = "http://192.168.43.94/ppk-api/public/kecantikan";
    public static int lastId;
    private String jk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kecantikan_form);

        namaPasien_et = (EditText) findViewById(R.id.namaPasien);
        umur_et = (EditText) findViewById(R.id.umur);
        tanggal_et = (EditText) findViewById(R.id.tanggal);
        catatan_et = (EditText) findViewById(R.id.catatan);
        otomatis_b = (Button) findViewById(R.id.otomatis);
        simpan_b = (Button) findViewById(R.id.simpan);
        laki_laki_rb = (RadioButton) findViewById(R.id.laki_laki);
        perempuan_rb = (RadioButton) findViewById(R.id.perempuan);
        user = LoginActivity.user;
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
            String keluhan = catatan_et.getText().toString();
            jk = "null";
            if(laki_laki_rb.isChecked()) {
                jk = "L";
            } else if(perempuan_rb.isChecked()) {
                jk = "P";
            }

            if(!namaPasien.isEmpty() && !umur.isEmpty()  && !jk.equals("null") && !tanggal.isEmpty() && !keluhan.isEmpty()) {
                SimpanKecantikan();
            } else {
                namaPasien_et.setError("Mohon masukkan nama pasien");
                umur_et.setError("Mohon masukkan umur pasien");
                laki_laki_rb.setError("Mohon pilih jenis kelamin");
                perempuan_rb.setError("Mohon pilih jenis kelamin");
                tanggal_et.setError("Mohon masukkan tanggal konsulatasi");
                catatan_et.setError("Mohon masukkan keluhan Anda");
            }

        }
    }

    private void SimpanKecantikan() {
        loading.setVisibility(View.VISIBLE);
        simpan_b.setVisibility(View.GONE);

        final String namaPasien = this.namaPasien_et.getText().toString().trim();
        final String umur = this.umur_et.getText().toString().trim();
        if(laki_laki_rb.isChecked()) {
            jk = "L";
        } else if(perempuan_rb.isChecked()) {
            jk = "P";
        }
        final String jenis_kelamin = jk.trim();
        final String tanggal = this.tanggal_et.getText().toString().trim();
        final String catatan = this.catatan_et.getText().toString().trim();
        final String username = this.user.getUsername().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("OK")) {
                                Toast.makeText(KecantikanFormActivity.this, "Tiket berhasil dibuat!", Toast.LENGTH_SHORT).show();
                                lastId = jsonObject.getInt("id");
                                Intent intent = new Intent(KecantikanFormActivity.this,MenuActivity.class);
                                startActivity(intent);
                                loading.setVisibility(View.GONE);
                                simpan_b.setVisibility(View.VISIBLE);
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(KecantikanFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                simpan_b.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KecantikanFormActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            simpan_b.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KecantikanFormActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("tanggal",tanggal);
                params.put("catatan",catatan);
                params.put("username", username);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}