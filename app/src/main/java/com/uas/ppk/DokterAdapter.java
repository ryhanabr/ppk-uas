package com.uas.ppk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.DokterViewHolder> {

    private ArrayList<Dokter> dataList;

    public DokterAdapter(ArrayList<Dokter> dataList) {
        this.dataList = dataList;
    }

    @Override
    public DokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new DokterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DokterViewHolder holder, int position) {
        String namaLengkap = dataList.get(position).getNamaLengkap();
        int id = dataList.get(position).getId();
        String jk = dataList.get(position).getJk();
        String kategori = dataList.get(position).getKategori();
        String jadwal = dataList.get(position).getJadwal();

        holder.namaLengkap_tv.setText(namaLengkap);
        holder.kategori_tv.setText(kategori);
        holder.jadwal_tv.setText(jadwal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),namaLengkap,Toast.LENGTH_SHORT) .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DokterViewHolder extends RecyclerView.ViewHolder{
        private TextView namaLengkap_tv, kategori_tv, jadwal_tv;

        public DokterViewHolder(View itemView) {
            super(itemView);
            namaLengkap_tv = (TextView) itemView.findViewById(R.id.tv_1);
            kategori_tv = (TextView) itemView.findViewById(R.id.tv_3);
            jadwal_tv = (TextView) itemView.findViewById(R.id.tv_2);
        }
    }
}
