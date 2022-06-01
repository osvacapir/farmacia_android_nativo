package com.example.provaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btNew, btList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btNew = (ImageButton) findViewById(R.id.btNovo);
        btList = (ImageButton) findViewById(R.id.btLista);
        DbSqlLite dBmain;
        SQLiteDatabase sqLiteDatabase;
        dBmain = new DbSqlLite(this);
        btNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovoArtigo();
            }
        });
        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLista();
            }
        });
    }

    public void abrirNovoArtigo() {
        Intent intent = new Intent(this, ArtigoRegisterActivicty.class);
        startActivity(intent);
    }
    public void abrirLista() {
        Intent intent = new Intent(this, ListaArtigoActivicty.class);
        startActivity(intent);
    }
}