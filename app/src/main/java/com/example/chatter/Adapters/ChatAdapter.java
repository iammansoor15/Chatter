package com.example.chatter.Adapters;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatter.R;
import com.example.chatter.modal.ChatModel;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter{



    ArrayList<ChatModel> chatModels;
    Context context;;

    public ChatAdapter(ArrayList<ChatModel> chatModels, Context context){
        this.chatModels = chatModels;
        this.context = context;
    }

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;


    @Override
    public int getItemViewType(int position) {
        if(chatModels.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else {
            return RECEIVER_VIEW_TYPE;

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_msg_design,parent,false);
            return new SenderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_msg_design,parent,false);
            return new ReceiverViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModels.get(position);

        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.senderMsg.setText(chatModel.getMessage());
            formatTime(senderViewHolder.senderTime, chatModel.getTimestamp());
        } else {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.receiverMsg.setText(chatModel.getMessage());
            formatTime(receiverViewHolder.receiverTime, chatModel.getTimestamp());
        }
    }



    // method to format the timestamp and set it to the time TextView
    private void formatTime(TextView senderTime, Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String formattedTime = sdf.format(new Date(timestamp));
            senderTime.setText(formattedTime);
        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg,receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiverMsg);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderMsg);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }




}
