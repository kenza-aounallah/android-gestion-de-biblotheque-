package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class InscriptionActivity extends AppCompatActivity {
    ProgressDialog progressdialog;
EditText iemail,ipassword,nomcomplet;
Button registerbtn;
TextView alreadyhaveanaccount;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        iemail=findViewById(R.id.register_email);
        ipassword=findViewById(R.id.register_passord);
        nomcomplet=findViewById(R.id.register_nom);
        registerbtn=findViewById(R.id.register_btn);
        alreadyhaveanaccount=findViewById(R.id.already_have_an_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("S'inscrire");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        progressdialog=new ProgressDialog(this);
        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adressem = iemail.getText().toString().trim();
                String PSWD=ipassword.getText().toString().trim();
                String nom=nomcomplet.getText().toString().trim();
                if (TextUtils.isEmpty(adressem)){
                    iemail.setError("email is required");
                }
                else if( TextUtils.isEmpty(PSWD))
                {
                    ipassword.setError("passwrod is required");
                }
                else if (PSWD.length() <6 ){
                    Toast.makeText(getApplicationContext(),"password length muste be >6 ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser(adressem,PSWD,nom);
                }

            }
        });
    }

    private void registerUser(final String adressem, String pswd, final String nom) {
        progressdialog.setTitle("please wait ......");
        progressdialog.show();
        Task<AuthResult> users = mAuth.createUserWithEmailAndPassword(adressem, pswd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(adressem, nom);
                            progressdialog.dismiss();
                            Task<Void> users;
                            users = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Inscription effectue avec succes ", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Inscription échoué", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }
                    }
                });
    }}
