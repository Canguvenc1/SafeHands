package com.example.safehands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Base64;

public class signup_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_page);


        try {

            Socket socketClient = new Socket("192.168.137.1",5555);
            DataOutputStream outToServer= new DataOutputStream(socketClient.getOutputStream());
            socketClient.close();

        }catch (Exception e){
            TextView textViewerror = (TextView) findViewById(R.id.textView6);
            textViewerror.setText("socket baglanamadi");

        }



        Button button_create = (Button) findViewById(R.id.button_create);
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_username = (TextView) findViewById(R.id.textView_username);
                TextView textView_password = (TextView) findViewById(R.id.textView_password);



                EditText editText_username = (EditText) findViewById(R.id.editText_username);
                EditText editText_password = (EditText) findViewById(R.id.editText_password);
                EditText editText_password2 = (EditText) findViewById(R.id.editText_password2);

                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();
                String password2 = editText_password2.getText().toString();

                textView_username.setVisibility(TextView.INVISIBLE);
                textView_password.setVisibility(TextView.INVISIBLE);

                try{
                    DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("credentials.txt")));
                    Scanner scanner = new Scanner(textFileStream);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] arrOfStr = line.split("-_-");
                        // process the line
                        if(username.equals(arrOfStr[0])){
                            //username exist
                            textView_username.setVisibility(TextView.VISIBLE);
                            return;
                        }
                    }
                }catch (FileNotFoundException e){
                    //file not found
                }
                catch (IOException e) {
                }
                if(!password.equals(password2)){
                    textView_password.setVisibility(TextView.VISIBLE);
                    return;
                }
                // kayit islemi
                //cam acilcak foto kaydedilip login sayfasina donulucek
                try {

                    dispatchTakePictureIntent();




                }catch (Exception e){

                }



                // Logın wındowuna gecer
                //openNewPage();

            }
        });
    }



    public static String encodeImage(byte[] imageByteArray) {

        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }


    public Bitmap bitmap=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //viewla
            /*ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);*/
            bitmap=imageBitmap;

        }



            }

    public void openNewPage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
