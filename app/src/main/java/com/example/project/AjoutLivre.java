package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AjoutLivre extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    Button ajout;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText titre,auteur,nombreexp,description,Dateedition;
    RadioButton r1,r2;
    ProgressDialog pd ;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_livre);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Ajouter Livre");
        ajout = findViewById(R.id.ajout_livre);
        titre=(EditText)findViewById(R.id.titre);
        nombreexp=(EditText)findViewById(R.id.nbrexemplaire);
        auteur=(EditText)findViewById(R.id.auteur) ;
        Dateedition=(EditText)findViewById(R.id.Dateedition) ;
        description=(EditText)findViewById(R.id.description);
        pd=new ProgressDialog(this);
       r1=(RadioButton) findViewById(R.id.r1);
        r2=(RadioButton) findViewById(R.id.r2);

       Dateedition.setOnClickListener( this);

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titre.getText().toString().trim();
                String autor=auteur.getText().toString().trim();
                String nombre=nombreexp.getText().toString().trim();
                String descript= description.getText().toString().trim();
                String date =Dateedition.getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    titre.setError("Titre obligatoire");
                }else if (TextUtils.isEmpty(autor)){
                    auteur.setError("auteur obligatoire");}
                    else if (TextUtils.isEmpty(nombre)){
                        nombreexp.setError("Nombre d'exemplaire obligatoire");
                } else if (TextUtils.isEmpty(descript)){
                    description.setError("desription obligatoire");
                } else if (TextUtils.isEmpty(date)){
                    Dateedition.setError("Date edition obligatoire");
                }
                    else{
                    uploadData();
                }


            }


        });
    }
    private void uploadData()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("titre",titre.getText().toString());
        map.put("auteur", auteur.getText().toString());
        if(r1.isChecked())
        {
            map.put("disponibilte",r1.getText().toString());}
        if(r2.isChecked())
        {map.put("disponibilte",r2.getText().toString());}
        map.put("description",description.getText().toString());
        map.put("Dateedition",Dateedition.getText().toString());
        map.put("nombreexempl",nombreexp.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("livres").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        titre.setText("");
                        auteur.setText("");
                        Dateedition.setText("");
                        nombreexp.setText("");
                        description.setText("");
                        Toast.makeText(getApplicationContext(),"Insertion effectué avec succés",Toast.LENGTH_LONG).show();
                        Intent i=new Intent (getApplicationContext(),MainActivity_Home.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"ECHEC",Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onClick(View v) {

        if (v == Dateedition) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            Dateedition.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }
}




