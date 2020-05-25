package com.example.safehands;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.karan.churi.PermissionManager.PermissionManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    PermissionManager permissionmanager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionmanager.checkResult(requestCode, permissions, grantResults);
        ArrayList<String> granted = permissionmanager.getStatus().get(0).granted;
        ArrayList<String> denied = permissionmanager.getStatus().get(0).denied;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionmanager = new PermissionManager() {};
        permissionmanager.checkAndRequestPermissions(this);



        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setVisibility(TextView.INVISIBLE);
                //login napıyor
                EditText editText_username = (EditText) findViewById(R.id.editText_username);
                EditText editText_password = (EditText) findViewById(R.id.editText_password);

                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();
                if (username.length() == 0 || password.length() == 0) {
                    //bı tane textvıew ı gorunur yap

                    textView2.setVisibility(TextView.VISIBLE);
                    return;
                }
                String Cred = username + "-_-" + password;
                TextView test = (TextView) findViewById(R.id.test);
                //pair olarak database checkle, onay gelırse kamera ac
                try{
                    DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("credentials.txt")));
                    Scanner scanner = new Scanner(textFileStream);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();

                        // process the line
                        if(Cred.equals(line)){
                            //camera ac foto cek

                            openContentPage();
                        }

                    }


                }catch (FileNotFoundException e){
                    //file not found

                }
                catch (IOException e) {

                }
            }
        });


        Button button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sıgnup wındowuna gecer
                openNewPage();
            }
        });
    }


    public void openNewPage() {
        Intent intent = new Intent(this, signup_page.class);
        startActivity(intent);
    }
    public void openContentPage() {
        Intent intent = new Intent(this, content_page.class);
        startActivity(intent);
    }
}
