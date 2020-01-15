package com.example.recepti;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class Dijalog extends DialogFragment {
    EditText naziv;
    EditText sastojci;
    EditText priprema;
    EditText autor;

    public Dijalog(EditText naziv, EditText sastojci, EditText priprema, EditText autor) {
        this.naziv = naziv;
        this.sastojci = sastojci;
        this.priprema = priprema;
        this.autor = autor;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Resetovanje forme")
                .setPositiveButton("Da",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("HandlerLeak")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                naziv.setText("");
                                sastojci.setText("");
                                priprema.setText("");
                                autor.setText("");
                                Toast.makeText(getContext(), "Forma je uspesno resetovana!", Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("Ne",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
        return builder.create();
    }
}

