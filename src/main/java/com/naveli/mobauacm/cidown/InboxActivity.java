package com.naveli.mobauacm.cidown;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;


public class InboxActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nombre;
    private EditText correo;
    private EditText telefono;
    private EditText comentario;
    private String name;
    private String email;
    private String phone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Contáctanos");

        nombre = (EditText) findViewById(R.id.name_inbox);
        correo = (EditText) findViewById(R.id.phone_inbox);
        telefono = (EditText) findViewById(R.id.tel_inbox);
        comentario = (EditText) findViewById(R.id.coment);

        FloatingActionButton send = (FloatingActionButton) findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = this.nombre.getText().toString();
        email = this.correo.getText().toString();
        phone = this.telefono.getText().toString();

        if(v.getId() == R.id.send) {


            if (name.equals("") || email.equals("") || phone.equals("")) {
                showSnackBar("Hay campos vacios. Intentalo nuevamente.");
            }else{
                Intent a = new Intent(Intent.ACTION_SEND);
                a.setData(Uri.parse("mailto:"));
                a.putExtra(Intent.EXTRA_EMAIL, new String[]{"cidown@naveli.net"});
                a.putExtra(Intent.EXTRA_SUBJECT, "Contacto CIDOWN");
                a.putExtra(Intent.EXTRA_TEXT,
                        nombre.getText()+"\n\n"+
                                correo.getText()+"\n"+
                                telefono.getText()+"\n\n"+
                                comentario.getText()+"\n");
                a.setType("message/rfc822");
                startActivity(Intent.createChooser(a,"Contáctanos"));
            }
        }


    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id._send), msg, Snackbar.LENGTH_LONG)
                .show();
    }
}

