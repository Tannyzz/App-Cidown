package com.naveli.mobauacm.cidown;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Directory extends ActionBarActivity implements View.OnClickListener {

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

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> directorioList;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DIRECT = "nombre";
    //private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "nombre";

    // products JSONArray
    JSONArray instituciones = null;

    ListView lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.facebook);
        FloatingActionButton tw = (FloatingActionButton) findViewById(R.id.twitter);
        fb.setOnClickListener(this);
        tw.setOnClickListener(this);

        final Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("etd"));

        // Hashmap para el ListView
        directorioList = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.list);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = lista.getAdapter().getItem(position);
                String texto = obj.toString();

                Intent a = new Intent(Directory.this, DataSet.class);
                a.putExtra("institucion",texto.substring(8,(texto.length())-1).replace(" ","%20"));
                a.putExtra("del",bundle.getString("etd"));
                startActivity(a);
            }
        });


    }//fin onCreate

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


    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Directory.this);
            pDialog.setMessage("Cargando Directorio Cidown. Por favor espere...");
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
            String url_naveli_cidown = "http://cidown.naveli.net/get_datas.php?estado="+bundle.getString("etd").replace(" ","%20");
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
                        //String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOMBRE);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        //map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, name);

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
                            Directory.this,
                            directorioList,
                            R.layout.single_post,
                            new String[] {
                                    //TAG_ID,
                                    TAG_NOMBRE,
                            },
                            new int[] {
                                    //R.id.single_post_tv_id,
                                    R.id.nombre,
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
            Intent a = new Intent(Directory.this, InboxActivity.class);
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
