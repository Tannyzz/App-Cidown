package com.naveli.mobauacm.cidown;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    public void openSocial(View twitterMoba, String url) throws Exception
    {
        String link = url;
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
    public boolean existeInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    ListViewAdapter adapter;

    String[] estados = {
            "Distrito Federal",
            "Aguascalientes",
            "Baja California",
            "Baja California Sur",
            "Campeche",
            "Chiapas",
            "Chihuahua",
            "Coahuila",
            "Colima",
            "Durango",
            "Guanajuato",
            "Guerrero",
            "Hidalgo",
            "Jalisco",
            "Estado de México",
            "Michoacán",
            "Morelos",
            "Nayarit",
            "Nuevo León",
            "Oaxaca",
            "Puebla",
            "Querétaro",
            "Quintana Roo",
            "San Luis Potosí",
            "Sinaloa",
            "Sonora",
            "Tabasco",
            "Tamaulipas",
            "Tlaxcala",
            "Veracruz",
            "Yucatán",
            "Zacatecas"
    };

    int[] icon = {
            R.mipmap.df,
            R.drawable.ic_50px_coat_of_arms_of_aguascalientes,
            R.drawable.ic_50px_coat_of_arms_of_baja_california,
            R.drawable.ic_50px_coat_of_arms_of_baja_california_sur,
            R.drawable.ic_50px_coat_of_arms_of_campeche,
            R.drawable.ic_50px_coat_of_arms_of_chiapas,
            R.drawable.ic_50px_coat_of_arms_of_chihuahua,
            R.drawable.ic_50px_coat_of_arms_of_coahuila,
            R.drawable.ic_50px_coat_of_arms_of_colima,
            R.drawable.ic_50px_coat_of_arms_of_durango,
            R.drawable.ic_50px_coat_of_arms_of_guanajuato,
            R.drawable.ic_50px_coat_of_arms_of_guerrero,
            R.mipmap.hidalgo,
            R.mipmap.jalisco,
            R.drawable.ic_50px_coat_of_arms_of_mexico_state,
            R.drawable.ic_50px_coat_of_arms_of_michoacan,
            R.drawable.ic_50px_coat_of_arms_of_morelos,
            R.drawable.ic_50px_coat_of_arms_of_nayarit,
            R.drawable.ic_50px_coat_of_arms_of_nuevo_leon,
            R.drawable.ic_50px_coat_of_arms_of_oaxaca,
            R.drawable.ic_50px_coat_of_arms_of_puebla,
            R.drawable.ic_50px_coat_of_arms_of_queretaro,
            R.drawable.ic_50px_coat_of_arms_of_quintana_roo,
            R.drawable.ic_50px_coat_of_arms_of_san_luis_potosi,
            R.drawable.ic_50px_coat_of_arms_of_sinaloa,
            R.drawable.ic_50px_coat_of_arms_of_sonora,
            R.drawable.ic_50px_coat_of_arms_of_tabasco,
            R.drawable.ic_50px_coat_of_arms_of_tamaulipas,
            R.drawable.ic_50px_coat_of_arms_of_tlaxcala,
            R.drawable.ic_50px_coat_of_arms_of_veracruz,
            R.drawable.ic_50px_coat_of_arms_of_yucatan,
            R.drawable.ic_50px_coat_of_arms_of_zacatecas

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.facebook);
        FloatingActionButton tw = (FloatingActionButton) findViewById(R.id.twitter);
        fb.setOnClickListener(this);
        tw.setOnClickListener(this);

        final ListView lista = (ListView) findViewById(R.id.list);
        adapter = new ListViewAdapter(this, estados, icon);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {

                if(position == 0){
                        Intent a = new Intent(MainActivity.this, DelActivity.class);
                        a.putExtra("etd",estados[position]);
                        startActivity(a);}
                else{
                    if(existeInternet()) {
                        Intent b = new Intent(MainActivity.this, Directory.class);
                        b.putExtra("etd", estados[position]);
                        startActivity(b);
                    }else{
                        showSnackBar("No hay internet. Verifica tu conexión.");
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mail) {
            Intent a = new Intent(MainActivity.this, InboxActivity.class);
            startActivity(a);
        }
        if(id == R.id.compartirApp){
            Intent intentCompartir = new Intent(Intent.ACTION_SEND);
            intentCompartir.setType("text/plain");
            intentCompartir.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.naveli.mobauacm.cidown&hl=es");
            startActivity(Intent.createChooser(intentCompartir, "Compartir CIDOWN"));
        }

        return super.onOptionsItemSelected(item);
    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id._main), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.facebook){
            String fm = "http://www.facebook.com/uacm.cidown";
            View view = (View) findViewById(R.id.facebook);
            try {
                if(existeInternet()){
                    openSocial(view, fm);
                }else{
                    showSnackBar("No hay internet. Verifica tu conexión");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(v.getId() == R.id.twitter){
            String tw = "http://www.twitter.com/uacm_cidown";
            View view = (View) findViewById(R.id.twitter);
            try {
                if(existeInternet()){
                    openSocial(view, tw);
                }else{
                    showSnackBar("No hay internet. Verifica tu conexión");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
