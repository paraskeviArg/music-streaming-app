package com.example.projectb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    String artistChoice;
    public Socket connection;
    String session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Consumer consumer = new Consumer();
        File file = new File("Song Downloads");
        file.mkdir();
        Button btn_online = (Button) findViewById(R.id.btn_online);
        //Connect - Ask for type of session
        try {

            connection = new Socket("10.0.2.2", 9600);
            System.out.println("geia");
            System.out.println("Connected.");
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            String sessionQ = (String) input.readObject();
            System.out.println(sessionQ);
            output.writeObject("online");
            output.writeObject(consumer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = "online";
                startActivity(new Intent(MainActivity.this, SearchArtistActivity.class));
            }
        });


    }


}
