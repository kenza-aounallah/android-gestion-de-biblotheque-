package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button login_btn;
TextView forgotpswd;
TextView needacoount;
EditText email,pswd;
ProgressDialog progressdialog;
private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_btn=findViewById(R.id.login_btn);
        forgotpswd=findViewById(R.id.forgot);
        needacoount=findViewById(R.id.need_an_account);
        email=findViewById(R.id.login_email);
        pswd=findViewById(R.id.login_passord);
        progressdialog=new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MyLibrary");
        needacoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent( getApplicationContext(),InscriptionActivity.class );
                startActivity(j);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTEXT=email.getText().toString().trim();
                String password=pswd.getText().toString().trim();
                if (TextUtils.isEmpty(emailTEXT)){
                    email.setError("Email is required");
                }else if (TextUtils.isEmpty(password)){
                  pswd.setError("Password is required");
                }else{
                    Login(emailTEXT, password);
                }
            }



    private void Login(String emailTEXT, String password) {
        progressdialog.setTitle("please wait...");
        progressdialog.show();
       auth.signInWithEmailAndPassword(emailTEXT , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            progressdialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this , MainActivity_Home.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });


    }
    });

forgotpswd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final EditText ressetmail= new EditText(v.getContext());
        AlertDialog.Builder passwordResetDialog =new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("mot de passe oublié ?");
        passwordResetDialog.setMessage("\n" +"entrez votre email pour recevoir le lien de réinitialisation");
        passwordResetDialog.setView(ressetmail);
        passwordResetDialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //extraire email et envoyer lien
                auth.sendPasswordResetEmail(ressetmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),
                                    "lien de réinitialisation envoyé à votre e-mail",Toast.LENGTH_LONG).show();
                        }
                       else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
});
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();


}

}