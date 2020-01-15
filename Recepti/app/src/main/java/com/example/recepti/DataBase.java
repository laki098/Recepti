package com.example.recepti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_FILEAME = "baza.sqlite";

    public DataBase(@Nullable Context context) {
        super(context, DATABASE_FILEAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                Recept.TABLE_NAME, Recept.FIELD_RECEPT_ID, Recept.FIELD_NAZIV, Recept.FIELD_KATEGORIJA, Recept.FIELD_SASTOJCI, Recept.FIELD_PRIPREMA, Recept.FIELD_AUTOR, Recept.FIELD_SLIKA));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Recept.TABLE_NAME));
        onCreate(db);
    }

    public void addRecept(String naziv, String kategorija, String sastojci, String priprema, String autor, String slika) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Recept.FIELD_NAZIV, naziv);
        cv.put(Recept.FIELD_KATEGORIJA, kategorija);
        cv.put(Recept.FIELD_SASTOJCI, sastojci);
        cv.put(Recept.FIELD_PRIPREMA, priprema);
        cv.put(Recept.FIELD_AUTOR, autor);
        cv.put(Recept.FIELD_SLIKA, slika);

        db.insert(Recept.TABLE_NAME, null, cv);

    }

    public void editRecept(int receptId, String naziv, String kategorija, String sastojci, String priprema, String autor, String slika) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Recept.FIELD_NAZIV, naziv);
        cv.put(Recept.FIELD_KATEGORIJA, kategorija);
        cv.put(Recept.FIELD_SASTOJCI, sastojci);
        cv.put(Recept.FIELD_PRIPREMA, priprema);
        cv.put(Recept.FIELD_AUTOR, autor);
        cv.put(Recept.FIELD_SLIKA, slika);
        db.update(Recept.TABLE_NAME, cv, Recept.FIELD_RECEPT_ID + " = ?", new String[]{String.valueOf(receptId)});
    }

    public int deleteRecept(int receptId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Recept.TABLE_NAME, Recept.FIELD_RECEPT_ID + " = ?", new String[]{String.valueOf(receptId)});
    }

    public Recept getReceptById(int receptId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = String.format("SELECT * FROM %s WHERE %s = %s", Recept.TABLE_NAME, Recept.FIELD_RECEPT_ID, receptId);
        Cursor res = db.rawQuery(SQL, null);
        if (res.moveToFirst()) {
            String naziv = res.getString(res.getColumnIndex(Recept.FIELD_NAZIV));
            String kategorija = res.getString(res.getColumnIndex(Recept.FIELD_KATEGORIJA));
            String sastojci = res.getString(res.getColumnIndex(Recept.FIELD_SASTOJCI));
            String priprema = res.getString(res.getColumnIndex(Recept.FIELD_PRIPREMA));
            String autor = res.getString(res.getColumnIndex(Recept.FIELD_AUTOR));
            String slika = res.getString(res.getColumnIndex(Recept.FIELD_SLIKA));
            return new Recept(receptId, naziv, kategorija, sastojci, priprema, autor, slika);
        } else {
            return null;
        }

    }

    public List<Recept> getAllRecepti() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = String.format("SELECT * FROM %s", Recept.TABLE_NAME);
        Cursor res = db.rawQuery(SQL, null);
        res.moveToFirst();
        List<Recept> lista = new ArrayList<>(res.getCount());
        while (res.isAfterLast() == false) {
            int receptId = res.getInt(res.getColumnIndex(Recept.FIELD_RECEPT_ID));
            String naziv = res.getString(res.getColumnIndex(Recept.FIELD_NAZIV));
            String kategorija = res.getString(res.getColumnIndex(Recept.FIELD_KATEGORIJA));
            String sastojci = res.getString(res.getColumnIndex(Recept.FIELD_SASTOJCI));
            String priprema = res.getString(res.getColumnIndex(Recept.FIELD_PRIPREMA));
            String autor = res.getString(res.getColumnIndex(Recept.FIELD_AUTOR));
            String slika = res.getString(res.getColumnIndex(Recept.FIELD_SLIKA));
            lista.add(new Recept(receptId, naziv, kategorija, sastojci, priprema, autor, slika));
            res.moveToNext();
        }
        return lista;
    }


}
