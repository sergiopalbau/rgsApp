package com.example.rgs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    WebView wb;
    int rangeStart, rangeEnd, card;
    String languaje = "en", email;
    String urlBase = "https://crudreglas.tk/displays/addGet";

    URL url;

    {
        try {
            url = new URL ("http://crudreglas.tk/display/addGet");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isUserData(); // pregunta si se han configurado las preferencias


        wb=findViewById(R.id.wv1);
        wb.setWebViewClient(new WebViewClient());
        String web = urlBase + "/" + card + "/" + email + "/" + languaje;
        Log.w("url", web);
        wb.loadUrl(web);



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

    // * looking for data access into server.

    public boolean isUserData (){

        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);

        //recuperamos los valores
        email =preferences.getString("email", "");
        languaje = preferences.getString("lang","en");

        //recuperamos los rangos de las cartas
        rangeStart = Integer.valueOf(preferences.getString("start","11"));
        rangeEnd = Integer.valueOf( preferences.getString("end","20"));
        card = Integer.valueOf(preferences.getString ("card","11"));


        if (email.equals ("")){
            email = "Configura el usuario";

            //llamamos a la pagina de configuracion

            Intent setings = new Intent (this,SettingsActivity.class);
            startActivity(setings);
        }
        Toast.makeText(this,email,  Toast.LENGTH_LONG).show();

        //recuperamos los rangos de las cartas

        return true;
    }


    public void salir (View view){
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences.edit();
        card++;
        if (card >= rangeEnd) {
            card = rangeStart;
        }
        String cardtxt = String.valueOf(card);

        Obj_editor.putString("card",cardtxt);
        Obj_editor.commit();


        finish();
    }


    public void siguiente (View view){
        String web = urlBase + "/" + card + "/" + email + "/" + languaje;
        SharedPreferences preferences = getSharedPreferences("reader", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences.edit();

        email =preferences.getString("email", "");
        languaje = preferences.getString("lang","en");

        //recuperamos los rangos de las cartas
        rangeStart = Integer.valueOf(preferences.getString("start","11"));
        rangeEnd = Integer.valueOf( preferences.getString("end","20"));
        card = Integer.valueOf(preferences.getString ("card","11"));



         web = urlBase + "/" + card + "/" + email + "/" + languaje;

        wb=findViewById(R.id.wv1);
        wb.setWebViewClient(new WebViewClient());

        Log.w("url", web);
        wb.loadUrl(web);
        card++;
        if (card >= rangeEnd) {
            card = rangeStart;
        }
        String cardtxt = String.valueOf(card);

        Obj_editor.putString("card",cardtxt);
        Obj_editor.commit();



    }

    public void json () throws IOException {
        //-------------------- pruebas json -----------------
        Log.w ("json","-----------json--------------------------");
        String data = null;
        try {
            url = new URL ("https:crudreglas.tk/cards/sendAll");
        } catch (MalformedURLException e) {
            Log.w("json", "catch de url, salio mal");
            e.printStackTrace();
        }
        URLConnection urlConnection = (HttpURLConnection)url.openConnection();
        Log.w ("json",urlConnection.getContent().toString()  );
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Log.w ("json", "antes de try  data(leer)");
        try {
           data = Leer (in);
            Log.w ("json", "dentro de try  data(leer)");
        }catch ( IOException e ){
            Log.w ("json", "ha  fallado");
        }
        finally {
            in.close();

        }
        Log.w ("json","----SALIDA ---------------------------------");
        Log.w ("json", data.toString());

    }

    public String Leer (InputStream is) throws IOException {
        Log.w ("leer", "estoy dentro");
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        int i = is.read();
        while (i != -1) {
            bo.write(i);
            i = is.read();
        }
        return bo.toString();
    }


    //------------------------------------------------------------------------------------
    private InputStream getStream (String url){
        try{
            URL url2 = new URL (url);
            URLConnection urlConnection = url2.openConnection();
            urlConnection.setConnectTimeout(1000);
            return urlConnection.getInputStream();

        } catch (Exception e) {
            return null;

        }

    }

}