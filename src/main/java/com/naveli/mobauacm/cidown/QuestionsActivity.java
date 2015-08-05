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

public class QuestionsActivity extends ActionBarActivity implements View.OnClickListener{

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
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id._main), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    ListViewAdapter adapter;

    String[] preguntas = {
            "¿Usted está embarazada?",
            "¿En qué consisten los estudios de diagnóstico prenatal para síndrome de Down?",
            "¿Qué es el síndrome de Down?",
            "¿De qué manera afecta el síndrome de Down a mi bebé?",
            "Datos generales sobre personas con síndrome de Down",
            "Datos médicos sobre personas con síndrome de Down",
            "¿De qué forma afecta a mi familia un bebé con síndrome de Down?",
            "¿Qué recursos existen para las mujeres embarazadas del Distrito Federal?",
            "¿Qué recursos existen para las personas con síndrome de Down y sus familias?"
    };

    int[] icon = {
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question,
            R.drawable.question

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
        adapter = new ListViewAdapter(this, preguntas, icon);
        lista.setAdapter(adapter);
        setTitle("Información CIDOWN");

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {

                switch (position){
                    case 0:
                        Intent a = new Intent(QuestionsActivity.this, QuestActivity.class);
                        a.putExtra("pregunta",preguntas[position]);
                        a.putExtra("respuesta",getString(R.string.pregunta1));
                        startActivity(a);
                        break;
                    case 1:
                        Intent b = new Intent(QuestionsActivity.this, QuestActivity.class);
                        b.putExtra("pregunta", preguntas[position]);
                        b.putExtra("respuesta", getString(R.string.pregunta2));
                        startActivity(b);
                        break;
                    case 2:
                        Intent c = new Intent(QuestionsActivity.this, QuestActivity.class);
                        c.putExtra("pregunta", preguntas[position]);
                        c.putExtra("respuesta", getString(R.string.pregunta3));
                        startActivity(c);
                        break;
                    case 3:
                        Intent d = new Intent(QuestionsActivity.this, QuestActivity.class);
                        d.putExtra("pregunta", preguntas[position]);
                        d.putExtra("respuesta", getString(R.string.pregunta4));
                        startActivity(d);
                        break;
                    case 4:
                        Intent e = new Intent(QuestionsActivity.this, QuestActivity.class);
                        e.putExtra("pregunta", preguntas[position]);
                        e.putExtra("respuesta", getString(R.string.pregunta5));
                        startActivity(e);
                        break;
                    case 5:
                        Intent f = new Intent(QuestionsActivity.this, QuestActivity.class);
                        f.putExtra("pregunta", preguntas[position]);
                        f.putExtra("respuesta", getString(R.string.pregunta6));
                        startActivity(f);
                        break;
                    case 6:
                        Intent g = new Intent(QuestionsActivity.this, QuestActivity.class);
                        g.putExtra("pregunta", preguntas[position]);
                        g.putExtra("respuesta", getString(R.string.pregunta7));
                        startActivity(g);
                        break;
                    case 7:
                        Intent h = new Intent(QuestionsActivity.this, QuestActivity.class);
                        h.putExtra("pregunta", preguntas[position]);
                        h.putExtra("respuesta", getString(R.string.pregunta8));
                        startActivity(h);
                        break;
                    case 8:
                        Intent i = new Intent(QuestionsActivity.this, QuestActivity.class);
                        i.putExtra("pregunta",preguntas[position]);
                        i.putExtra("respuesta",getString(R.string.pregunta9));
                        startActivity(i);
                        break;
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
            Intent a = new Intent(QuestionsActivity.this, InboxActivity.class);
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