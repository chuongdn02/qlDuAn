package com.jovanovic.stefan.sqlitetutorial.Handle;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;
import com.jovanovic.stefan.sqlitetutorial.R;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.TaiKhoanViewHolder> implements Filterable {

    private List<Tai_khoan> mDataList;
    private List<Tai_khoan> dataListFiltered;

    public TaiKhoanAdapter(List<Tai_khoan> dataList) {
        mDataList = dataList;
        dataListFiltered = dataList;
    }

    @NonNull
    @Override
    public TaiKhoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tk, parent, false);
        return new TaiKhoanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiKhoanViewHolder holder, int position) {
        Tai_khoan tai_khoan = dataListFiltered.get(position);
        holder.tvCCCD.setText(tai_khoan.getCccd());
        holder.tv_username.setText(tai_khoan.getUsername());
        holder.tvBacTK.setText(tai_khoan.getBacTK());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of this item in the RecyclerView
                int adapterPosition = holder.getAdapterPosition();

                // Get the corresponding object in the original data source
                Tai_khoan taiKhoanToDelete = dataListFiltered.get(adapterPosition);
                int positionInDataList = mDataList.indexOf(taiKhoanToDelete);

                // Create a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xác nhận xoá");
                builder.setMessage("Bạn có muốn xoá tài khoản này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the data source
                        dataListFiltered.remove(positionInDataList);

                        // Delete the item from the database
                        DBHelper dbHelper = new DBHelper(v.getContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("account", "id = ?", new String[]{String.valueOf(taiKhoanToDelete.getId())});
                        db.close();

                        // Notify the adapter that an item has been removed
                        notifyItemRemoved(adapterPosition);
                    }
                });
                builder.setNegativeButton("Không", null);

                // Show the confirmation dialog
                AlertDialog dialog = builder.create();
                dialog.show();
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
                String query = charSequence.toString();

                List<Tai_khoan> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(mDataList);
                } else {
                    for (Tai_khoan data : mDataList) {
                        if (data.getBacTK().equals(query) || data.getUsername().contains(query)) {
                            filteredList.add(data);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (List<Tai_khoan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TaiKhoanViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCCCD, tv_username, tvBacTK;
        private Button btnDelete;

        public TaiKhoanViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvCCCD = itemView.findViewById(R.id.tv_cccd);
            tv_username = itemView.findViewById(R.id.tv_username);
            tvBacTK = itemView.findViewById(R.id.tv_bac_tk);
        }
    }
}
