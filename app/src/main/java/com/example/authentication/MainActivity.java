package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText et_username, et_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signup(View view) {
        Intent I = new Intent(MainActivity.this,SignUp.class);
        startActivity(I);
    }

    public void login(View view) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if(et_username.getText().toString().isEmpty()){
            et_username.setError("Empty");
            et_username.requestFocus();
        }
        else if(et_password.getText().toString().isEmpty()){
            et_password.setError("Empty");
            et_password.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user.isEmailVerified()) {
                                    Toast.makeText(MainActivity.this, "Successfully signed in",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                }
                                else{
                                    user.sendEmailVerification();
                                    Toast.makeText(MainActivity.this,"Check Your Email to verify",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                et_password.setText("");
                            }
                        }
                    });
        }

    }

    public void forgetpass(View view){
        startActivity(new Intent(MainActivity.this,ForgetPass.class));
    }
}