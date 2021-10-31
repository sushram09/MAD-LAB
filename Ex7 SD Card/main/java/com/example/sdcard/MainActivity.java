package com.example.sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    EditText filename,filecontent;
    Button read,write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename = findViewById(R.id.fileNameET);
        filecontent = findViewById(R.id.contentET);
        read=findViewById(R.id.readButton);
        write=findViewById(R.id.writeButton);

        final File sdcard= Environment.getExternalStorageDirectory();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pathtowrite = sdcard.getAbsolutePath()+"/"+filename.getText().toString();
                String contenttowrite = filecontent.getText().toString();
                writeFunc(pathtowrite,contenttowrite);
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pathtoread = sdcard.getAbsolutePath()+"/"+filename.getText().toString();
                readFunc(pathtoread);
            }
        });
    }

    public void readFunc(String pathtoread) {
        try{
            FileReader reader=new FileReader(pathtoread);
            char[] buff=new char[1024];
            reader.read(buff);
            String text=new String(buff);
            filecontent.setText(text);
            Toast.makeText(this,"File Read",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this,"File Read Exception",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void writeFunc(String pathtowrite,String contenttowrite) {
        try{
            FileWriter writer=new FileWriter(pathtowrite,false);
            writer.write(contenttowrite);
            writer.close();
            filecontent.setText("");
            Toast.makeText(this,"File Written",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this,"File Write Exception",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}