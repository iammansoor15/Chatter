package com.example.chatter.Adapters;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatter.DetailedChat;
import com.example.chatter.R;
import com.example.chatter.modal.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MsgRecyclerViewAdapter extends RecyclerView.Adapter<MsgRecyclerViewAdapter.ViewHolder> {
    FirebaseDatabase database;
    FirebaseAuth auth;
    AlertDialog.Builder alertDialog;

    ArrayList<User>userList;
    Context context;
    public MsgRecyclerViewAdapter(ArrayList<User>userList, Context context){
        this.userList = userList;
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);

        // Log data for debugging
        Log.d("RecyclerView", "Binding item: " + user.getName() + " at position: " + position);

        // Load user profile image
        Picasso.get().load(user.getProfileImg()).placeholder(R.drawable.nodp).into(holder.profileImg);

        // Set user name
        holder.name.setText(user.getName());

        // Listen for real-time updates to the last message
        database.getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getuserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                // Get last message
                                String lastMessage = snapshot1.child("message").getValue(String.class);
                                holder.lastMsg.setText(lastMessage != null ? lastMessage : "No messages yet");

                                // Get timestamp and format it
                                Long timestamp = snapshot1.child("timestamp").getValue(Long.class);
                                if (timestamp != null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                    String formattedTime = sdf.format(new Date(timestamp));
                                    holder.timestamp.setText(formattedTime);
                                } else {
                                    holder.timestamp.setText("No time available");
                                }
                            }
                        } else {
                            holder.lastMsg.setText("No messages yet");
                            holder.timestamp.setText("  ");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        holder.lastMsg.setText("Error loading message");
                        holder.timestamp.setText("Error loading timestamp");
                    }
                });

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            Log.d("RecyclerView", "Item clicked: " + user.getName());

            Intent intent = new Intent(context, DetailedChat.class);
            intent.putExtra("userId", user.getuserId());
            intent.putExtra("name", user.getName());
            intent.putExtra("Profile_Img", user.getProfileImg());
            context.startActivity(intent);
        });

        //removing user
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete Chat")
                        .setMessage("Are you sure you want to remove this user?").setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            database.getReference().child("Chats").child(auth.getUid() + user.getuserId()).removeValue();
                            userList.remove(position);
                            notifyItemRemoved(position);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        });
                alertDialog.show();


                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImg ;
        TextView name,lastMsg,timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profileImg);
            name = itemView.findViewById(R.id.name);
            lastMsg = itemView.findViewById(R.id.lastMsg);
            timestamp = itemView.findViewById(R.id.timeStamp);
        }
    }





}
