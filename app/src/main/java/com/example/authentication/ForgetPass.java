package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {

    EditText et_resetEmail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        et_resetEmail = findViewById(R.id.et_resetEmail);
        mAuth = FirebaseAuth.getInstance();
    }

    public void submitEmail(View view) {
        if(et_resetEmail.getText().toString().isEmpty()){
            et_resetEmail.setError("Empty");
            et_resetEmail.requestFocus();
        }
        else{
            String email = et_resetEmail.getText().toString().trim();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgetPass.this,"Check your email",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(ForgetPass.this,"Error ! Try again",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}