package com.example.chatter.Signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatter.HomeScreen;
import com.example.chatter.databinding.ActivitySignUpBinding;
import com.example.chatter.modal.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database ;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        binding.linkSignin.setOnClickListener(v-> onBackPressed());

        dialog = new ProgressDialog(SignUp.this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("creating your CHATTER acoount");

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String cnfpassword = binding.cnfpassword.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || cnfpassword.isEmpty()){
                    Toast.makeText(SignUp.this, "places cannot be empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                if(!password.equals(cnfpassword)){
                    Toast.makeText(SignUp.this, "password mismatch", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                dialog.dismiss();
                                if (task.isSuccessful()){
                                    User user = new User(name,email,password,cnfpassword);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Intent i = new Intent(SignUp.this, HomeScreen.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}