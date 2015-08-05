package com.naveli.mobauacm.cidown;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by plaknava on 3/08/15.
 */
public class MobaActivity extends ActionBarActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moba);
        setTitle("Desarrolladores");

        ImageButton fMoba = (ImageButton) findViewById(R.id.facebookMoba);
        ImageButton tMoba = (ImageButton) findViewById(R.id.twitterMoba);
        ImageButton fNaveli = (ImageButton) findViewById(R.id.facebookNaveli);
        ImageButton tNaveli = (ImageButton) findViewById(R.id.twitterNaveli);
        ImageButton inNaveli = (ImageButton) findViewById(R.id.inNaveli);
        fMoba.setOnClickListener(this);
        tMoba.setOnClickListener(this);
        fNaveli.setOnClickListener(this);
        tNaveli.setOnClickListener(this);
        inNaveli.setOnClickListener(this);


    }
    public void openSocial(View twitterMoba, String url) throws Exception
    {
        String link = url;
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW,Uri.parse(link));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.facebookMoba){
            String fm = "http://www.facebook.com/mobauacm";
            View view = (View) findViewById(R.id.facebookMoba);
            try {
                openSocial(view, fm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(v.getId() == R.id.twitterMoba){
            String fm = "http://www.twitter.com/mobauacm";
            View view = (View) findViewById(R.id.twitterMoba);
            try {
                openSocial(view, fm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(v.getId() == R.id.facebookNaveli){
            String fm = "http://www.facebook.com/navelimx";
            View view = (View) findViewById(R.id.facebookNaveli);
            try {
                openSocial(view, fm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(v.getId() == R.id.twitterNaveli){
            String fm = "http://www.twitter.com/naveli_mx";
            View view = (View) findViewById(R.id.twitterNaveli);
            try {
                openSocial(view, fm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(v.getId() == R.id.inNaveli){
            String fm = "https://www.linkedin.com/company/naveli";
            View view = (View) findViewById(R.id.inNaveli);
            try {
                openSocial(view, fm);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
