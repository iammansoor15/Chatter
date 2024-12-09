package com.example.chatter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatter.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Settings extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ProgressDialog dialog = new ProgressDialog(Settings.this);


        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateNmae = binding.edtName.getText().toString();
                String about = binding.edtAbout.getText().toString();

                HashMap<String, Object>obj = new HashMap<>();
                obj.put("name",updateNmae);
                obj.put("about",about);

                if (!updateNmae.isEmpty() && !about.isEmpty()) {
                    database.getReference().child("Users").child(auth.getUid()).updateChildren(obj);
                    dialog.setMessage("Updating details...");
                    dialog.show();
                }else {
                    Toast.makeText(Settings.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(Settings.this, "Details Updated", Toast.LENGTH_SHORT).show();
                    }
                },2000);

                binding.edtName.setText("");
                binding.edtAbout.setText("");

            }
        });



        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iEmail = new Intent(Intent.ACTION_SEND);
                iEmail.setType("message/rfc822");
                iEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"eesamansoorpatan@gmail.com"});
                iEmail.putExtra(Intent.EXTRA_SUBJECT,"Regarding Chatter app");
                iEmail.putExtra(Intent.EXTRA_TEXT,"Hi I'm  using Chatter app");
                iEmail.setPackage("com.google.android.gm");
                startActivity(iEmail);
            }
        });



        binding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LinkedIn deep link and fallback URL
                String deepLinkUrl = "linkedin://in/patan-eesa-mansoor-788503248/";
                String fallbackUrl = "https://www.linkedin.com/in/patan-eesa-mansoor-788503248/";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
                    startActivity(fallbackIntent);
                }
            }
        });


        binding.checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your GitHub repository URL
                String githubUrl = "https://github.com/iammansoor15/Chatter";

                // Create an Intent to view the URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                startActivity(intent);

            }
        });


    }
}