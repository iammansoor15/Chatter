package com.example.chatter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chatter.Signin.sign_in;
import com.example.chatter.databinding.ActivityHomeScreenBinding;
import com.example.chatter.fragments.Chats;
import com.example.chatter.fragments.Logs;
import com.example.chatter.fragments.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {
    private ActivityHomeScreenBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);;
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        auth = FirebaseAuth.getInstance();






        //Bottom Navigation
        binding.bottomNavigation.setSelectedItemId(R.id.chat);
        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id==R.id.chat){
                    onBtmItemSelected(new Chats(),false);
                } else if (id==R.id.status) {
                    onBtmItemSelected(new Status(),false);
                }else {
                    onBtmItemSelected(new Logs(),false);
                }

                return true;
            }

        });


    }


    //Menu bar on Top--------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(HomeScreen.this).inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if(itemId==R.id.settings){
            Intent iNext = new Intent(this, Settings.class);
            startActivity(iNext);

        } else if (itemId==R.id.logout) {

            auth.signOut();
            Intent iNext = new Intent(HomeScreen.this, sign_in.class);
            startActivity(iNext);
            finish();

        }
        return super.onOptionsItemSelected(item);

    }



    //Bottom nav Fragment Management ----------------------------------------------------------------
    public void onBtmItemSelected(Fragment fragment,Boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag==true){
            ft.add(R.id.msgContainer, fragment);
        }else {
            ft.replace(R.id.msgContainer, fragment);
        }

        ft.commit();
    }
}