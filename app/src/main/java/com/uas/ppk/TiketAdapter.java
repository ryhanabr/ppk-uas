package com.uas.ppk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.TiketViewHolder> {
    private ArrayList<Tiket> dataList;

    public TiketAdapter(ArrayList<Tiket> dataList) {
        this.dataList = dataList;
    }

    @Override
    public TiketAdapter.TiketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new TiketAdapter.TiketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TiketAdapter.TiketViewHolder holder, int position) {
        String nama = dataList.get(position).getNama();
        int id = dataList.get(position).getId();
        String jk = dataList.get(position).getJk();
        String kategori = dataList.get(position).getKategori();
        Date tanggal = dataList.get(position).getTanggal();

        holder.nama.setText(nama);
        holder.kategori.setText(kategori);
        holder.tanggal.setText(tanggal.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),nama,Toast.LENGTH_SHORT) .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class TiketViewHolder extends RecyclerView.ViewHolder{
        private TextView nama, kategori, tanggal;

        public TiketViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.tv_1);
            kategori = (TextView) itemView.findViewById(R.id.tv_2);
            tanggal = (TextView) itemView.findViewById(R.id.tv_3);
        }
    }
}
