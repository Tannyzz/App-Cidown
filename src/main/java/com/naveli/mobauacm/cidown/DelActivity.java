package com.naveli.mobauacm.cidown;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by plaknava on 7/05/15.
 */
public class DelActivity extends ActionBarActivity implements View.OnClickListener{

    public void openSocial(View twitterMoba, String url) throws Exception
    {
        String link = url;
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    ListViewAdapter adapter;

    public boolean existeInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id._main), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("etd"));

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.facebook);
        FloatingActionButton tw = (FloatingActionButton) findViewById(R.id.twitter);
        fb.setOnClickListener(this);
        tw.setOnClickListener(this);

        final String[] delegaciones = {
                "Álvaro Obregón",
                "Azcapotzalco",
                "Benito Juárez",
                "Coyoacán",
                "Cuajimalpa",
                "Cuauhtémoc",
                "Gustavo A. Madero",
                "Iztacalco",
                "Iztapalapa",
                "Magdalena Contreras",
                "Miguel Hidalgo",
                "Milpa Alta",
                "Tláhuac",
                "Tlalpan",
                "Venustiano Carranza",
                "Xochimilco"
        };

        int[] icon = {
                R.mipmap.ic0,
                R.mipmap.ic1,
                R.mipmap.ic3,
                R.mipmap.ic4,
                R.mipmap.ic5,
                R.mipmap.ic6,
                R.mipmap.ic7,
                R.mipmap.ic8,
                R.mipmap.ic9,
                R.mipmap.ic10,
                R.mipmap.ic11,
                R.mipmap.ic12,
                R.mipmap.ic12,
                R.mipmap.ic13,
                R.mipmap.ic14,
                R.mipmap.ic15

        };
            final ListView lista = (ListView) findViewById(R.id.list);
            adapter = new ListViewAdapter(this, delegaciones, icon);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                    if (existeInternet()) {
                        Intent a = new Intent(DelActivity.this, DelDirectory.class);
                        a.putExtra("etd", delegaciones[i]);
                        startActivity(a);
                    } else {
                        showSnackBar("No hay internet. Verifica tu conexión.");
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
            Intent a = new Intent(DelActivity.this, InboxActivity.class);
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