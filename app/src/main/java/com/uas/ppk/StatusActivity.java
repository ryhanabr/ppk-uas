package com.uas.ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TiketAdapter adapter;
    private ArrayList<Tiket> tiketArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new TiketAdapter(tiketArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StatusActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void addData() {

    }


}