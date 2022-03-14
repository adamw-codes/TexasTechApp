package com.utd.texastechapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.MyViewHolder> {
    private ArrayList<String> userList = new ArrayList<String>();
    private OnUserListener localUserListener;

    public usersAdapter(ArrayList<String> userList, OnUserListener onUserListener){
        this.userList = userList;
        localUserListener = onUserListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView userText;
        OnUserListener onUserListener;

        public MyViewHolder(final View view, OnUserListener onUserListener){
            super(view);
            userText = view.findViewById(R.id.userEditName);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { onUserListener.onUserClick((getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public usersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout, parent, false);
        return new MyViewHolder(itemView, localUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull usersAdapter.MyViewHolder holder, int position) {
        String user = userList.get(position);
        holder.userText.setText(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface OnUserListener{
        void onUserClick(int position);
    }
}
