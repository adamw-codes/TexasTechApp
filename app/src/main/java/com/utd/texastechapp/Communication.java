package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Communication extends Application implements Serializable {
    private Socket socket;

    private boolean connected = false;
    private boolean socketRunning = false;

    private DataOutputStream out;
    private DataInputStream in;

    private final String SERVER_IP_PI = "172.20.10.8";
    private final int SERVER_PORT = 5000;

    public Communication() throws SocketException {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        boolean connection;
        do {
            connection = Connect(SERVER_IP_PI);
        } while(connection);
    }

    public boolean Connect(String SERVER_IP_PI){
        try {
            SocketAddress socketAddress = new InetSocketAddress(SERVER_IP_PI, SERVER_PORT);
            Socket socket = new Socket();
            socket.connect(socketAddress, 1000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            socketRunning = true;
            System.out.println("STARTING RECEIVE THREAD");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }


    public String ReceivedMessage(){
        while (true) {
            try {
                byte[] byteData = new byte[4];
                int numBytes = in.read(byteData);
                String receivedMessage = "";
                if (numBytes <= 0) {
                    continue;
                }
                byte[] typeArray = Arrays.copyOfRange(byteData, 0, numBytes);
                String dataType = new String(typeArray, StandardCharsets.UTF_8);
                System.out.println(dataType);

                switch (dataType) {
                    case("STUP"):
                        receivedMessage = "STUP";
                        break;
                    case ("RGBG"):
                        receivedMessage = "RGBG";
                        break;
                    case ("RGF1"):
                        receivedMessage = "RGF1";
                        break;
                    case ("RGF2"):
                        receivedMessage = "RGF2";
                        break;
                    case ("RGFD"):
                        receivedMessage = "RGFD";
                        break;
                    case ("RGPC"):
                        receivedMessage = "RGPC";
                        break;
                    case("RGDN"):
                        receivedMessage = "RGDN";
                        break;
                    case("REDY"):
                        receivedMessage = "REDY";
                        break;
                    case("SDAU"):
                        receivedMessage = "SDAU";
                        break;
                    case("SDUL"):
                        receivedMessage = "SDUL";
                        break;
                    case("RMVU"):
                        receivedMessage = "RMVU";
                        break;
                    case("NOUS"):
                        receivedMessage = "NOUS";
                        break;
                    case("KPRC"):
                        receivedMessage = "KPRC";
                        break;
                    case("CURI"):
                        receivedMessage = "CURI";
                        break;
                    case("CEML"):
                        receivedMessage = "CEML";
                        break;
                }
                return receivedMessage;
            } catch (SocketException e) {
                System.out.println("Can't receive data... Socket has been closed...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SendMessage(String message){
        String sentMessage = "";

        switch(message){
            case("Set Up"):
                sentMessage = "STUP";
                break;
            case("Start Registering"):
                sentMessage = "RGST";
                break;
            case("Begin Registering"):
                sentMessage = "RGBG";
                break;
            case("Fingerprint 1"):
                sentMessage = "RGF1";
                break;
            case("Fingerprint 2"):
                sentMessage = "RGF2";
                break;
            case("Facial Recognition Start"):
                sentMessage = "RGFD";
                break;
            case("Face Scan"):
                sentMessage = "RGPC";
                break;
            case("Face Denied"):
                sentMessage = "DENY";
                break;
            case("Registering Done"):
                sentMessage = "RGDN";
                break;
            case("Get Admin User"):
                sentMessage = "SDAU";
                break;
            case("Get User List"):
                sentMessage = "SDUL";
                break;
            case("Update User Admin"):
                sentMessage = "UPAS";
                break;
            case("Remove User"):
                sentMessage = "RMVU";
                break;
            case("Set the Keypad"):
                sentMessage = "KPCC";
                break;
            case("Change URI Spotify"):
                sentMessage = "CURI";
                break;
            case("Change Email"):
                sentMessage = "CEML";
                break;
            default:
                sentMessage = message;
        }

        try {
            out.write(sentMessage.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(sentMessage + ": Message sent");
    }

    public void CloseConnection(){
        try {
            out.write("STOP_ERROR".getBytes(StandardCharsets.UTF_8));
            socketRunning = false;
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int readDataSize() throws IOException {
        byte[] byteData = new byte[16];
        int numBytes = in.read(byteData);
        byte[] sizeArray = Arrays.copyOfRange(byteData, 0, numBytes);
        String sizeStr = new String(sizeArray, StandardCharsets.UTF_8);
        sizeStr = sizeStr.trim();
        int size = -1;
        try {
            size = Integer.parseInt(sizeStr);
            System.out.println("Size: " + size);
        }
        catch (NumberFormatException e) {
            System.out.println("SIZE: NOT AN INT");
            size = -1;
        }
        return size;
    }

    public Bitmap imageReceive() throws IOException {
        int size = readDataSize();
        System.out.println(size);

        if (size > 0) {
            byte[] inData = new byte[size];
            in.readFully(inData);
            Bitmap bmp = BitmapFactory.decodeByteArray(inData, 0, inData.length);
            return bmp;
        }
        else {
            System.out.println("NO IMAGE");
        }
        return null;
    }

    public void sendUserName(String name){
        SendMessage("NAME^"+name);
    }

    public User getUser() throws IOException {
        int size = readDataSize();
        System.out.println(size);

        if(size > 0){
            byte[] inData = new byte[size];
            in.readFully(inData);
            String userData = new String(inData, StandardCharsets.US_ASCII);
            System.out.println(userData);
            String[] curUser = userData.split("&&&", 3);
            User user = new User(curUser[0], curUser[1], curUser[2]);
            return user;
        }
        else{
            System.out.println("There is no user!");
        }
        return null;
    }

    public ArrayList<User> getUserList() throws IOException {
        int size = readDataSize();
        System.out.println(size);
        ArrayList<User> userList = new ArrayList<>();

        if(size > 0) {
            for (int i = 0; i < size; i++) {
                User user = getUser();
                System.out.println(user.getName());
                userList.add(user);
            }
            return userList;
        }
        else{
            System.out.println("There are no users!");
        }
        return null;
    }

    public void updateAdminUser(String userID) {
        SendMessage(userID);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void updateKeypad(String keypadData){
        SendMessage(keypadData);
    }

    public void sendURI(String URI) {
        SendMessage(URI);
    }

    public void sendEmail(String email){
        SendMessage(email);
    }
}
