package com.example.bzykol.intelldriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void click(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tankowanieBtn:
                intent = new Intent(Menu.this,Tankowanie.class);
                startActivity(intent);
                break;
            case R.id.historiaBtn:
                intent = new Intent(Menu.this,Historia.class);
                startActivity(intent);
                break;
            case R.id.statystykiBtn:
                intent = new Intent(Menu.this,Statystyki.class);
                startActivity(intent);
                break;

        }
    }



}
