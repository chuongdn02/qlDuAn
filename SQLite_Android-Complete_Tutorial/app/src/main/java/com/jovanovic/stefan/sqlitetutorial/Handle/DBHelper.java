package com.jovanovic.stefan.sqlitetutorial.Handle;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qlvpgt.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, cccd TEXT, BacTK TEXT )");
        db.execSQL("CREATE TABLE nguoi_vi_pham (id INTEGER PRIMARY KEY AUTOINCREMENT, ho_ten TEXT, dia_chi TEXT, cccd TEXT, sdt TEXT, email TEXT, Sex TEXT, Nation TEXT)");
        db.execSQL("CREATE TABLE bien_ban (id INTEGER PRIMARY KEY AUTOINCREMENT, cccd TEXT, loai_vi_pham TEXT, loai_xe TEXT, bien_so TEXT, tien_phat TEXT, id_can_bo TEXT, vi_tri TEXT, FOREIGN KEY (cccd) REFERENCES nguoi_vi_pham(cccd), FOREIGN KEY (id_can_bo) REFERENCES account(cccd))");
        db.execSQL("CREATE TABLE khieu_nai (id INTEGER PRIMARY KEY AUTOINCREMENT, id_bien_ban INTEGER, ngay_khieu_nai TEXT, noi_khieu_nai TEXT, noi_dung_khieu_nai TEXT, FOREIGN KEY (id_bien_ban) REFERENCES bien_ban(id))");
        db.execSQL("CREATE TABLE thanh_toan (id INTEGER PRIMARY KEY AUTOINCREMENT, id_bien_ban INTEGER, ngay_thanh_toan TEXT, so_tien INTEGER, FOREIGN KEY (id_bien_ban) REFERENCES bien_ban(id))");
        db.execSQL("CREATE TABLE can_bo (id INTEGER PRIMARY KEY AUTOINCREMENT, ten_can_bo TEXT, chuc_vu TEXT, cccd TEXT)");
        db.execSQL("CREATE TABLE thong_ke (id INTEGER PRIMARY KEY AUTOINCREMENT, ngay_thong_ke TEXT, so_vi_pham INTEGER, so_tien_phat INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nguoi_vi_pham");
        db.execSQL("DROP TABLE IF EXISTS bien_ban");
        db.execSQL("DROP TABLE IF EXISTS khieu_nai");
        db.execSQL("DROP TABLE IF EXISTS thanh_toan");
        db.execSQL("DROP TABLE IF EXISTS can_bo");
        db.execSQL("DROP TABLE IF EXISTS thong_ke");
        db.execSQL("DROP TABLE IF EXISTS vi_pham");
        onCreate(db);
    }

    public boolean addAcc(Tai_khoan account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("cccd", account.getCccd());
        values.put("BacTK", account.getBacTK());

        long result = db.insert("account", null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public void insertBien_ban(String cccd, String loaiViPham, String loaiXe, String BienSo, String tienPhat, String idCanBo, String viTri) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cccd", cccd);
            values.put("loai_vi_pham", loaiViPham);
            values.put("loai_xe", loaiXe);
            values.put("bien_so", BienSo);
            values.put("tien_phat", tienPhat);
            values.put("id_can_bo", idCanBo);
            values.put("vi_tri", viTri);
            db.insert("bien_ban", null, values);
            db.close();
        } catch (Exception e) {
            Log.e("INSERT_ERROR", "Error inserting into bien_ban table: " + e.getMessage());
        }
    }



    @SuppressLint("Range")
    public Tai_khoan getAcc(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "username", "password", "cccd", "BacTK"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("account", columns, selection, selectionArgs, null, null, null);
        Tai_khoan acc = null;
        if (cursor != null && cursor.moveToFirst()) {
            acc = new Tai_khoan();
            acc.setId(cursor.getInt(cursor.getColumnIndex("id")));
            acc.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            acc.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            acc.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
            acc.setBacTK(cursor.getString(cursor.getColumnIndex("BacTK"))); // Cập nhật bậc tài khoản
            cursor.close();
        }
        db.close();
        return acc;
    }

    @SuppressLint("Range")
    public List<Bien_ban> getAllBienBan() {
        List<Bien_ban> bienBanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bien_ban", null);
        if (cursor.moveToFirst()) {
            do {
                Bien_ban bienBan = new Bien_ban();
                bienBan.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bienBan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
                bienBan.setLoaiViPham(cursor.getString(cursor.getColumnIndex("loai_vi_pham")));
                bienBan.setLoaiXe(cursor.getString(cursor.getColumnIndex("loai_xe")));
                bienBan.setBienso(cursor.getString(cursor.getColumnIndex("bien_so")));
                bienBan.setSoTienPhat(cursor.getString(cursor.getColumnIndex("tien_phat")));
                bienBan.setId_can_bo(cursor.getInt(cursor.getColumnIndex("id_can_bo")));
                bienBan.setVtri(cursor.getString(cursor.getColumnIndex("vi_tri")));
                bienBanList.add(bienBan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bienBanList;
    }

    @SuppressLint("Range")
    public List<Tai_khoan> getAllTaiKhoan() {
        List<Tai_khoan> tai_khoansList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account", null);
        if (cursor.moveToFirst()) {
            do {
                Tai_khoan tai_khoan = new Tai_khoan();
                tai_khoan.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tai_khoan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
                tai_khoan.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                tai_khoan.setBacTK(cursor.getString(cursor.getColumnIndex("BacTK")));

                tai_khoansList.add(tai_khoan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tai_khoansList;
    }

//    public void deleteTaiKhoan(String username) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("tai_khoan", "username = ?", new String[]{username});
//        db.close();
//    }

    @SuppressLint("Range")
    public Bien_ban getBienBanByBienSoAndLoaiXe(String bienSo, String loaiXe) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "bien_so=? AND loai_xe=?";
        String[] selectionArgs = {bienSo, loaiXe};
        Cursor cursor = db.query("bien_ban", null, selection, selectionArgs, null, null, null);
        Bien_ban bienBan = null;
        if (cursor.moveToFirst()) {
            bienBan = new Bien_ban();
            bienBan.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bienBan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
            bienBan.setLoaiViPham(cursor.getString(cursor.getColumnIndex("loai_vi_pham")));
            bienBan.setLoaiXe(cursor.getString(cursor.getColumnIndex("loai_xe")));
            bienBan.setBienso(cursor.getString(cursor.getColumnIndex("bien_so")));
            bienBan.setSoTienPhat(cursor.getString(cursor.getColumnIndex("tien_phat")));
            bienBan.setId_can_bo(cursor.getInt(cursor.getColumnIndex("id_can_bo")));
            bienBan.setVtri(cursor.getString(cursor.getColumnIndex("vi_tri")));
        }

        cursor.close();
        db.close();
        return bienBan;
    }


}












