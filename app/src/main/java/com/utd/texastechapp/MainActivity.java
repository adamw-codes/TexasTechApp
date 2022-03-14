package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<User>();
    TextView userNameText;
    Communication comm = new Communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // createUserListFile();
        // setUserList();
        setContentView(R.layout.activity_main);
        userNameText = findViewById(R.id.userHomepageText);
//        if(userList.size() != 0) {
//            setUserNameText(userList.get(0));
//        }
        String setup = ((Communication) this.getApplication()).ReceivedMessage();
        ((Communication) this.getApplication()).SendMessage("Set Up");
        String response = ((Communication) this.getApplication()).ReceivedMessage();
        ((Communication) this.getApplication()).SendMessage("Get Admin User");
        response = ((Communication) this.getApplication()).ReceivedMessage();
        try {
            User adminUser = ((Communication) this.getApplication()).getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUserListFile() {
        // need to write function that writes to the userList file
        FileOutputStream stream = null;
        File directory = getFilesDir();
        // sets name based on directory and the quiz name
        File userFileDir = new File(directory, "UserList.txt");
        try{
            stream = new FileOutputStream(userFileDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            // close the stream after the file has been written
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void setUserList() {
//        try {
//            // Returns file with UserList
//            File directory = getFilesDir();
//            File[] userListFile = directory.listFiles(new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    if(name.startsWith("UserList")){
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            // Scanner to read the users from the file
//            Scanner userReader = null;
//            userReader = new Scanner(userListFile[0]);
//            while(userReader.hasNext()) {
//                String user = userReader.nextLine();
//                userList.add(user);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    private void setUserNameText(String name) {
        userNameText.setText(name);
    }

    public void onUserClick(View view) {
        Intent intent = new Intent(this, UserListActivity.class);
        intent.putExtra("userList", userList);
        startActivity(intent);
    }

    public void onLockModeClick(View view) {
        Intent intent = new Intent(this, LockModesActivity.class);
        startActivity(intent);
    }
}