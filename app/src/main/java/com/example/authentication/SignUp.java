package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_name , et_dob , et_email , et_password ,et_conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        et_name = findViewById(R.id.et_name);
        et_dob = findViewById(R.id.et_dob);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_pass);
        et_conpass = findViewById(R.id.et_con_pass);
    }

    public void register(View view) {
        String full_name = et_name.getText().toString().trim();
        String dob = et_dob.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String con_pass = et_conpass.getText().toString().trim();
        if(et_name.getText().toString().isEmpty()){
            et_name.setError("Empty");
            et_name.requestFocus();
        }
        if(et_dob.getText().toString().isEmpty()){
            et_dob.setError("Empty");
            et_dob.requestFocus();
        }
        if(et_email.getText().toString().isEmpty()){
            et_email.setError("Empty");
            et_email.requestFocus();
        }
        if(et_password.getText().toString().isEmpty()){
            et_password.setError("Empty");
            et_password.requestFocus();
        }
        if(et_conpass.getText().toString().isEmpty()){
            et_conpass.setError("Empty");
            et_conpass.requestFocus();
        }
        if(! con_pass.equals(password)){
            et_conpass.setError("Password not match");
            et_conpass.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user =new User(full_name,dob,email);

                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "Successfully Register",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this,MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(SignUp.this,"Error in adding to database"
                                            ,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                               Toast.makeText(SignUp.this,"Error in Setup authentication",
                                       Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}