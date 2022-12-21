package com.uas.ppk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;

import java.util.ArrayList;

public class KesehatanAdapter extends RecyclerView.Adapter<KesehatanAdapter.KesehatanViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<KesehatanTicket> dataList;
    public static ArrayList<KesehatanTicket> temp = new ArrayList<>();

    public KesehatanAdapter(ArrayList<KesehatanTicket> dataList,RecyclerViewInterface recyclerViewInterface) {
        this.dataList = dataList;
        this.recyclerViewInterface = recyclerViewInterface;
        temp = dataList;
    }

    @Override
    public KesehatanAdapter.KesehatanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new KesehatanAdapter.KesehatanViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(KesehatanAdapter.KesehatanViewHolder holder, int position) {
        String namaPasien = dataList.get(position).getNamaPasien();
        String tanggal = dataList.get(position).getTanggal().toString();;
        String kategori = dataList.get(position).getKategori();

        holder.namaPasien_tv.setText(namaPasien);
        holder.kategori_tv.setText(kategori);
        holder.tanggal_tv.setText(tanggal);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public static class KesehatanViewHolder extends RecyclerView.ViewHolder{
        private TextView namaPasien_tv, kategori_tv, tanggal_tv;

        public KesehatanViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            namaPasien_tv = (TextView) itemView.findViewById(R.id.tv_1);
            kategori_tv = (TextView) itemView.findViewById(R.id.tv_2);
            tanggal_tv = (TextView) itemView.findViewById(R.id.tv_3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            tampilJadwal.code = 1;
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
