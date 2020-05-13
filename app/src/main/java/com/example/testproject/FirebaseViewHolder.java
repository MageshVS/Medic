package com.example.testproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.CollationElementIterator;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView date;
    public TextView problem;
    public TextView time;
    public TextView hospital_name;
    public TextView htime;
    public TextView hdate;
    public TextView blood;
    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameone);
        date = itemView.findViewById(R.id.nametwo);
        time = itemView.findViewById(R.id.time);
        problem = itemView.findViewById(R.id.problem);
        hospital_name = itemView.findViewById(R.id.hname);
        hdate = itemView.findViewById(R.id.hdate);
        htime = itemView.findViewById(R.id.htime);
        blood = itemView.findViewById(R.id.hblood);
    }
}
