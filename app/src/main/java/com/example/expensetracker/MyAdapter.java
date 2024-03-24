package com.example.expensetracker;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Model.Data;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Data> list;

    public MyAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.income_recycler,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Data data=list.get(position);
        holder.itype.setText(data.getType());
        holder.iamount.setText(String.valueOf(data.getAmount()));
        holder.idate.setText(data.getDate());
        holder.inote.setText(data.getNote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itype,inote,idate,iamount;

        public MyViewHolder(View itemView){
            super(itemView);
            itype=itemView.findViewById(R.id.income_type);
            inote=itemView.findViewById(R.id.income_note);
            iamount=itemView.findViewById(R.id.income_amt);
            idate=itemView.findViewById(R.id.income_date);
        }
    }
}
