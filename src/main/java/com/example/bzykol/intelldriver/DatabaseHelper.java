package com.example.bzykol.intelldriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String database_name = "IntellDriverDB";
    public static final String database_table = "Tankowanie";
    public DatabaseHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + database_table +  " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATA DATE, LICZNIK INTEGER, CENA FLOAT, KOSZT FLOAT, LITRY FLOAT, DOPELNA STRING, SPALANIE FLOAT, DOPELNA1 INT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + database_table);
        onCreate(db);
    }

    public boolean wstawDane(String data, String licznik, String cena, String koszt, String litry, String doPelna, String spalanie, int doPelna1){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Data", data);
        cv.put("Licznik", licznik);
        cv.put("Cena", cena);
        cv.put("Koszt", koszt);
        cv.put("Litry", litry);
        cv.put("DoPelna", doPelna);
        cv.put("Spalanie", spalanie);
        cv.put("DoPelna1", doPelna1);
        if(db.insert(database_table, null, cv) == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean usunDane(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.delete(database_table,"ID = ?", new String[]{id})>0)
            return true;
         else
            return false;

    }

    public boolean aktualizujDane(String id,String liczba){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("Liczba", liczba);
        db.update(database_table,cv,"ID = ?", new String [] {id});
        return true;
    }

    public SQLiteCursor wyswietlDane(){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + database_table, null);
        return kursor;
    }

    public String ostatniLicznik(){
        String wynik;
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT licznik FROM " + database_table + " ORDER BY ID DESC LIMIT 1", null);
        if(kursor != null && kursor.moveToFirst()){
            wynik = kursor.getString(0);
    }else return "";
        return  wynik;
    }

    public Double srednieSpalanie(){
        Double wynik = null;
        Double suma = null;
        int ile = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT SUM(spalanie),count() FROM " + database_table + " WHERE spalanie!='0' ", null);
        if(kursor != null && kursor.moveToFirst()){
            suma = kursor.getDouble(0);
            ile = kursor.getInt(1);
        }else return null;
        wynik = suma/ile;
        return  wynik;
    }

    public Double aktualneSpalanie(){
        Double wynik = null;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT spalanie FROM " + database_table +" WHERE spalanie!='0' ORDER BY ID DESC LIMIT 1", null);
        if(kursor != null && kursor.moveToFirst()){
            wynik = kursor.getDouble(0);
        }else return null;
        return  wynik;
    }

    public Double sredniKosztZa100(){
        Double wynik = null;
        Double srCena = null;
        Double suma = null;
        Double spalanie = srednieSpalanie();
        int ile = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT SUM(cena),count() FROM " + database_table, null);
        if(kursor != null && kursor.moveToFirst()){
            suma = kursor.getDouble(0);
            ile = kursor.getInt(1);
        }else return null;
        srCena = suma/ile;
        wynik = spalanie*srCena;
        return  wynik;
    }

    public Double aktualnyKosztZa100(){
        Double wynik = null;
        Double cena = null;
        Double spalanie = aktualneSpalanie();
        int ile = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT cena FROM " + database_table + " ORDER BY ID DESC LIMIT 1 ", null);
        if(kursor != null && kursor.moveToFirst()){
            cena = kursor.getDouble(0);
        }else return null;
        wynik = spalanie*cena;
        return  wynik;
    }


    public String spalanie(String licznik, String litry, int doPelna){
        Double licznik1 = Double.parseDouble(licznik);
        Double litry1 = Double.parseDouble(litry);
        Double licznik2 = null;
        Double wynik;
        int doPelna2 = 0;
        if(doPelna != 1) return "0";
        SQLiteDatabase db = this.getWritableDatabase();

            SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT licznik, doPelna1  FROM " + database_table + " ORDER BY ID DESC LIMIT 1", null);
            if(kursor != null && kursor.moveToFirst()){
                licznik2 = Double.parseDouble(kursor.getString(0));
                doPelna2 = Integer.parseInt(kursor.getString(1));
            }else return "0";

        if(doPelna2 == 0)return "0";

        wynik = litry1/((licznik1-licznik2)/100);
        return wynik.toString();
    }

    public Double wyswietlKoszt(){
        Double koszt = null;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT SUM(KOSZT) FROM " + database_table, null);
        if(kursor != null && kursor.moveToFirst()){
            koszt = Double.parseDouble(kursor.getString(0));
        }else return null;
        return koszt;
    }
}
