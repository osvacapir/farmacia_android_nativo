package com.example.provaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.provaapp.data.model.Artigo;
import com.example.provaapp.ArtigoAdapter;

import java.util.ArrayList;

public class ListaArtigoActivicty extends AppCompatActivity {
    SearchView sv_pesquisa;
    Button bt_pesquisa, bt_lista;
    ImageButton btNew, btHome;
    ListView lvLista;
    TextView tvStatus;
    DbSqlLite dBmain;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_artigo_activicty);
        fyndById();
        dBmain = new DbSqlLite(this);
        displaydata("");
        novo();
        home();
    }

    public void fyndById() {
        btNew = (ImageButton) findViewById(R.id.btNew);
        btHome = (ImageButton) findViewById(R.id.btHome);
        sv_pesquisa = (SearchView) findViewById(R.id.svSearch);
        bt_pesquisa = (Button) findViewById(R.id.btSearch);
        lvLista = (ListView) findViewById(R.id.lvArtigos);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }

    /*CARREGAR LISTA COM PAARÁMETRO SEARCH*/
    public void Search(View view) {
        displaydata(sv_pesquisa.getQuery().toString());
    }


    /*CARREGAR LISTA DA BD*/
    private void displaydata(String cond) {


        sqLiteDatabase = dBmain.getReadableDatabase();
        String sql;
        if (TextUtils.isEmpty(cond))
            sql = "select *from artigos ORDER BY name_comer ASC";
        else
            sql = "select *from artigos WHERE name_comer LIKE '%" + cond + "%' ORDER BY name_comer ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Artigo> artigos = new ArrayList<>();
        ArtigoAdapter adapter;
        if (cursor.getCount() > 0) {
            int qtd = cursor.getCount();

            int i = 0;
            while (cursor.moveToNext()) {
                artigos.add(new Artigo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5)));
                i++;
            }

        }
        tvStatus.setText(" Artigos: " + artigos.size());
        adapter = new ArtigoAdapter(this, android.R.layout.simple_list_item_1, artigos);
        lvLista.setAdapter(adapter);
    }

    public void home() {
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaArtigoActivicty.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    public void novo() {
        btNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaArtigoActivicty.this, ArtigoRegisterActivicty.class);
                startActivity(i);
                finish();
            }
        });
    }
}