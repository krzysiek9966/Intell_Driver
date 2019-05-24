package com.example.bzykol.intelldriver;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Historia extends Activity {
    DatabaseHelper db;
    Button btnWyswietl, btnUsun;
    EditText etUsunId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        db = new DatabaseHelper(this);

// Przypisanie obiektów
        btnWyswietl = (Button) findViewById(R.id.btnWyswietl);
        btnUsun = (Button) findViewById(R.id.btnUsun);
        etUsunId = (EditText) findViewById(R.id.etUsunId);

// Wyświetlanie
        btnWyswietl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteCursor kursor = db.wyswietlDane();
        if (kursor.getCount() > 0) {
            StringBuffer buff = new StringBuffer();
            while (kursor.moveToNext()) {
                buff.append("ID: " + kursor.getString(0)+"\n");
                buff.append("Data: " +kursor.getString(1)+"\n");
                buff.append("Licznik: " +kursor.getString(2)+ " km" + "\n");
                buff.append("Cena: " +String.format("%.2f", kursor.getDouble(3))+ " zł/l" + "\n");
                buff.append("Koszt: " +kursor.getString(4)+ " zł" + "\n");
                buff.append("Litry: " +kursor.getString(5)+ "l" + "\n");
                if(kursor.getDouble(7)!=0)  {
                    buff.append("Spalanie: " + String.format("%.2f", kursor.getDouble(7)) + " l/100km" + "\n");
                }
                if(kursor.getInt(8)==1) {
                    buff.append("--Do pełna!--" + "\n");
                }
                buff.append("\n"+"\n");
            }
            pokazWiadomosc("Historia:", buff.toString());
        }else {
            pokazWiadomosc("Brak", "Niestety brak rekordów");
        }
    }
});

// Usuwanie
        btnUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.usunDane(etUsunId.getText().toString()))
                    Toast.makeText(Historia.this, "Udało się!", Toast.LENGTH_LONG).show();
                 else
                    Toast.makeText(Historia.this, "Niestety, nie udało się :(", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void pokazWiadomosc(String tytul, String wiadomosc){
        AlertDialog.Builder budowniczy = new AlertDialog.Builder(this);
        budowniczy.setCancelable(true);
        budowniczy.setMessage(wiadomosc);
        budowniczy.setTitle(tytul);
        budowniczy.show();
    }

}
