package com.uas.ppk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KecantikanAdapter extends RecyclerView.Adapter<KecantikanAdapter.KecantikanViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    public static ArrayList<KecantikanTicket> temp;
    private ArrayList<KecantikanTicket> dataList;

    public KecantikanAdapter(ArrayList<KecantikanTicket> dataList, RecyclerViewInterface recyclerViewInterface) {
        this.dataList = dataList;
        this.recyclerViewInterface = recyclerViewInterface;
        temp = dataList;
    }

    @Override
    public KecantikanAdapter.KecantikanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new KecantikanAdapter.KecantikanViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(KecantikanAdapter.KecantikanViewHolder holder, int position) {
        String namaPasien = dataList.get(position).getNamaPasien();
        String tanggal = dataList.get(position).getTanggal().toString();

        holder.namaPasien_tv.setText(namaPasien);
        holder.kecantikan_tv.setText("Perawatan Kecantikan");
        holder.tanggal_tv.setText(tanggal);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public static class KecantikanViewHolder extends RecyclerView.ViewHolder{
        private TextView namaPasien_tv, kecantikan_tv, tanggal_tv;

        public KecantikanViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            namaPasien_tv = (TextView) itemView.findViewById(R.id.tv_1);
            kecantikan_tv = (TextView) itemView.findViewById(R.id.tv_2);
            tanggal_tv = (TextView) itemView.findViewById(R.id.tv_3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            tampilJadwal.code = 2;
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }

}
