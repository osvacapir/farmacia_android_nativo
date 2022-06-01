package com.example.provaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ArtigoRegisterActivicty extends AppCompatActivity {
    Button btGuardar;
    ImageButton btDeletar;
    EditText etNomeComer, etNomeCient, etId, /*etValidate, etFabri,*/
            etPrice;
    DatePicker etValidate, etFabri;
    TextView tvTitulo;
    DbSqlLite dBmain;
    SQLiteDatabase sqLiteDatabase;
    Bundle bundle;
    Calendar hoje;
    int dia, mes, ano;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigo_register);
        findById();
        bundle = getIntent().getExtras();
        SQLiteDatabase sqLiteDatabase;
        dBmain = new DbSqlLite(this);
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String hoje = frmt.format(data);
        dia = Integer.parseInt(new SimpleDateFormat("dd").format(data));
        mes = Integer.parseInt(new SimpleDateFormat("MM").format(data)) - 1;
        ano = Integer.parseInt(new SimpleDateFormat("yyyy").format(data));

        if (bundle == null) {
            limparEdits();
            insertArtigo();
            btDeletar.setVisibility(View.INVISIBLE);
        } else {
            preencherEdits(bundle);
            editArtigo();
            apagarArtigo(Integer.parseInt(bundle.get("id").toString()));
            tvTitulo.setText("Alterar Artigo nº " + bundle.get("id"));
            btGuardar.setText("Gravar Alteração");
        }
    }

    private void findById() {
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        btGuardar = (Button) findViewById(R.id.btGravar);
        btDeletar = (ImageButton) findViewById(R.id.btDelete);
        etNomeComer = (EditText) findViewById(R.id.etNomeComercial);
        etNomeCient = (EditText) findViewById(R.id.etNomeCientifico);
        etId = (EditText) findViewById(R.id.etId);
        etPrice = (EditText) findViewById(R.id.etPreco);
      /*
      etValidate = (EditText) findViewById(R.id.etDataValidade);
        etFabri = (EditText) findViewById(R.id.etDataFabrico);
        */
        etValidate = (DatePicker) findViewById(R.id.etDataValidade);
        etFabri = (DatePicker) findViewById(R.id.etDataFabrico);
       /* etValidate.setSpinnersShown(false);
        etFabri.setSpinnersShown(false);*/

    }

    private void insertArtigo() {

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEditText();
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", etId.getText().toString().trim());
                contentValues.put("name_comer", etNomeComer.getText().toString().trim());
                contentValues.put("name_cient", etNomeCient.getText().toString().trim());
                /*
                contentValues.put("data_valid", etValidate.getText().toString().trim());
                contentValues.put("data_fabri", etFabri.getText().toString().trim());
                 */
                contentValues.put("data_valid", etValidate.getDayOfMonth() + "/" + etValidate.getMonth() + "/" + etValidate.getYear());
                contentValues.put("data_fabri", etFabri.getDayOfMonth() + "/" + etFabri.getMonth() + "/" + etFabri.getYear());
                contentValues.put("price", etPrice.getText().toString().trim());

                sqLiteDatabase = dBmain.getWritableDatabase();
                Long recid = sqLiteDatabase.insert("artigos", null, contentValues);
                if (recid > 0) {
                    Toast.makeText(ArtigoRegisterActivicty.this, "Regito INSERIDO", Toast.LENGTH_SHORT).show();
                    limparEdits();
                } else {
                    tvTitulo.setError("Não Gravado!");
                }
            }
        });

    }

    private void editArtigo() {

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEditText();
                ContentValues contentValues = new ContentValues();
                setContentValues(contentValues);
                sqLiteDatabase = dBmain.getWritableDatabase();

                int recid = sqLiteDatabase.update("artigos",
                        contentValues,
                        "id=" +
                                etId.getText().toString(), null

                );
                if (recid > 0) {
                    Toast.makeText(ArtigoRegisterActivicty.this, "Regito ALTERADO", Toast.LENGTH_SHORT).show();
                    bundle = null;
                    mostratLista();

                } else {
                    tvTitulo.setError("NÃO Alterado!");
                }
            }
        });

    }

    private void apagarArtigo(int cod) {

        btDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase;

                DbSqlLite dBmain = new DbSqlLite(ArtigoRegisterActivicty.this);
                sqLiteDatabase = dBmain.getReadableDatabase();
                long recremove = sqLiteDatabase.delete(
                        "artigos",
                        "id=" + cod,
                        null
                );
                if (recremove != -1) {
                    Toast.makeText(ArtigoRegisterActivicty.this, "APAGOU", Toast.LENGTH_SHORT).show();
                    mostratLista();

                }
            }
        });

    }


    private void limparEdits() {
        etId.setText("");
        etNomeCient.setText("");
        etNomeComer.setText("");
        etPrice.setText("");
        etFabri.updateDate(ano, mes, dia);
        etValidate.updateDate(ano, mes, dia);
    }

    private void preencherEdits(Bundle bundle) {
        etId.setText(bundle.get("id").toString());
        etId.setEnabled(false);
        etNomeComer.setText(bundle.getString("name_com"));
        etNomeCient.setText(bundle.getString("name_cient"));
        etPrice.setText(String.valueOf(bundle.getDouble("price")));
        String data;
        novaData(bundle.getString("data_valida"));
        etValidate.updateDate(ano, mes, dia);
        novaData(bundle.getString("data_fabri"));
        etFabri.updateDate(ano, mes, dia);
    }

    public void novaData(String _data) {
        String[] dataSeparada = new String[3];
        if (_data != null) {
            if (_data.contains("-"))
                dataSeparada = _data.split("-");
            if (_data.contains("/"))
                dataSeparada = _data.split("/");
        dia = Integer.parseInt(dataSeparada[0]);
        mes = Integer.parseInt(dataSeparada[1]);
        ano = Integer.parseInt(dataSeparada[2]);


        }
    }

    public void mostratLista() {
        Intent intent = new Intent(this, ListaArtigoActivicty.class);
        startActivity(intent);
        finish();
    }

    public void validarEditText() {
        if (TextUtils.isEmpty(etId.getText().toString()))
            etId.setError("Campo Obrigatório");
        if (TextUtils.isEmpty(etNomeComer.getText().toString()))
            etNomeComer.setError("Campo Obrigatório");
        if (TextUtils.isEmpty(etNomeCient.getText().toString()))
            etNomeCient.setError("Campo Obrigatório");
        /*if (!(etValidate.getDayOfMonth() > 0))
            etValidate.setError("Campo Obrigatório");
        if (!(etFabri.getDayOfMonth() > 0))
            etFabri.setError("Campo Obrigatório");*/
        if (TextUtils.isEmpty(etPrice.getText().toString()))
            etId.setError("Campo Obrigatório");

    }

    public void setContentValues(ContentValues contentValues) {
        contentValues.put("id", etId.getText().toString().trim());
        contentValues.put("name_comer", etNomeComer.getText().toString().trim());
        contentValues.put("name_cient", etNomeCient.getText().toString().trim());
                /*
                contentValues.put("data_valid", etValidate.getText().toString().trim());
                contentValues.put("data_fabri", etFabri.getText().toString().trim());
                 */
        contentValues.put("data_valid", etValidate.getDayOfMonth() + "/" + etValidate.getMonth() + "/" + etValidate.getYear());
        contentValues.put("data_fabri", etFabri.getDayOfMonth() + "/" + etFabri.getMonth() + "/" + etFabri.getYear());
        contentValues.put("price", etPrice.getText().toString().trim());

    }
}