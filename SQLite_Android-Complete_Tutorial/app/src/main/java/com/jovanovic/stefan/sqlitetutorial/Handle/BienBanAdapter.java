package com.jovanovic.stefan.sqlitetutorial.Handle;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.R;

import java.util.ArrayList;
import java.util.List;

public class BienBanAdapter extends RecyclerView.Adapter<BienBanAdapter.BienBanViewHolder> implements Filterable {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private List<Bien_ban> mDataList;
    private List<Bien_ban> dataListFiltered;
    private OnItemClickListener listener;
    private OnItemLongClickListener onItemLongClickListener;

    public BienBanAdapter(List<Bien_ban> dataList, OnItemClickListener listener) {
        this.mDataList = dataList;
        this.dataListFiltered = dataList;
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BienBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bienban, parent, false);
        return new BienBanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BienBanViewHolder holder, int position) {
        Bien_ban bien_ban = dataListFiltered.get(position);
        holder.tvNameVP.setText(bien_ban.getLoaiViPham());
        holder.tvLoaiXe.setText("Loại xe: " + bien_ban.getLoaiXe());
        holder.tvTienPhat.setText("Tiền phạt: " + bien_ban.getSoTienPhat());
        holder.tvViTri.setText(bien_ban.getVtri());
        holder.tvSoQuyetDinh.setText(bien_ban.getSoQuyetDinh());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();

                List<Bien_ban> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(mDataList);
                } else {
                    for (Bien_ban bienBan : mDataList) {
                        if (bienBan.getVtri().toLowerCase().contains(query)
                                || bienBan.getLoaiXe().toLowerCase().equals(query)
                                || bienBan.getSoQuyetDinh().contains(query)) {
                            filteredList.add(bienBan);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (List<Bien_ban>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < mDataList.size()) {
            mDataList.remove(position);
            dataListFiltered.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateData(List<Bien_ban> newDataList) {
        mDataList = newDataList;
        dataListFiltered = newDataList;  // Update filtered data as well
        notifyDataSetChanged();
    }

    public static class BienBanViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameVP, tvLoaiXe, tvTienPhat, tvViTri, tvSoQuyetDinh;

        public BienBanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameVP = itemView.findViewById(R.id.tv_name_vp);
            tvLoaiXe = itemView.findViewById(R.id.loai_xe);
            tvTienPhat = itemView.findViewById(R.id.tien_phat);
            tvViTri = itemView.findViewById(R.id.viTri);
            tvSoQuyetDinh = itemView.findViewById(R.id.soQuyetDinh);
        }
    }
}
