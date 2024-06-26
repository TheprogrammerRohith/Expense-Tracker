package com.example.expensetracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;


public class DashboardFragment extends Fragment {

    private FloatingActionButton add,income,expense;
    private TextView income_text,expense_text,income_r,expense_r;
    private boolean flag=true;

    private FirebaseAuth mAuth;
    private DatabaseReference incomedata;
    private DatabaseReference expensedata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String uid=user.getUid();
        incomedata= FirebaseDatabase.getInstance().getReference().child("Income").child(uid);
        expensedata=FirebaseDatabase.getInstance().getReference().child("Expenses").child(uid);
        add=view.findViewById(R.id.add_ft_btn);
        income=view.findViewById(R.id.income_ft_btn);
        expense=view.findViewById(R.id.expense_ft_btn);
        income_text=view.findViewById(R.id.income_ft_text);
        expense_text=view.findViewById(R.id.expense_ft_text);
        income_r=view.findViewById(R.id.income_data);
        expense_r=view.findViewById(R.id.expense_data);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    income_text.setVisibility(View.VISIBLE);
                    income.setVisibility(View.VISIBLE);
                    expense_text.setVisibility(View.VISIBLE);
                    expense.setVisibility(View.VISIBLE);
                    addData();
                    flag=false;
                }
                else{
                    income_text.setVisibility(View.INVISIBLE);
                    income.setVisibility(View.INVISIBLE);
                    expense_text.setVisibility(View.INVISIBLE);
                    expense.setVisibility(View.INVISIBLE);
                    flag=true;
                }

            }
        });

        incomedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i_total=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    Data data=ds.getValue(Data.class);
                    i_total=i_total+data.getAmount();
                }
                income_r.setText(String.valueOf(i_total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        expensedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int e_total=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    Data data=ds.getValue(Data.class);
                    e_total=e_total+data.getAmount();
                }
                expense_r.setText(String.valueOf(e_total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void addData() {
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income_text.setVisibility(View.INVISIBLE);
                income.setVisibility(View.INVISIBLE);
                expense_text.setVisibility(View.INVISIBLE);
                expense.setVisibility(View.INVISIBLE);
                flag=true;
                incomeDataInsert();
            }

        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income_text.setVisibility(View.INVISIBLE);
                income.setVisibility(View.INVISIBLE);
                expense_text.setVisibility(View.INVISIBLE);
                expense.setVisibility(View.INVISIBLE);
                flag=true;
                expenseDataInsert();
            }
        });
    }

    public void incomeDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialog_layout,null);
        mydialog.setView(view);
        AlertDialog dialog=mydialog.create();

        EditText edtAmount=view.findViewById(R.id.amount_edt);
        EditText edtType=view.findViewById(R.id.type_edt);
        EditText edtNote=view.findViewById(R.id.note_edt);
        Button cancel=view.findViewById(R.id.btncancel);
        Button save=view.findViewById(R.id.btnsave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=edtAmount.getText().toString();
                String type=edtType.getText().toString();
                String note=edtNote.getText().toString();
                if(amount.equals("")){
                    edtAmount.setError("Required Field ..");
                    return;
                }
                int Amount=Integer.parseInt(amount);
                if(type.equals("")){
                    edtType.setError("Required Field ..");
                    return;
                }
                if(note.equals("")){
                    edtNote.setError("Required Field ..");
                    return;
                }
                String id=incomedata.push().getKey();
                String date= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(Amount,type,note,id,date);
                incomedata.child(id).setValue(data);
                Toast.makeText(getActivity(),"Details are stored",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void expenseDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialog_layout,null);
        mydialog.setView(view);
        AlertDialog dialog=mydialog.create();

        EditText Amount=view.findViewById(R.id.amount_edt);
        EditText Type=view.findViewById(R.id.type_edt);
        EditText Note=view.findViewById(R.id.note_edt);
        Button cancel_btn=view.findViewById(R.id.btncancel);
        Button save_btn=view.findViewById(R.id.btnsave);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=Amount.getText().toString();
                String type=Type.getText().toString();
                String note=Note.getText().toString();
                if(amount.equals("")){
                    Amount.setError("Required Field ..");
                    return;
                }
                int Amount=Integer.parseInt(amount);
                if(type.equals("")){
                    Type.setError("Required Field ..");
                    return;
                }
                if(note.equals("")){
                    Note.setError("Required Field ..");
                    return;
                }
                String id=expensedata.push().getKey();
                String date= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(Amount,type,note,id,date);
                expensedata.child(id).setValue(data);
                Toast.makeText(getActivity(),"Details are stored",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}