package com.example.rgs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    private EditText email;
    private RadioButton rbSp, rbEn, rbFr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // captura de los elementos de la vista
        email = (EditText) findViewById(R.id.et_email);
        rbSp = (RadioButton) findViewById(R.id.rb_sp);
        rbFr = (RadioButton) findViewById(R.id.rb_fr);
        rbEn = (RadioButton) findViewById(R.id.rb_en);

        //sharePreferences
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        email.setText((preferences.getString("email", "")));

    }
    //method save
    public void Save (View view){
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences.edit();
        Obj_editor.putString("email",email.getText().toString());
        Obj_editor.commit();
        finish();
    }
}