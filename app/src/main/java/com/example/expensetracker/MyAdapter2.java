package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Model.Data;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context2;
    ArrayList<Data> list2;

    public MyAdapter2(Context context2, ArrayList<Data> list2) {
        this.context2 = context2;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context2).inflate(R.layout.expense_recycler,parent,false);
        return new MyAdapter2.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Data data2=list2.get(position);
        holder.etype.setText(data2.getType());
        holder.eamount.setText(String.valueOf(data2.getAmount()));
        holder.edate.setText(data2.getDate());
        holder.enote.setText(data2.getNote());
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView etype,enote,edate,eamount;

        public MyViewHolder2(View itemView){
            super(itemView);
            etype=itemView.findViewById(R.id.expense_type);
            enote=itemView.findViewById(R.id.expense_note);
            eamount=itemView.findViewById(R.id.expense_amt);
            edate=itemView.findViewById(R.id.expense_date);
        }
    }
}
