package com.naveli.mobauacm.cidown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TableRow;

/**
 * Created by plaknava on 4/08/15.
 */
public class InicioActivity extends ActionBarActivity implements View.OnClickListener{

    public boolean existeInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        TableRow btn_dir = (TableRow) findViewById(R.id.iddirect);
        TableRow btn_inf = (TableRow) findViewById(R.id.idinfo);
        TableRow btn_share = (TableRow) findViewById(R.id.idshare);
        TableRow btn_contact = (TableRow) findViewById(R.id.idcontact);
        TableRow btn_term = (TableRow) findViewById(R.id.idterminos);
        TableRow btn_develop = (TableRow) findViewById(R.id.iddevelopers);

        btn_dir.setOnClickListener(this);
        btn_inf.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_term.setOnClickListener(this);
        btn_develop.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iddirect){
            Intent a = new Intent(InicioActivity.this, MainActivity.class);
            startActivity(a);
        }
        if(v.getId() == R.id.idinfo){
            Intent b = new Intent(InicioActivity.this, QuestionsActivity.class);
            startActivity(b);
        }
        if(v.getId() == R.id.iddevelopers){
            Intent d = new Intent(InicioActivity.this, MobaActivity.class);
            startActivity(d);
        }
        if(v.getId() == R.id.idterminos){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Términos y Condiciones");
            builder.setMessage("Dentro de esta aplicación CIDOWN, usted podrá encontrar las diferentes fundaciones y" +
                    " organizaciones sin fines de lucro que apoyan a las personas con síndrome de" +
                    " Down y sus familias. En general, pero no todas las instituciones, proporcionan servicios gratuitos o" +
                    " de bajo costo. \n\nCIDOWN reúne a más de 90 organizaciones localizadas en la" +
                    " República Mexicana relacionadas con el síndrome de Down, de forma que puede" +
                    " encontrar información específica  o relacionada con el síndrome de Down en su" +
                    " área local.\n"+"\nLa información proporcionada en esta aplicación móvil tiene exclusivamente fines" +
                    " informativos. EL USUARIO NO DEBE UTILIZAR ESTA INFORMACIÓN COMO ASESORAMIENTO MÉDICO. Se solicita al usuario acudir a un médico autorizado o" +
                    " profesionales de la salud que sepan acerca del síndrome de Down. \n\nCIDOWN no" +
                    " se responsabiliza por ningún daño directo, indirecto, consecuente, ni por ningún" +
                    " otro daño derivado del uso de la información proporcionada en esta aplicación" +
                    " móvil con respecto a diagnósticos, tratamientos o acciones.");
            builder.setPositiveButton("OK",null);
            builder.create();
            builder.show();
        }
        if(v.getId() == R.id.idshare){
            if(existeInternet()) {
                Intent intentCompartir = new Intent(Intent.ACTION_SEND);
                intentCompartir.setType("text/plain");
                intentCompartir.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.naveli.mobauacm.cidown&hl=es");

                startActivity(Intent.createChooser(intentCompartir, "Compartir CIDOWN"));
            }else{
                showSnackBar("No hay internet. Verifica tu conexión.");
            }
        }
        if(v.getId() == R.id.idcontact){
            Intent c = new Intent(InicioActivity.this, InboxActivity.class);
            startActivity(c);
        }

    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id._inicio), msg, Snackbar.LENGTH_LONG)
                .show();
    }


}
