package com.example.recepti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class Opsirnije extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opsirnije);
        Bundle extras = getIntent().getExtras();
        final DataBase db = new DataBase(this);
        final Recept recept = db.getReceptById(Integer.parseInt(extras.getString("id")));

        TextView opsirnijeNaziv = findViewById(R.id.opsirnijeNaziv);
        opsirnijeNaziv.setTextColor(Color.WHITE);
        opsirnijeNaziv.setPadding(40, 0, 0, 0);
        opsirnijeNaziv.setTextSize(18);
        TextView opsirnijeKategorija = findViewById(R.id.opsirnijeKategorija);
        opsirnijeKategorija.setTextColor(Color.WHITE);
        opsirnijeKategorija.setPadding(40, 0, 0, 0);
        opsirnijeKategorija.setTextSize(18);
        TextView opsirnijeAutor = findViewById(R.id.opsirnijeAutor);
        opsirnijeAutor.setTextColor(Color.WHITE);
        opsirnijeAutor.setPadding(40, 0, 0, 0);
        opsirnijeAutor.setTextSize(18);
        TextView opsirnijeSastojci = findViewById(R.id.opsirnijeSastojci);
        opsirnijeSastojci.setTextColor(Color.WHITE);
        opsirnijeSastojci.setPadding(40, 0, 0, 0);
        opsirnijeSastojci.setTextSize(18);
        TextView opsirnijePriprema = findViewById(R.id.opsirnijePriprema);
        opsirnijePriprema.setTextColor(Color.WHITE);
        opsirnijePriprema.setPadding(40, 0, 0, 0);
        opsirnijePriprema.setTextSize(18);
        ImageView opsirnijeSlika = findViewById(R.id.opsirnijeSlika);

        opsirnijeNaziv.setText(recept.getNaziv());
        opsirnijeKategorija.setText(recept.getKategorija());
        opsirnijeAutor.setText(recept.getAutor());
        opsirnijeSastojci.setText(recept.getSastojci());
        opsirnijePriprema.setText(recept.getPriprema());
        int res = getResources().getIdentifier(recept.getSlika(), "drawable", this.getPackageName());
        opsirnijeSlika.setImageResource(res);

        Button obrisi = findViewById(R.id.buttonObrisi);
        obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Opsirnije.this, MainActivity.class);
                DialogFragment brisanjeDijalog = new DijalogBrisanje(db, recept, i);
                brisanjeDijalog.show(getSupportFragmentManager(), "izlaz");
            }
        });
        Button izmeni = findViewById(R.id.buttonOpsirnijeIzmeni);
        izmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("id", String.valueOf(recept.getReceptId()));
                Intent i = new Intent(Opsirnije.this, EditRecepta.class);
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }
}
