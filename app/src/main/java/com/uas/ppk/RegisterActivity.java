package com.uas.ppk;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_nama, et_username, et_email, et_alamat, et_telp, et_password, et_konfirmasi;
    private CheckBox cb_aturan;
    private Button btn_register;
    private TextView tv_login;
    private ProgressBar loading;

    private String URL_REGIST = "http://192.168.100.227/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_nama = (EditText) findViewById(R.id.nama);
        et_username = (EditText) findViewById(R.id.username);
        et_email = (EditText) findViewById(R.id.email);
        et_alamat = (EditText) findViewById(R.id.alamat);
        et_telp = (EditText) findViewById(R.id.telp);
        et_password = (EditText) findViewById(R.id.password);
        et_konfirmasi = (EditText) findViewById(R.id.konfirmasi);
        cb_aturan = (CheckBox) findViewById(R.id.cb_aturan);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_login = (TextView) findViewById(R.id.tv_login);
        loading = (ProgressBar) findViewById(R.id.progress_register);

        tv_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == tv_login) {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        } else if(view == btn_register) {
            String nama = et_nama.getText().toString().trim();
            String username = et_username.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String alamat = et_alamat.getText().toString().trim();
            String telp = et_telp.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            String konfirmasi = et_konfirmasi.getText().toString().trim();

            if(!nama.isEmpty() && !username.isEmpty() && !email.isEmpty() && !alamat.isEmpty() && !telp.isEmpty() && !password.isEmpty() && !konfirmasi.isEmpty()) {
                if(cb_aturan.isChecked()) {
                    if(password.equals(konfirmasi)) Register();
                    else et_konfirmasi.setError("Konfirmasi password tidak sesuai!");
                } else {
                    cb_aturan.setError("Silakan centang checkbox berikut!");
                }
            } else {
                et_nama.setError("Mohon masukkan nama");
                et_username.setError("Mohon masukkan username");
                et_email.setError("Mohon masukkan email");
                et_alamat.setError("Mohon masukkan alamat");
                et_telp.setError("Mohon masukkan nomor telepon");
                et_password.setError("Mohon masukkan password");
                et_konfirmasi.setError("Mohon masukkan konfirmasi password");
            }
        }
    }

    private void Register() {
        loading.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.GONE);

        final String nama = this.et_nama.getText().toString().trim();
        final String username = this.et_username.getText().toString().trim();
        final String email = this.et_email.getText().toString().trim();
        final String alamat = this.et_alamat.getText().toString().trim();
        final String telp = this.et_telp.getText().toString().trim();
        final String password = this.et_password.getText().toString().trim();
        final String konfirmasi = this.et_konfirmasi.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            if (status.equals("1")) {
                                Toast.makeText(RegisterActivity.this, "Registrasi berhasi!", Toast.LENGTH_SHORT).show();
                                Intent success = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(success);
                                loading.setVisibility(View.GONE);
                                btn_register.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_register.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_register.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nama_lengkap",nama);
                params.put("username",username);
                params.put("email",email);
                params.put("alamat",alamat);
                params.put("telp",telp);
                params.put("pass",password);
                params.put("konf_pass",konfirmasi);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}