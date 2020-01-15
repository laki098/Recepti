package com.example.recepti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class EditRecepta extends AppCompatActivity {
    EditText naziv;
    EditText sastojci;
    EditText priprema;
    EditText autor;
    Spinner kategorije;
    Toast poruka;
    Boolean edit;
    Recept recept;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recepta);
        extras = getIntent().getExtras();
        DataBase db = new DataBase(this);
        edit = false;
        kategorije = findViewById(R.id.kategorijeEdit);

        ArrayList<String> sveKategorije = new ArrayList<>();
        sveKategorije.add("Kolaci");
        sveKategorije.add("Corbe");
        sveKategorije.add("Jela");
        kategorije();

        naziv = findViewById(R.id.inputNaziv);
        sastojci = findViewById(R.id.inputSastojci);
        priprema = findViewById(R.id.inputPriprema);
        autor = findViewById(R.id.inputAutor);

        if (extras != null) {
            edit = true;
            recept = db.getReceptById(Integer.parseInt(extras.getString("id")));
            naziv.setText(recept.getNaziv());
            sastojci.setText(recept.getSastojci());
            priprema.setText(recept.getPriprema());
            autor.setText(recept.getAutor());
            int i = 0;
            for (String k : sveKategorije) {
                if (k.equals(recept.getKategorija())) {
                    i = sveKategorije.indexOf(k);
                }
            }
            kategorije.setSelection(i);
        }

        Button sacuvaj = findViewById(R.id.buttonSacuvaj);
        sacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit == false) {
                    dodajRecept();
                } else {
                    editRecept(recept);
                }
            }
        });
        Button reset = findViewById(R.id.buttonReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetuj();
            }
        });

    }

    private void kategorije() {
        ArrayList<String> kategorija = new ArrayList<>();
        kategorija.add("Kolaci");
        kategorija.add("Corbe");
        kategorija.add("Jela");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kategorija);

        kategorije.setAdapter(adapter);
    }

    private void editRecept(Recept recept) {
        DataBase db = new DataBase(this);

        String nazivString = naziv.getText().toString().trim();
        String sastojciString = sastojci.getText().toString().trim();
        String pripremaString = priprema.getText().toString().trim();
        String autorString = autor.getText().toString().trim();
        String kategorija = (String) ((Spinner) findViewById(R.id.kategorijeEdit)).getSelectedItem();
        String slikaString = kategorija.toLowerCase();

        if (nazivString.isEmpty() != true && sastojciString.isEmpty() != true && pripremaString.isEmpty() != true) {

            db.editRecept(recept.getReceptId(), nazivString, kategorija, sastojciString, pripremaString, autorString, slikaString);
            Toast.makeText(this, "Uspesno izmenjen recept", Toast.LENGTH_LONG).show();

            Intent i = new Intent(EditRecepta.this, Opsirnije.class);
            i.putExtras(extras);
            startActivity(i);
        } else if (nazivString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli naziv", Toast.LENGTH_LONG).show();
        } else if (sastojciString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli sastojke", Toast.LENGTH_LONG).show();
        } else if (pripremaString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli pripremu", Toast.LENGTH_LONG).show();
        }


    }

    private void dodajRecept() {
        DataBase db = new DataBase(this);
        String nazivString = naziv.getText().toString().trim();
        String sastojciString = sastojci.getText().toString().trim();
        String pripremaString = priprema.getText().toString().trim();
        String autorString = autor.getText().toString().trim();
        String kategorija = (String) ((Spinner) findViewById(R.id.kategorijeEdit)).getSelectedItem();
        String slikaString = kategorija.toLowerCase();
        if (nazivString.isEmpty() != true && sastojciString.isEmpty() != true && pripremaString.isEmpty() != true) {
            db.addRecept(nazivString, kategorija, sastojciString, pripremaString, autorString, slikaString);
            Toast.makeText(this, "Uspesno dodat recept", Toast.LENGTH_LONG).show();
            naziv.setText("");
            sastojci.setText("");
            priprema.setText("");
            autor.setText("");
        } else if (nazivString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli naziv", Toast.LENGTH_LONG).show();
        } else if (sastojciString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli sastojke", Toast.LENGTH_LONG).show();
        } else if (pripremaString.isEmpty() == true) {
            Toast.makeText(this, "Niste uneli pripremu", Toast.LENGTH_LONG).show();
        }


    }

    private void resetuj() {
        String nazivString = naziv.getText().toString().trim();
        String sastojciString = sastojci.getText().toString().trim();
        String pripremaString = priprema.getText().toString().trim();
        String autorString = autor.getText().toString().trim();

        if (nazivString.isEmpty() != true || sastojciString.isEmpty() != true || pripremaString.isEmpty() != true || autorString.isEmpty() != true) {
            DialogFragment resetovanjeDijalog = new Dijalog(naziv, sastojci, priprema, autor);
            resetovanjeDijalog.show(getSupportFragmentManager(), "izlaz");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditRecepta.this, MainActivity.class));
    }
}
