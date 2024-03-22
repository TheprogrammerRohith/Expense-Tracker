package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText username,password;
    private TextView r_login;
    private Button register;
    private ProgressBar pb;
    private FirebaseAuth mAuth;

    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(Register.this,HomePage.class);
            startActivity(intent);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username=findViewById(R.id.r_username);
        password=findViewById(R.id.r_password);
        r_login=findViewById(R.id.r_login);
        register=findViewById(R.id.r_register);
        pb=findViewById(R.id.r_progress_bar);
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String email=username.getText().toString();
                String pwd=password.getText().toString();
                if(email.equals("")){
                    Toast.makeText(Register.this,"enter username",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pwd.equals("")){
                    Toast.makeText(Register.this,"enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(view.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        r_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
}
