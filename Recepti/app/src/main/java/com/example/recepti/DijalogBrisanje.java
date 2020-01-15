package com.example.recepti;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DijalogBrisanje extends DialogFragment {
    DataBase db;
    Recept recept;
    Intent intent;

    public DijalogBrisanje(DataBase db, Recept recept, Intent i) {
        this.db = db;
        this.recept = recept;
        this.intent = i;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Brisanje recepta")
                .setPositiveButton("Da",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("HandlerLeak")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteRecept(recept.getReceptId());
                                startActivity(intent);
                                Toast.makeText(getContext(), "Uspesno obrisano!", Toast.LENGTH_LONG).show();
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

