package com.example.rgs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isUserData();

    }
    // lo usamos para mostrar el menu
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menuacciones,menu);
        return true;
    }
    // que hace cuando apretamos settings
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if ( id == R.id.btn_settings){
            Toast.makeText(this,"settings",  Toast.LENGTH_LONG).show();
            Intent setings = new Intent (this,SettingsActivity.class);
            startActivity(setings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * looking for data access into server
     * @return
     */
    public boolean isUserData ()
    {
        String email;
        //sharePreferences
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        email =preferences.getString("email", "");

        if (email.equals ("")){
            email = "Configura el usuario";

            //llamamos a la pagina de configuracion

            Intent setings = new Intent (this,SettingsActivity.class);
            startActivity(setings);
        }
        Toast.makeText(this,email,  Toast.LENGTH_LONG).show();
        return true;
    }
}