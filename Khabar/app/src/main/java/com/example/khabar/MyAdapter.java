package com.example.khabar;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.holder> {

    ArrayList<Contacts>list;
    //Contacts []list;


    public MyAdapter(ArrayList<Contacts> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row,parent,false);


        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.uName.setText((CharSequence) list.get(position).getName());
        holder.uMobile.setText((CharSequence)list.get(position).getPhone().getMobile());
        holder.uGender.setText((CharSequence)list.get(position).getGender());
        holder.uEmail.setText((CharSequence) list.get(position).getEmail());
//        holder.uMobile.setText((CharSequence)list[position].getPhone().getMobile());
//        holder.uGender.setText((CharSequence)list[position].getGender());
//        holder.uEmail.setText((CharSequence) list[position].getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class holder extends RecyclerView.ViewHolder{

        //fileds
        TextView uName;
        TextView uEmail;
        TextView uGender;
        TextView uMobile;
        public holder(@NonNull View itemView) {
            super(itemView);
            uName = (TextView) itemView.findViewById(R.id.userName);
            uEmail = (TextView) itemView.findViewById(R.id.userEmail);
            uGender = (TextView) itemView.findViewById(R.id.userGender);
            uMobile = (TextView) itemView.findViewById(R.id.userMobile);
        }
    }
}
