package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username, et_password;
    private Button btn_login;
    private TextView tv_registrasi;
    private ProgressBar loading;
    private String URL_LOGIN = "http://192.168.100.227/ppk-api/public/login";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_registrasi = (TextView) findViewById(R.id.registrasi);
        loading = (ProgressBar) findViewById(R.id.progress_login);
        user = new User();

        tv_registrasi.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == tv_registrasi) {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
        else if(view == btn_login) {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if(!username.isEmpty() && !password.isEmpty()) {
                Login();
            } else {
                et_username.setError("Mohon masukkan username");
                et_password.setError("Mohon masukkan password");
            }
        }
    }

    private void Login() {
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        final String username = this.et_username.getText().toString().trim();
        final String password = this.et_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("1")) {
                                Toast.makeText(LoginActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                                user.setId(jsonObject.getInt("userId"));
                                user.setNama(jsonObject.getString("nama_lengkap"));
                                user.setUsername(jsonObject.getString("username"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setAlamat(jsonObject.getString("alamat"));
                                user.setTelp(jsonObject.getString("telp"));
                                if(jsonObject.getString("admin").equals("0")) {
                                    user.setAdmin(false);
                                } else {
                                    user.setAdmin(true);
                                }

                                Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                                startActivity(intent);
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("pass",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}