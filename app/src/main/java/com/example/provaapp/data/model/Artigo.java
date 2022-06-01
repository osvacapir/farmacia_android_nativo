package com.example.provaapp.data.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;

public class Artigo {
    private int id;
    private String name_comer,
            name_cient,
            data_valid,
            data_fabri;
    private double price;

    public Artigo() {
    }

    public Artigo(int id) {
        this.id = id;
    }

    public Artigo(int id, String name_comer, String name_cient, String data_valid, String data_fabri, double price) {
        this.id = id;
        this.name_comer = name_comer;
        this.name_cient = name_cient;
        this.data_valid = data_valid;
        this.data_fabri = data_fabri;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_comer() {
        return name_comer;
    }

    public void setName_comer(String name_comer) {
        this.name_comer = name_comer;
    }

    public String getName_cient() {
        return name_cient;
    }

    public void setName_cient(String name_cient) {
        this.name_cient = name_cient;
    }

    public String getData_valid() {
        return data_valid;
    }

    public void setData_valid(String data_valid) {
        this.data_valid = data_valid;
    }

    public String getData_fabri() {
        return data_fabri;
    }

    public void setData_fabri(String data_fabri) {
        this.data_fabri = data_fabri;
    }

    public double getPrice() {
        return price;
    }

    public String getFormPrice() {
        NumberFormat f = new DecimalFormat("##.###");
        String _price = f.format(price);
        return _price+" Kz";
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
