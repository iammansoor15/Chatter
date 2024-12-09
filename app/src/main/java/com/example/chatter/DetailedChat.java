package com.example.chatter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatter.Adapters.ChatAdapter;
import com.example.chatter.databinding.ActivityDetailedChatBinding;
import com.example.chatter.modal.ChatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class DetailedChat extends AppCompatActivity {
    private ActivityDetailedChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();



        final String  senderId = auth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String name =  getIntent().getStringExtra("name");
        String profileImg = getIntent().getStringExtra("Profile_Img");


        //BackPress button
        binding.backPress.setOnClickListener(v -> onBackPressed());

        //Taskbar name and profile pic
        binding.username.setText(name);
        Picasso.get().load(profileImg).placeholder(R.drawable.nodp).into(binding.profilePic);


        //Chat Adapter
        ArrayList<ChatModel>chatModels = new ArrayList<>();
        ChatAdapter chatAdapter = new ChatAdapter(chatModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);



        String SENDER_ROOM = senderId+receiverId;
        String RECEIVER_ROOM = receiverId+senderId;

        database.getReference().child("Chats").child(SENDER_ROOM).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModels.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ChatModel chatModel = snapshot1.getValue(ChatModel.class);
                    chatModels.add(chatModel);
                }
                chatAdapter.notifyDataSetChanged();
                if (!chatModels.isEmpty()) {
                    binding.chatRecyclerView.smoothScrollToPosition(chatModels.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Sending Messsege
        binding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messege = binding.edtMessege.getText().toString();
                final ChatModel model = new ChatModel(senderId,messege);
                model.setTimestamp(new Date().getTime());
                binding.edtMessege.setText("");

                database.getReference().child("Chats").child(SENDER_ROOM).push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("Chats").child(RECEIVER_ROOM).push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });
    }





}