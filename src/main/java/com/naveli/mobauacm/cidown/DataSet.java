package com.naveli.mobauacm.cidown;

import android.content.Context;
import android.content.Intent;
import com.getbase.floatingactionbutton.FloatingActionButton;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataSet extends ActionBarActivity {

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
                .make(findViewById(R.id._dataset), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> directorioList;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DIRECT = "nombre";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_DIRECCION = "direccion";
    private static final String TAG_ESTADO = "estado";
    private static final String TAG_DELEGACION = "deleg";
    private static final String TAG_MUNICIPIO = "municipio";
    private static final String TAG_CP = "cp";
    private static final String TAG_TEL1 = "tel1";
    private static final String TAG_TEL2 = "tel2";
    private static final String TAG_EMAIL1 = "email1";
    private static final String TAG_EMAIL2 = "email2";
    private static final String TAG_POBLACION = "pob_atiende";
    private static final String TAG_SERVICIOS = "serv_pro";
    private static final String TAG_WEB = "web";
    private static final String TAG_FACEBOOK = "facebook";
    private static final String TAG_TWITTER = "twitter";
    private static final String TAG_LEGAL = "fecha_leg";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    private static String latitud;
    private static String longitud;
    private static String nombre;
    private static String direccion;

    // products JSONArray
    JSONArray instituciones = null;

    ListView lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset);

        Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("del"));

        // Hashmap para el ListView
        directorioList = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.list_dataset);





    }//fin onCreate


    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DataSet.this);
            pDialog.setMessage("Cargando Información de Institución Cidown. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todas las instituciones
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL

            Bundle bundle = getIntent().getExtras();
            String url_naveli_cidown = "http://cidown.naveli.net/dataset_cidown.php?institucion="+bundle.getString("institucion");
            JSONObject json = jParser.makeHttpRequest(url_naveli_cidown, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Instituciones:", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    instituciones = json.getJSONArray(TAG_DIRECT);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < instituciones.length(); i++) {
                        JSONObject c = instituciones.getJSONObject(i);

                        // Storing each json item in variable
                        nombre = c.getString(TAG_NOMBRE);
                        direccion = c.getString(TAG_DIRECCION);
                        String estado = c.getString(TAG_ESTADO);
                        String delegacion = c.getString(TAG_DELEGACION);
                        String municipio = c.getString(TAG_MUNICIPIO);
                        String cp = c.getString(TAG_CP);
                        String tel1 = c.getString(TAG_TEL1);
                        String tel2 = c.getString(TAG_TEL2);
                        String email1 = c.getString(TAG_EMAIL1);
                        String email2 = c.getString(TAG_EMAIL2);
                        String poblacion = c.getString(TAG_POBLACION);
                        String servicios = c.getString(TAG_SERVICIOS);
                        String web = c.getString(TAG_WEB);
                        String facebook = c.getString(TAG_FACEBOOK);
                        String twitter = c.getString(TAG_TWITTER);
                        String legal = c.getString(TAG_LEGAL);
                        latitud = c.getString(TAG_LAT);
                        longitud = c.getString(TAG_LNG);
                        // creating new HashMap
                        HashMap map = new HashMap();


                            FloatingActionButton maps = (FloatingActionButton) findViewById(R.id.maps);
                            maps.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    if(existeInternet()) {
                                        if(latitud.equals("No aplica") || longitud.equals("No aplica")) {
                                            showSnackBar("Cidown no cuenta con la dirección de la Institución.");
                                        }else{
                                            Intent maps = new Intent(DataSet.this, MapsActivity.class);
                                            maps.putExtra("latitud", Double.parseDouble(latitud));
                                            maps.putExtra("longitud", Double.parseDouble(longitud));
                                            maps.putExtra("direccion", direccion);
                                            maps.putExtra("nombre", nombre);
                                            startActivity(maps);
                                        }
                                    }else{
                                        showSnackBar("No hay internet. Verifica tu conexión.");
                                    }

                                }
                            });


                        // adding each child node to HashMap key => value
                        //map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, nombre);
                        map.put(TAG_DIRECCION, direccion);
                        map.put(TAG_ESTADO, estado);
                        map.put(TAG_DELEGACION, delegacion);
                        map.put(TAG_MUNICIPIO, municipio);
                        map.put(TAG_CP, cp);
                        map.put(TAG_TEL1, tel1);
                        map.put(TAG_TEL2, tel2);
                        map.put(TAG_EMAIL1, email1);
                        map.put(TAG_EMAIL2, email2);
                        map.put(TAG_POBLACION, poblacion);
                        map.put(TAG_SERVICIOS, servicios);
                        map.put(TAG_WEB, web);
                        map.put(TAG_FACEBOOK, facebook);
                        map.put(TAG_TWITTER, twitter);
                        map.put(TAG_LEGAL, legal);

                        directorioList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            DataSet.this,
                            directorioList,
                            R.layout.activity_instituciones,
                            new String[] {
                                    TAG_NOMBRE,
                                    TAG_DIRECCION,
                                    TAG_ESTADO,
                                    TAG_DELEGACION,
                                    TAG_MUNICIPIO,
                                    TAG_CP,
                                    TAG_LEGAL,
                                    TAG_TEL1,
                                    TAG_TEL2,
                                    TAG_EMAIL1,
                                    TAG_EMAIL2,
                                    TAG_POBLACION,
                                    TAG_SERVICIOS,
                                    TAG_WEB,
                                    TAG_FACEBOOK,
                                    TAG_TWITTER,
                            },
                            new int[] {
                                    R.id.nombre_inst,
                                    R.id.dirreccion,
                                    R.id.estado,
                                    R.id.delegacion,
                                    R.id.municipio,
                                    R.id.cp,
                                    R.id.legal,
                                    R.id.tel1,
                                    R.id.tel2,
                                    R.id.email1,
                                    R.id.email2,
                                    R.id.poblacion,
                                    R.id.servicios,
                                    R.id.web,
                                    R.id.facebook,
                                    R.id.twitter,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);

                }
            });
        }
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
            Intent a = new Intent(DataSet.this, InboxActivity.class);
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
}
