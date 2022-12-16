package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

public class KesehatanFormActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesehatan_form);

        textInputLayout = findViewById(R.id.kategori);
        autoCompleteTextView = findViewById(R.id.item);

        String [] items = {"THT", "Mata", "Gigi", "Umum"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(KesehatanFormActivity.this, R.layout.dropdown_item, items);
        autoCompleteTextView.setAdapter(itemAdapter);
    }
}