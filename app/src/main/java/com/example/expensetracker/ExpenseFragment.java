package com.example.expensetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExpenseFragment extends Fragment {

    private FirebaseAuth Auth;
    private DatabaseReference expenseData;
    private RecyclerView recyclerView;
    MyAdapter2 myAdapter2;
    ArrayList<Data> list2;
    private TextView expense_result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense, container, false);

        recyclerView=view.findViewById(R.id.expense_recycler_view);
        Auth=FirebaseAuth.getInstance();
        FirebaseUser user=Auth.getCurrentUser();
        String uid=user.getUid();
        expenseData= FirebaseDatabase.getInstance().getReference().child("Expenses").child(uid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list2=new ArrayList<>();
        myAdapter2=new MyAdapter2(getContext(),list2);
        recyclerView.setAdapter(myAdapter2);
        expense_result=view.findViewById(R.id.expense_result);
        expenseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int e_total_value=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    Data data=ds.getValue(Data.class);
                    e_total_value=e_total_value+data.getAmount();
                    list2.add(data);
                }
                expense_result.setText(String.valueOf(e_total_value));
                myAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}