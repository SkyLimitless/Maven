package com.example.skylimitless.myapp3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        Bundle extras = getIntent().getExtras();
        CityOutput cityOutput = (CityOutput)extras.get("Restaurant");
        this.setTitle(cityOutput.getName());
       final TextView addr = (TextView)findViewById(R.id.textView3);
        TextView cat = (TextView)findViewById(R.id.textView5);
        TextView cui = (TextView)findViewById(R.id.textView7);
        TextView price = (TextView)findViewById(R.id.textView9);
        //TextView rate = (TextView)findViewById(R.id.textView11);
        final TextView web = (TextView)findViewById(R.id.textView13);
        final TextView tel = (TextView)findViewById(R.id.textView15);
        RatingBar Rrate = (RatingBar)findViewById(R.id.rating1);
        addr.setPaintFlags(addr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        web.setPaintFlags(addr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tel.setPaintFlags(addr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        set((TextView) findViewById(R.id.textView2), addr, cityOutput.getAddr());
        set((TextView)findViewById(R.id.textView4),cat,cityOutput.getCategory());
        set((TextView)findViewById(R.id.textView6),cui,cityOutput.getCuisine());
        set((TextView)findViewById(R.id.textView8),price,cityOutput.getPrice());
        //set((TextView)findViewById(R.id.textView10),rate,cityOutput.getRating());
        set((TextView)findViewById(R.id.textView12),web,cityOutput.getWebsite());
        set((TextView)findViewById(R.id.textView15),tel,cityOutput.getTele());
        Rrate.setRating(Float.parseFloat(cityOutput.getRating()));

        int num_$ = 0;
        Log.e("Aakash", "Price :- " + num_$);
        num_$ = Integer.parseInt(price.getText().toString());
        String price_$ = "";
        for(int i = 0; i < num_$;i++){ price_$ = price_$+"$";}
        Log.e("Aakash", "Price :- " + price_$);
        price.setText(price_$);
        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Navigation is starting.....", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + addr.getText().toString()));
                startActivity(i);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Opening Website...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(web.getText().toString()));
                startActivity(intent);
            }
        });

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tel.getText().toString()));
                startActivity(intent);
            }
        });

        return true;
    }
    public void set(TextView name,TextView value, String in)
    {
        if(in == "")
        {
            name.setVisibility(View.INVISIBLE);
            value.setVisibility(View.INVISIBLE);
        }
        else
        {
            value.setText(in);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
