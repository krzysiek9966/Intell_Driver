package com.example.bzykol.intelldriver;

import android.app.Activity;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.widget.TextView;

public class Statystyki extends Activity {

    DatabaseHelper db;
    TextView tvKoszty;
    TextView tvSrSpalanie;
    TextView tvSrKosztZa100;
    TextView tvAktualSpalanie;
    TextView tvAktualKosztZa100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        db = new DatabaseHelper(this);

        tvKoszty = (TextView) findViewById(R.id.tvKoszty);
        tvSrSpalanie = (TextView) findViewById(R.id.tvSrSpalanie);
        tvSrKosztZa100 = (TextView) findViewById(R.id.tvSrKosztZa100);
        tvAktualSpalanie = (TextView) findViewById(R.id.tvAktualSpalanie);
        tvAktualKosztZa100 = (TextView) findViewById(R.id.tvAktualKosztZa100);

// Wydano na paliwo:
        try {
            if(db.wyswietlKoszt()!=null) {
                tvKoszty.setText(String.format("%.2f", db.wyswietlKoszt()) + " zł");
            }
        }catch(Exception e){}

// Średnie spalanie
        try {
            if(db.srednieSpalanie()!=null) {
                tvSrSpalanie.setText(String.format("%.2f", db.srednieSpalanie()) + " l/100km");
            }
        }catch(Exception e){}

// Średni koszto za 100km
        try {
            if(db.sredniKosztZa100()!=null) {
                tvSrKosztZa100.setText(String.format("%.2f", db.sredniKosztZa100()) + " zł");
            }
        }catch(Exception e){}

// Aktualne spalanie
        try {
            if(db.aktualneSpalanie()!=null) {
                tvAktualSpalanie.setText(String.format("%.2f", db.aktualneSpalanie()) + " l/100km");
            }
        }catch(Exception e){}

// Aktualny koszt 100km
        try {
            if(db.aktualnyKosztZa100()!=null) {
                tvAktualKosztZa100.setText(String.format("%.2f", db.aktualnyKosztZa100()) + " zł");
            }
        }catch(Exception e){}

    }

}
