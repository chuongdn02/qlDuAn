package com.jovanovic.stefan.sqlitetutorial.Handle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.R;

import java.util.List;

public class BienBanAdapter extends RecyclerView.Adapter<BienBanAdapter.BienBanViewHolder> {

    private List<Bien_ban> mDataList;

    public BienBanAdapter(List<Bien_ban> dataList) {
        mDataList = dataList;
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
        Bien_ban bienban = mDataList.get(position);

        holder.tvCCCD.setText("CCCD: " + bienban.getCccd());
        holder.tvLoaiViPham.setText("Loại vi phạm: " + bienban.getLoaiViPham());
        holder.tvLoaiXe.setText("Loại xe: " + bienban.getLoaiXe());
        holder.tvBienSo.setText("Biển số: " + bienban.getBienso());
        holder.tvSoTienPhat.setText("Số tiền phạt: " + bienban.getSoTienPhat());
        holder.tvIDCanBo.setText("ID cán bộ: " + bienban.getId_can_bo());
        holder.tvViTri.setText("Vị trí: " + bienban.getVtri());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class BienBanViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCCCD, tvLoaiViPham, tvLoaiXe, tvBienSo, tvSoTienPhat, tvIDCanBo, tvViTri;

        public BienBanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCCCD = itemView.findViewById(R.id.tv_cccd);
            tvLoaiViPham = itemView.findViewById(R.id.tv_loaivipham);
            tvLoaiXe = itemView.findViewById(R.id.tv_loaixe);
            tvBienSo = itemView.findViewById(R.id.tv_bienso);
            tvSoTienPhat = itemView.findViewById(R.id.tv_sotienphat);
            tvIDCanBo = itemView.findViewById(R.id.tv_idcanbo);
            tvViTri = itemView.findViewById(R.id.tv_vitri);
        }
    }
}
