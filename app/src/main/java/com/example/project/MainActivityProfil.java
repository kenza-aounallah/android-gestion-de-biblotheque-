package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityProfil extends AppCompatActivity {
private FirebaseUser user;
private DatabaseReference reference;
private String userID;
FirebaseAuth bauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profil);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("profil");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userID=user.getUid();
        final TextView nomcomplet=(TextView)findViewById(R.id.nomc);
        final TextView mail=(TextView)findViewById(R.id.ml);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile != null)
                {
                    String fullName=userprofile.nomcomplet;
                    String email= userprofile.adressem;
                    nomcomplet.setText("Nom complet :"+fullName);
                     mail.setText("Email :"+email);
                    Toast.makeText(getApplicationContext(),"Bienvenue "+fullName,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"il ya un probleme veiuller resseyer plus tard",Toast.LENGTH_LONG
                ).show();

            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.profilmenu,menu);
        return true;
}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout)

        {  bauth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext() , MainActivity.class));
        }
        if(item.getItemId()==R.id.loc)
        {
            startActivity(new Intent(getApplicationContext() , MainActivityMap.class));

        }
        if(item.getItemId()==R.id.home)
        {
            startActivity(new Intent(getApplicationContext() , MainActivity_Home.class));

        }


        return super.onOptionsItemSelected(item);

    }}