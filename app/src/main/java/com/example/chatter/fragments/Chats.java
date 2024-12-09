package com.example.chatter.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatter.Adapters.MsgRecyclerViewAdapter;
import com.example.chatter.R;
import com.example.chatter.databinding.FragmentChatsBinding;
import com.example.chatter.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Chats extends Fragment {

    FragmentChatsBinding binding;
    ArrayList<User> userList = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        MsgRecyclerViewAdapter adapter = new MsgRecyclerViewAdapter(userList, getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();

        // Fetching data from Firebase
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setuserId(dataSnapshot.getKey()); // Set user ID properly
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("Names", "Successfully updated the names");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to read data: " + error.getMessage());
            }
        });

        return binding.getRoot();
    }
}
