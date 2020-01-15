package com.example.recepti;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    LinearLayout mainLayout;

    LinearLayout receptLayout;
    List<Recept> recepti;
    EditText pretraga;
    List<Recept> receptiKopija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pretraga = findViewById(R.id.pretraga);

        Button buttonDodaj = findViewById(R.id.buttonDodaj);
        kategorije();
        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditRecepta.class);
                startActivity(i);

            }
        });
        generateData();
        pretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pretraga();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void kategorije() {
        Spinner kategorije = (Spinner) findViewById(R.id.kategorije);
        //kategorije.setBackgroundColor(Color.BLACK);


        final ArrayList<String> kategorija = new ArrayList<>();
        kategorija.add("Sve kategorije");
        kategorija.add("Kolaci");
        kategorija.add("Corbe");
        kategorija.add("Jela");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kategorija);

        kategorije.setAdapter(adapter);

        kategorije.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recepti = receptiKopija;
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                List<Recept> rezPretrage = new ArrayList<Recept>();
                if (kategorija.get(position).equals("Sve kategorije")) {
                    rezPretrage = recepti;
                } else {
                    for (final Recept r : recepti) {
                        if (r.getKategorija().equals(kategorija.get(position))) {
                            rezPretrage.add(r);
                        }
                    }
                }

                mainLayout.removeAllViews();
                recepti = rezPretrage;
                for (final Recept r : recepti) {
                    receptLayout = (LinearLayout) inflater.inflate(R.layout.recept,
                            mainLayout, false);

                    TextView naziv = receptLayout.findViewById(R.id.prikazNaziv);
                    naziv.setTextColor(Color.WHITE);
                    naziv.setPadding(40, 0, 0, 0);
                    naziv.setTextSize(18);
                    TextView kategorija = receptLayout.findViewById(R.id.prikazKategorija);
                    kategorija.setTextColor(Color.WHITE);
                    kategorija.setPadding(40, 0, 0, 0);
                    kategorija.setTextSize(18);
                    TextView autor = receptLayout.findViewById(R.id.prikazAutor);
                    autor.setTextColor(Color.WHITE);
                    autor.setPadding(40, 0, 0, 0);
                    autor.setTextSize(18);
                    ImageView viewSlika = receptLayout.findViewById(R.id.viewSlika);


                    receptLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle extras = new Bundle();
                            extras.putString("id", String.valueOf(r.getReceptId()));
                            //LinearLayout opsirnijeLayout = findViewById(R.id.opsirnijeLayout);
                            Intent i = new Intent(MainActivity.this, Opsirnije.class);
                            i.putExtras(extras);
                            startActivity(i);

                        }
                    });

                    // Dobavljanje slike
                    String lowerPicName = r.getSlika();
                    String resource = "R.drawable." + lowerPicName;
                    int idSlike = getResources().getIdentifier(lowerPicName, "drawable", getPackageName());


                    viewSlika.setImageResource(idSlike);
                    naziv.setText(r.getNaziv());
                    kategorija.setText(r.getKategorija());
                    autor.setText(r.getAutor());

                    mainLayout.addView(receptLayout);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public List<Recept> dobaviRecepte() {
        DataBase db = new DataBase(this);


        recepti = db.getAllRecepti();
        if (recepti.size() == 0) {
            db.addRecept("Pita s jabukama", "Kolaci", "jabuke, jaja, secer, kore", "U serpici prokuvati vodu i ulje i to ce nam posluziti za prelivanje kora da bi bile rskavije nakon pecenja, a i zbog lakseg savijanja u strudlice.\n" + "U pakovanju obicno ima 12 kora i ja pravim cetiri strudlice.\n" + "\n" + "Jabuke ocistiti i narendati.\n", "Lazar Filipovic", "kolaci");
            db.addRecept("Pilca Supa", "Corbe", "piletina, voda, vegeta", "Napuni šerpu sa 2 litra vode, te dodajte pileća leđa ili dva pileća bataka. Zatim stavite na ringlu. Dok se voda zagrejava iseckajte 3 srednje šargarepe, 3 paškanata na kolutove, a 1 glavicu crnog luka na četvrtine.", "Mirjana Filipovic", "corbe");
            db.addRecept("Pilav", "Jela", "400 g belog mesa\n 2 glavice crnog luka\n5 šaka pirinča\n1 šargarepa\nulje\nvegeta\nso\nbiber", " 1\nLuk i šargarepu propržiti na ulju, dodati sitni seckano belo meso pa sve zajedno propržiti. Naliti jednu čašu vode i dodati pirinač, začin, so, biber. Kuvati oko desetak minuta dok se pirinač ne skuva. Po potrebi dodavati vodu.\n" + "\n" + "2\n" + "Kada je pirinač skuvan, staviti u rernu jedno dvadesetak minuta na 200C da voda uvri i da se pilav zapeče.", "Milan Miric", "jela");
        }

        return recepti;
    }

    private void generateData() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mainLayout = findViewById(R.id.mainLayout);


        recepti = dobaviRecepte();
        receptiKopija = dobaviRecepte();

        for (final Recept r : recepti) {
            receptLayout = (LinearLayout) inflater.inflate(R.layout.recept,
                    mainLayout, false);

            TextView naziv = receptLayout.findViewById(R.id.prikazNaziv);
            TextView kategorija = receptLayout.findViewById(R.id.prikazKategorija);
            TextView autor = receptLayout.findViewById(R.id.prikazAutor);
            ImageView viewSlika = receptLayout.findViewById(R.id.viewSlika);


            receptLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("id", String.valueOf(r.getReceptId()));
                    //LinearLayout opsirnijeLayout = findViewById(R.id.opsirnijeLayout);
                    Intent i = new Intent(MainActivity.this, Opsirnije.class);
                    i.putExtras(extras);
                    startActivity(i);

                }
            });


            // Dobavljanje slike
            String lowerPicName = r.getSlika();
            String resource = "R.drawable." + lowerPicName;
            int id = getResources().getIdentifier(lowerPicName, "drawable", getPackageName());


            viewSlika.setImageResource(id);
            naziv.setText(r.getNaziv());
            kategorija.setText(r.getKategorija());
            autor.setText(r.getAutor());

            mainLayout.addView(receptLayout);
        }

    }

    private void pretraga() {
        recepti = receptiKopija;
        LayoutInflater inflater = LayoutInflater.from(this);
        List<Recept> rezPretrage = new ArrayList<Recept>();
        String pretraga = ((TextView) findViewById(R.id.pretraga)).getText().toString();
        for (final Recept r : recepti) {
            if (r.getNaziv().toUpperCase().contains(pretraga.toUpperCase())) {
                rezPretrage.add(r);
            }
        }
        mainLayout.removeAllViews();
        recepti = rezPretrage;
        for (final Recept r : recepti) {
            receptLayout = (LinearLayout) inflater.inflate(R.layout.recept,
                    mainLayout, false);

            TextView naziv = receptLayout.findViewById(R.id.prikazNaziv);
            naziv.setTextColor(Color.WHITE);
            naziv.setPadding(40, 0, 0, 0);
            naziv.setTextSize(18);
            TextView kategorija = receptLayout.findViewById(R.id.prikazKategorija);
            kategorija.setTextColor(Color.WHITE);
            kategorija.setPadding(40, 0, 0, 0);
            kategorija.setTextSize(18);
            TextView autor = receptLayout.findViewById(R.id.prikazAutor);
            autor.setTextColor(Color.WHITE);
            autor.setPadding(40, 0, 0, 0);
            autor.setTextSize(18);
            ImageView viewSlika = receptLayout.findViewById(R.id.viewSlika);


            receptLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("id", String.valueOf(r.getReceptId()));
                    //LinearLayout opsirnijeLayout = findViewById(R.id.opsirnijeLayout);
                    Intent i = new Intent(MainActivity.this, Opsirnije.class);
                    i.putExtras(extras);
                    startActivity(i);

                }
            });

            // Dobavljanje slike
            String lowerPicName = r.getSlika();
            String resource = "R.drawable." + lowerPicName;
            int id = getResources().getIdentifier(lowerPicName, "drawable", getPackageName());


            viewSlika.setImageResource(id);
            naziv.setText(r.getNaziv());
            kategorija.setText(r.getKategorija());
            autor.setText(r.getAutor());

            mainLayout.addView(receptLayout);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        dobaviRecepte();
    }


}
