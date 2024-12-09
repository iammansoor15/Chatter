package com.example.chatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatter.Signin.sign_in;
import com.example.chatter.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        ImageView imgLogo = findViewById(R.id.appLogo);
        TextView appName = findViewById(R.id.appName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent iNext;
                if(auth.getCurrentUser()!=null){
                    iNext = new Intent(MainActivity.this,HomeScreen.class);
                    Log.d("MAIN ACTIVTY"," Logging in into Home Screen");
                    finish();
                }else{
                    iNext = new Intent(MainActivity.this, sign_in.class);
                    Log.d("MAIN ACTIVTY"," Logging in into Login Page");
                    finish();
                }
                startActivity(iNext);
                finish();
            }
        },2000);
    }
}