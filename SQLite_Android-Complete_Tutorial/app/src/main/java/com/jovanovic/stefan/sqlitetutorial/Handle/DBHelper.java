package com.jovanovic.stefan.sqlitetutorial.Handle;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("CREATE TABLE account (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, cccd TEXT, BacTK TEXT, ho_ten TEXT, dia_chi TEXT, sdt TEXT, email TEXT, gioi_tinh TEXT, quoc_gia TEXT, chuc_vu TEXT)");
        db.execSQL("CREATE TABLE bien_ban (id INTEGER PRIMARY KEY AUTOINCREMENT, so_quyet_dinh TEXT, cccd TEXT, loai_vi_pham TEXT, loai_xe TEXT, bien_so TEXT, tien_phat TEXT, id_can_bo TEXT, vi_tri TEXT, FOREIGN KEY (id_can_bo) REFERENCES account(cccd))");
        db.execSQL("CREATE TABLE khieu_nai (id INTEGER PRIMARY KEY AUTOINCREMENT, id_bien_ban INTEGER, ngay_khieu_nai TEXT, noi_khieu_nai TEXT, noi_dung_khieu_nai TEXT, FOREIGN KEY (id_bien_ban) REFERENCES bien_ban(id))");
        db.execSQL("CREATE TABLE thanh_toan (id INTEGER PRIMARY KEY AUTOINCREMENT, id_bien_ban INTEGER, ngay_thanh_toan TEXT, so_tien INTEGER, FOREIGN KEY (id_bien_ban) REFERENCES bien_ban(id))");



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

//    public void insertBien_ban(String soQuyetDinh,String cccd, String loaiViPham, String loaiXe, String BienSo, String tienPhat, String idCanBo, String viTri) {
//            SQLiteDatabase db = getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put("so_quyet_dinh", soQuyetDinh);
//            values.put("cccd", cccd);
//            values.put("loai_vi_pham", loaiViPham);
//            values.put("loai_xe", loaiXe);
//            values.put("bien_so", BienSo);
//            values.put("tien_phat", tienPhat);
//            values.put("id_can_bo", idCanBo);
//            values.put("vi_tri", viTri);
//            long result = db.insert("bien_ban", null, values);
//
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        }
    public boolean insertBien_ban(Bien_ban bien_ban) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("so_quyet_dinh", bien_ban.getSoQuyetDinh());
        values.put("cccd", bien_ban.getCccd());
        values.put("loai_vi_pham", bien_ban.getLoaiViPham());
        values.put("loai_xe", bien_ban.getLoaiXe());
        values.put("bien_so", bien_ban.getBienso());
        values.put("tien_phat", bien_ban.getSoTienPhat());
        values.put("id_can_bo", bien_ban.getId_can_bo());
        values.put("vi_tri", bien_ban.getVtri());

        long result = db.insert("bien_ban", null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteBienBan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bien_ban", "id=?", new String[]{String.valueOf(id)});
        db.close();
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
                bienBan.setSoQuyetDinh(cursor.getString(cursor.getColumnIndex("so_quyet_dinh")));
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

    public List<Bien_ban> getBienBanByLoaiXe(String loaiXe) {
        List<Bien_ban> bienBanList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Execute the query to fetch the violations by loaiXe
        Cursor cursor = db.query("bien_ban", null, "loai_xe = ?", new String[]{loaiXe}, null, null, null);

        // Iterate through the cursor and populate the bienBanList
        while (cursor.moveToNext()) {
            Bien_ban bienBan = new Bien_ban();
            bienBan.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bienBan.setSoQuyetDinh(cursor.getString(cursor.getColumnIndex("so_quyet_dinh")));
            bienBan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
            bienBan.setLoaiViPham(cursor.getString(cursor.getColumnIndex("loai_vi_pham")));
            bienBan.setLoaiXe(cursor.getString(cursor.getColumnIndex("loai_xe")));
            bienBan.setBienso(cursor.getString(cursor.getColumnIndex("bien_so")));
            bienBan.setSoTienPhat(cursor.getString(cursor.getColumnIndex("tien_phat")));
            bienBan.setId_can_bo(cursor.getInt(cursor.getColumnIndex("id_can_bo")));
            bienBan.setVtri(cursor.getString(cursor.getColumnIndex("vi_tri")));
            bienBanList.add(bienBan);
        }

        // Close the cursor and the database connection
        cursor.close();
        db.close();
        return bienBanList;
    }

    public List<Bien_ban> getBienBanByLoiVP(String loaiViPham) {
        List<Bien_ban> bienBanList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Execute the query to fetch the bien_ban by loaiViPham
        Cursor cursor = db.query("bien_ban", null, "loai_vi_pham = ?", new String[]{loaiViPham}, null, null, null);

        // Iterate through the cursor and populate the bienBanList
        while (cursor.moveToNext()) {
            Bien_ban bienBan = new Bien_ban();
            bienBan.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bienBan.setSoQuyetDinh(cursor.getString(cursor.getColumnIndex("so_quyet_dinh")));
            bienBan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
            bienBan.setLoaiViPham(cursor.getString(cursor.getColumnIndex("loai_vi_pham")));
            bienBan.setLoaiXe(cursor.getString(cursor.getColumnIndex("loai_xe")));
            bienBan.setBienso(cursor.getString(cursor.getColumnIndex("bien_so")));
            bienBan.setSoTienPhat(cursor.getString(cursor.getColumnIndex("tien_phat")));
            bienBan.setId_can_bo(cursor.getInt(cursor.getColumnIndex("id_can_bo")));
            bienBan.setVtri(cursor.getString(cursor.getColumnIndex("vi_tri")));
            bienBanList.add(bienBan);
        }

        // Close the cursor and the database connection
        cursor.close();
        db.close();

        return bienBanList;
    }

    public List<Bien_ban> getBienBanByViTri(String viTri) {
        List<Bien_ban> bienBanList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Execute the query to fetch the bien_ban by viTri
        Cursor cursor = db.query("bien_ban", null, "vi_tri = ?", new String[]{viTri}, null, null, null);

        // Iterate through the cursor and populate the bienBanList
        while (cursor.moveToNext()) {
            Bien_ban bienBan = new Bien_ban();
            bienBan.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bienBan.setSoQuyetDinh(cursor.getString(cursor.getColumnIndex("so_quyet_dinh")));
            bienBan.setCccd(cursor.getString(cursor.getColumnIndex("cccd")));
            bienBan.setLoaiViPham(cursor.getString(cursor.getColumnIndex("loai_vi_pham")));
            bienBan.setLoaiXe(cursor.getString(cursor.getColumnIndex("loai_xe")));
            bienBan.setBienso(cursor.getString(cursor.getColumnIndex("bien_so")));
            bienBan.setSoTienPhat(cursor.getString(cursor.getColumnIndex("tien_phat")));
            bienBan.setId_can_bo(cursor.getInt(cursor.getColumnIndex("id_can_bo")));
            bienBan.setVtri(cursor.getString(cursor.getColumnIndex("vi_tri")));
            bienBanList.add(bienBan);
        }

        // Close the cursor and the database connection
        cursor.close();
        db.close();

        return bienBanList;
    }
    public boolean updateAcc(Tai_khoan account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cccd", account.getCccd());
        values.put("ho_ten", account.getHo_ten());
        values.put("dia_chi", account.getDia_chi());
        values.put("sdt", account.getSdt());
        values.put("email", account.getEmail());
        values.put("gioi_tinh", account.getGioi_tinh());

        int rowsAffected = db.update("account", values, "cccd = ?", new String[]{account.getCccd()});

        return rowsAffected > 0;
    }
    public Tai_khoan getAccount(String cccd) {
        SQLiteDatabase db = this.getReadableDatabase();
        Tai_khoan account = null;

        // Truy vấn để lấy thông tin tài khoản dựa trên CCCD
        Cursor cursor = db.query("account", null, "cccd = ?", new String[]{cccd}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                // Lấy dữ liệu từ Cursor và tạo đối tượng Tai_khoan
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("ho_ten"));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("dia_chi"));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow("sdt"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String gioiTinh = cursor.getString(cursor.getColumnIndexOrThrow("gioi_tinh"));

                account = new Tai_khoan(cccd, hoTen, diaChi, sdt, email, gioiTinh);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return account;
    }

    public boolean updateAccount(Tai_khoan account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ho_ten", account.getHo_ten());
        values.put("dia_chi", account.getDia_chi());
        values.put("sdt", account.getSdt());
        values.put("email", account.getEmail());
        values.put("gioi_tinh", account.getGioi_tinh());

        // Cập nhật thông tin người dùng trong cơ sở dữ liệu
        int rowsAffected = db.update("account", values, "cccd = ?", new String[]{account.getCccd()});

        db.close();

        // Trả về true nếu cập nhật thành công (ít nhất một dòng bị ảnh hưởng), ngược lại trả về false
        return rowsAffected > 0;
    }

    // Method to update the bien ban in the database
    public boolean updateBienBan(Bien_ban bienBan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cccd", bienBan.getCccd());
        values.put("bien_so", bienBan.getBienso());
        values.put("tien_phat", bienBan.getSoTienPhat());
        values.put("vi_tri", bienBan.getVtri());
        values.put("loai_vi_pham", bienBan.getLoaiViPham());
        values.put("loai_xe", bienBan.getLoaiXe());

        int rowsAffected = db.update("bien_ban", values, "so_quyet_dinh = ?", new String[]{bienBan.getSoQuyetDinh()});

        db.close();

        return rowsAffected > 0;
    }

}


