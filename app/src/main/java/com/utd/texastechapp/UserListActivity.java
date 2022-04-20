package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UserListActivity extends AppCompatActivity implements usersAdapter.OnUserListener{
    ArrayList<User> userList = new ArrayList<User>();
    int selectedUser;
    RecyclerView recyclerView;
    usersAdapter adapter;
    TextView userPicked;
    CheckBox primaryCheckBox;
    Communication comm;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.usersListRecyclerView);
        primaryCheckBox = findViewById(R.id.primaryCheckBox);
        Intent intent = getIntent();
        ((Communication)this.getApplication()).SendMessage("Get User List");
        response = ((Communication) this.getApplication()).ReceivedMessage();
        if(!response.equals("NOUS")) {
            try {
                userList = ((Communication) this.getApplication()).getUserList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setAdapter();
    }

    private void setAdapter() {
        adapter = new usersAdapter(userList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void onAddUserClick(View view) {
        Intent intent = new Intent(this, SetFingerprintActivity.class);
        ((Communication) this.getApplication()).SendMessage("Start Registering");
        String response = ((Communication) this.getApplication()).ReceivedMessage();
        startActivity(intent);
    }


    @Override
    public void onUserClick(int position) {
        if((position != 0 && primaryCheckBox.isChecked()) || (position == 0 && !primaryCheckBox.isChecked())) {
            primaryCheckBox.toggle();
        }
            selectedUser = position;
    }

    public void onPrimaryUserClick(View view) {
        primaryCheckBox = (CheckBox) view;
        if(primaryCheckBox.isChecked() && selectedUser != 0) {
            makePrimaryUser(selectedUser);
        }
        else{
            primaryCheckBox.toggle();
        }
    }

    private void makePrimaryUser(int userPos) {
        for(int i = 0; i < selectedUser; i++){
            Collections.swap(userList, selectedUser - 1 - i, selectedUser - i);
        }
        ((Communication)this.getApplication()).SendMessage("Update User Admin");
        String response = ((Communication)this.getApplication()).ReceivedMessage();
        ((Communication)this.getApplication()).updateAdminUser(userList.get(0).getUserID());
        userList.get(0).setAdmin(true);
        userList.get(selectedUser).setAdmin(false);
        selectedUser = 0;
        adapter.notifyDataSetChanged();
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDeleteUserClick(View view) {
        if(userList.size() != 0) {
            ((Communication)this.getApplicationContext()).SendMessage("Remove User");
            String response = ((Communication)this.getApplicationContext()).ReceivedMessage();
            ((Communication)this.getApplicationContext()).SendMessage(userList.get(selectedUser).getUserID());
            userList.remove(selectedUser);
            adapter.notifyDataSetChanged();
        }
    }
}