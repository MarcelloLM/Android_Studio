package com.example.nippo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class tela_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogin);
    }

    public void onClick(View view){
        Intent intent = new Intent(tela_login.this, tela_cadastro.class);
        startActivity(intent);
    }
}