package com.example.provaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provaapp.data.model.Artigo;

import java.util.ArrayList;

class ArtigoAdapter extends ArrayAdapter<Artigo> {
    private Activity activity;
    private ArrayList<Artigo> lArtigos;
    private static LayoutInflater inflater = null;
    public ImageButton display_bt_edit, display_bt_dele;

    public ArtigoAdapter(Activity _activity, int textViewResourceId, ArrayList<Artigo> _lArtigos) {
        super(_activity, textViewResourceId, _lArtigos);
        try {
            this.activity = _activity;
            this.lArtigos = _lArtigos;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
        }
    }

    public int getCount() {
        return lArtigos.size();
    }

    public Artigo getItem(Artigo position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        //public TextView display_number;
        public TextView display_name_ci;
        public TextView display_price;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.layout_list_item, null);
                holder = new ViewHolder();
                holder.display_name = (TextView) vi.findViewById(R.id.txt_name_com);
               // holder.display_number = (TextView) vi.findViewById(R.id.txt_id);
                holder.display_name_ci = (TextView) vi.findViewById(R.id.txt_name_cie);
                holder.display_price = (TextView) vi.findViewById(R.id.txt_price);
                display_bt_dele = (ImageButton) vi.findViewById(R.id.bt_delete);
                display_bt_edit = (ImageButton) vi.findViewById(R.id.bt_edtit);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            holder.display_name.setText("(" + lArtigos.get(position).getId() + ") - "+lArtigos.get(position).getName_comer());
            //holder.display_number.setText();
            holder.display_name_ci.setText(lArtigos.get(position).getName_cient());

            holder.display_price.setText(lArtigos.get(position).getFormPrice());
        } catch (Exception e) {
        }
        /*BUTTOM EDITAR DENTRO DA LISTA*/
        display_bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", lArtigos.get(position).getId());
                bundle.putString("name_com", lArtigos.get(position).getName_comer());
                bundle.putString("name_cient", lArtigos.get(position).getName_cient());
                bundle.putString("data_fabri", lArtigos.get(position).getData_fabri());
                bundle.putString("data_valid", lArtigos.get(position).getData_valid());
                bundle.putDouble("price", lArtigos.get(position).getPrice());
                Intent intent = new Intent(activity, ArtigoRegisterActivicty.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        display_bt_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase;

                DbSqlLite dBmain = new DbSqlLite(activity);
                sqLiteDatabase = dBmain.getReadableDatabase();
                long recremove = sqLiteDatabase.delete(
                        "artigos",
                        "id=" + lArtigos.get(position).getId(),
                        null
                );
                if (recremove != -1) {
                    Toast.makeText(activity, "APAGOU" + lArtigos.get(position).getName_comer(), Toast.LENGTH_SHORT).show();
                    activity.startActivity(new Intent(activity, ListaArtigoActivicty.class));
                    activity.finish();

                }
            }
        });
        return vi;
    }
}

