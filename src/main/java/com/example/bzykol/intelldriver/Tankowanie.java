package com.example.bzykol.intelldriver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Tankowanie extends Activity {
    DatabaseHelper db;
    Button btnDodaj;
    EditText etData, etLicznik, etCena, etKoszt, etLitry;
    Switch swDoPelna;
    String czyDoPelna;
    Integer czyDoPelna1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tankowanie);
        db = new DatabaseHelper(this);

// Przypisanie obiektów

        btnDodaj = (Button) findViewById(R.id.btnDodaj);
        etData = (EditText) findViewById(R.id.etData);
        etLicznik = (EditText) findViewById(R.id.etLicznik);
        etCena = (EditText) findViewById(R.id.etCena);
        etKoszt = (EditText) findViewById(R.id.etKoszt);
        etLitry = (EditText) findViewById(R.id.etLitry);
        swDoPelna = (Switch) findViewById(R.id.swDoPelna);

// Automatyczne uzupełnienie daty
        Calendar kalendarz = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String aktualData = df.format(kalendarz.getTime());
        etData.setText(aktualData);
// Podpowiedź - ostatnia wartość licznika
        try {
            etLicznik.setHint("Licznik kilometrów  (ostatni:" + db.ostatniLicznik() + ")");
        }catch(Exception e){}
// Dodawanie rekordu

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swDoPelna.isChecked()){
                    czyDoPelna = swDoPelna.getTextOn().toString();
                    czyDoPelna1 = 1;
                } else{
                    czyDoPelna = swDoPelna.getTextOff().toString();
                    czyDoPelna1 = 0;
                }
                try {
                    String spalanie = db.spalanie(etLicznik.getText().toString(),etLitry.getText().toString(),czyDoPelna1);
                    Float cena = Float.parseFloat(etKoszt.getText().toString()) / Float.parseFloat(etLitry.getText().toString());
                    Boolean czySieUdalo;
                    czySieUdalo = db.wstawDane(
                            etData.getText().toString(),
                            etLicznik.getText().toString(),
                            cena.toString(),
                            etKoszt.getText().toString(),
                            etLitry.getText().toString(),
                            czyDoPelna,
                            spalanie,
                            czyDoPelna1
                    );
                    if (czySieUdalo) {
                        Toast.makeText(Tankowanie.this, "Udało się!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Tankowanie.this, "Niestety, nie udało się :(", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Toast.makeText(Tankowanie.this, "Uzupełnij wszystkie pola!", Toast.LENGTH_LONG).show();
                }
                etLicznik.setHint("Licznik kilometrów  (ostatni:" + db.ostatniLicznik()+")");
            }

        });



    }

}
