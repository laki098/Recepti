package com.example.recepti;


public class Recept {
    public static final String TABLE_NAME = "recept";
    public static final String FIELD_RECEPT_ID = "recept_id";
    public static final String FIELD_NAZIV = "naziv";
    public static final String FIELD_KATEGORIJA = "kategorija";
    public static final String FIELD_SASTOJCI = "sastojci";
    public static final String FIELD_PRIPREMA = "priprema";
    public static final String FIELD_AUTOR = "autor";
    public static final String FIELD_SLIKA = "slika";

    private int receptId;
    private String naziv;
    private String kategorija;
    private String sastojci;
    private String priprema;
    private String autor;
    private String slika;

    public Recept(int receptId, String naziv, String kategorija, String sastojci, String priprema, String autor, String slika) {
        this.receptId = receptId;
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.sastojci = sastojci;
        this.priprema = priprema;
        this.autor = autor;
        this.slika = slika;
    }

    public int getReceptId() {
        return receptId;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getSastojci() {
        return sastojci;
    }

    public void setSastojci(String sastojci) {
        this.sastojci = sastojci;
    }

    public String getPriprema() {
        return priprema;
    }

    public void setPriprema(String priprema) {
        this.priprema = priprema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @Override
    public String toString() {
        return "Recept{" +
                "receptId=" + receptId +
                ", naziv='" + naziv + '\'' +
                ", kategorija='" + kategorija + '\'' +
                ", sastojci='" + sastojci + '\'' +
                ", priprema='" + priprema + '\'' +
                ", autor='" + autor + '\'' +
                ", slika='" + slika + '\'' +
                '}';
    }
}
