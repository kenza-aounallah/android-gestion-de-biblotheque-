package com.example.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myrow,parent,false);
        return new myviewholder(view);
    }
    Button submit;
    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model) {
     holder.titre.setText("Titre :"+" "+model.getTitre());
     holder.auteur.setText("Auteur :"+" "+model.getAuteur());
     holder.description.setText("Description :"+" "+model.getDescription());
     holder.disponibilte.setText("Disponibilte :"+" "+model.getDisponibilte());
     holder.Dateedition.setText("Date edition :"+" "+model.getDateedition());
     holder.nombreexp.setText("nombre d'exemplaire :"+" "+model.getNombreexempl());
     holder.edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final DialogPlus dialogPlus;
             dialogPlus = DialogPlus.newDialog(holder.edit.getContext())
                     .setContentHolder(new ViewHolder(R.layout.dialogcontenu))
                     .setExpanded(true,1100)
                      .create();
             View myview=dialogPlus.getHolderView();
             final EditText titre=myview.findViewById(R.id.titre);
             final EditText auteur=myview.findViewById(R.id.auteur);
             final EditText description=myview.findViewById(R.id.description);
             final EditText nombreexp=myview.findViewById(R.id.nbrexemplaire);
             Button submit=myview.findViewById(R.id.usubmit);
             final RadioButton r1=myview.findViewById(R.id.r1);
             final RadioButton r2=myview.findViewById(R.id.r2);
             dialogPlus.show();
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 HashMap<String, Object> map = new HashMap<>();
                 map.put("titre",titre.getText().toString());
                 map.put("auteur", auteur.getText().toString());
                 if(r1.isChecked())
                 {
                     map.put("disponibilte",r1.getText().toString());}
                 if(r2.isChecked())
                 {map.put("disponibilte",r2.getText().toString());}
                 map.put("description",description.getText().toString());
                 map.put("nombreexempl",nombreexp.getText().toString());
                 FirebaseDatabase.getInstance().getReference().child("livres")
                         .child(getRef(position).getKey()).updateChildren(map)
                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 dialogPlus.dismiss();
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 dialogPlus.dismiss();
                             }
                         });
             }
         });


         }
     });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Suppression");
                builder.setMessage("Supprimer...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("livres")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod








    public  class myviewholder extends RecyclerView.ViewHolder {
        TextView auteur, titre, description, Dateedition, nombreexp, disponibilte;
        ImageView edit, delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre);
            auteur = (TextView) itemView.findViewById(R.id.auteur);
            description = (TextView) itemView.findViewById(R.id.description);
            Dateedition = (TextView) itemView.findViewById(R.id.date);
            nombreexp = (TextView) itemView.findViewById(R.id.nbrexemplaire);
            disponibilte = (TextView) itemView.findViewById(R.id.disponibilite);
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            delete = (ImageView) itemView.findViewById(R.id.deleteicon);

        }

    }
}