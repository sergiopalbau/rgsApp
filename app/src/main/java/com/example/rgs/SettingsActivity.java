package com.example.rgs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private EditText email,startCard, endCard;
    private RadioButton rbSp, rbEn, rbFr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // captura de los elementos de la vista
        email = (EditText) findViewById(R.id.et_email);
        startCard = (EditText) findViewById(R.id.et_inicio);
        endCard = (EditText) findViewById(R.id.et_fin);
        email = (EditText) findViewById(R.id.et_email);
        rbSp = (RadioButton) findViewById(R.id.rb_sp);
        rbFr = (RadioButton) findViewById(R.id.rb_fr);
        rbEn = (RadioButton) findViewById(R.id.rb_en);

        //sharePreferences
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        email.setText((preferences.getString("email", "")));
        startCard.setText(preferences.getString ("start","11"));
        endCard.setText(preferences.getString("end","20"));

        String lang = preferences.getString("lang", "en");

        if (lang =="es") {
            rbSp.setChecked(true);
        }else if ((lang =="fr")) {
            rbFr.setChecked(true);
        }else {
            rbEn.setChecked(true);
        }


    }
    //method save
    public void Save (View view){
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences.edit();
        Obj_editor.putString("email",email.getText().toString());

        String lang = "";
        if (rbSp.isChecked() ){
            lang = "es";
        }else if ( rbFr.isChecked()){
            lang = "fr";

        }else {
            lang = "en";
        }

        Obj_editor.putString("lang", lang);
        Obj_editor.putString("email",email.getText().toString());
        String start= (startCard.getText().toString());
        int numberStart = Integer.parseInt(startCard.getText().toString());
        int numberEnd = Integer.parseInt(endCard.getText().toString());
        if (numberStart <= numberEnd){
            Obj_editor.putString("start",startCard.getText().toString());
            Obj_editor.putString("end",endCard.getText().toString());
            Obj_editor.commit();
            finish();
        }else {
            Toast.makeText(this,"El rango no es correcto",  Toast.LENGTH_LONG).show();

        }



    }
}