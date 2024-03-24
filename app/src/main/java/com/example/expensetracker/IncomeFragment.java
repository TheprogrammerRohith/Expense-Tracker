package com.example.expensetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class IncomeFragment extends Fragment {

    private FirebaseAuth Auth;
    private DatabaseReference incomeData;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Data> list;
    private TextView income_result;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_income, container, false);
        recyclerView=view.findViewById(R.id.income_recycler_view);
        Auth=FirebaseAuth.getInstance();
        FirebaseUser user=Auth.getCurrentUser();
        String uid=user.getUid();
        incomeData=FirebaseDatabase.getInstance().getReference().child("Income").child(uid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(getContext(),list);
        recyclerView.setAdapter(myAdapter);
        income_result=view.findViewById(R.id.income_result);

        incomeData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i_total_value=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    Data data=ds.getValue(Data.class);
                    i_total_value=i_total_value+data.getAmount();
                    list.add(data);
                }
                income_result.setText(String.valueOf(i_total_value));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }



}