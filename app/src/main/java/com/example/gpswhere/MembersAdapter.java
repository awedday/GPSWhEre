package com.example.gpswhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {
    ArrayList<User> nameList;
    Context c;
    MembersAdapter(ArrayList<User> nameList, Context c)
    {
        this.nameList = nameList;
        this.c=c;
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        MembersViewHolder membersViewHolder = new MembersViewHolder(v,c, nameList);

        return membersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        User currentUserObj = nameList.get(position);
        holder.name_txt.setText(currentUserObj.getName());
        Picasso.get().load(currentUserObj.getImageUrl()).into(holder.circleImageView);
        Picasso.get().load(currentUserObj.getStatusid()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent( c, SideActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myIntent.putExtra("Position", currentUserObj.getUserId());
                c.startActivity(myIntent);
            }
        });

    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name_txt;
        CircleImageView circleImageView;
        ImageView imageView;

        Context c;
        ArrayList<User> nameArrayList;
        FirebaseAuth auth;
        FirebaseUser user;
        DatabaseReference databaseReference;

        public MembersViewHolder(@NonNull View itemView, Context c, ArrayList<User> nameArrayList) {
            super(itemView);

            this.c = c;
            this.nameArrayList = nameArrayList;

            itemView.setOnClickListener(this);
            auth = FirebaseAuth.getInstance();
            //user = auth.getCurrentUser();

            name_txt = itemView.findViewById(R.id.item_title);
            circleImageView = itemView.findViewById(R.id.i11);
            imageView = itemView.findViewById(R.id.statusId);
            //databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("CircleMembers");
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(c, "Вы нажали на данного пользователя", Toast.LENGTH_SHORT).show();
//            v.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    databaseReference.child("Users").child(user.getUid()).child("CircleMembers").removeValue();
//                    return true;
//                }
//            });

        }
    }
}