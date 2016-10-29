package com.example.skylimitless.myapp3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;

import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity  {

    EditText searchCity = null;
    Button searchButton = null, currentCity = null;
    Geocoder gcd;
    LocationManager lm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Maven");
        searchCity = (EditText) findViewById(R.id.editText);
        searchButton = (Button) findViewById(R.id.button);
        currentCity = (Button) findViewById((R.id.button2));
        searchButton.setEnabled(false);
        registerForContextMenu(searchButton);
        gcd = new Geocoder(this, Locale.getDefault());
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        searchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchCity.getText().length() > 0)
                    searchButton.setEnabled(true);
                else
                    searchButton.setEnabled(false);
            }
        });

        searchCity.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.e("Aakash","before_Key_Enter");
                if (keyCode == KeyEvent.KEYCODE_ENTER && searchCity.getText().length() > 0) {
                    //Log.e("Aakash","Key_Enter");
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        Intent i = new Intent(MainActivity.this, ListRestaurants.class);
                        i.putExtra("City", searchCity.getText().toString());
                        startActivity(i);
                    }
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListRestaurants.class);
                i.putExtra("City", searchCity.getText().toString());
                startActivity(i);
            }
        });

        currentCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Address> addresses = null;

                try {
                    Location location  = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Log.e("Aakash","Inside try");
                    if(location != null) {
                        double lat = location.getLatitude();
                        Log.e("Aakash", "Inside try");
                        double lng = location.getLongitude();
                        Log.e("Aakash", "Inside try");
                        addresses = gcd.getFromLocation(lat, lng, 1);
                        Log.e("Aakash", "Inside try");
                        if (addresses.size() > 0)
                            searchCity.setText(addresses.get(0).getLocality());
                    }
                    else
                    {
                        make_toast("Please Enable Your GPS or else type manually");
                    }
                } catch (Exception e) {
                    Log.e("Aakash","Inside catch"+e.toString());
                    e.printStackTrace();
                }

            }
        });
    }


    public void make_toast(String s)
    {
        Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
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
        if (id == R.id.action_settings) {
            make_toast("                       !!!SkyLimitless!!!     \nName : Aakash Chaurasia\nemail:- aakash.chaurasia91@gmail.com");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
