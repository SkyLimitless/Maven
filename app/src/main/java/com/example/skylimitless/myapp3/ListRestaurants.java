package com.example.skylimitless.myapp3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.factual.driver.Query;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurants extends Activity {

    List<CityOutput> restaurants = new ArrayList<CityOutput>();
    String search_city = null;
    String search_url = null;
    ArrayAdapter<CityOutput> cityAdapter = null;
    RelativeLayout load = null;
    RelativeLayout failed = null;
    ListView success =  null;
    private ProgressBar spinner;
    downloadRestaurants down_rest = new downloadRestaurants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);
        search_city = getIntent().getStringExtra("City");
        //load = (RelativeLayout)findViewById(R.layout.load);
        //failed = (RelativeLayout)findViewById(R.id.failed_xml);
        success = (ListView) findViewById(R.id.listView);
        cityAdapter = new CityAdapter(this,restaurants);
        success.setAdapter(cityAdapter);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        registerForContextMenu(success);
        success.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent next = new Intent(ListRestaurants.this,Details.class);
                next.putExtra("Restaurant",cityAdapter.getItem(i));
                startActivity(next);
            }
        });
        //make_toast(R.id.load_xml+"");
        //make_toast(search_city);
        /*Code for api*/
        callLoad();
    }

    @Override
    protected void onDestroy()
    {
        finish();
        super.onDestroy();
    }
    private void callLoad()
    {
        //setContentView(R.layout.load);
        Log.e("Aakash", "Before Async");
        spinner.setVisibility(View.VISIBLE);
       // ProgressBar = new ProgressDialog(this);
        down_rest.cancel(true);
        down_rest = new downloadRestaurants();

        Log.e("Aakash", "Before");

        Query query = new Query()
                .field("locality").equal(search_city)
                .only("name","address","locality","rating","category_labels","website","cuisine","price","tel")
                .limit(new Long(50));
        down_rest.execute(query);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_restaurants, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class downloadRestaurants extends SearchCityAsync {
        @Override
        public void onPostExecute(List<CityOutput> outputList) {
            // Hide the loading in progress views
            //loadingLayout.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            Log.e("Aakash", "Post Execute");
            // If no courses were returned, an error occured, show the failed views and toast the screen
            if (outputList == null) {
                Toast.makeText(ListRestaurants.this, "Bad Internet Connection, Please Try Again Later", Toast.LENGTH_LONG).show();
                onDestroy();
            } else {
                // If we received courses, clear our current course list and add the new courses
                ListRestaurants.this.restaurants.clear();

                // If the course list is empty, show our empty message, otherwise update the list view
                if (outputList.isEmpty()) {
                   // setContentView(R.layout.failed);
                    Toast.makeText(ListRestaurants.this, search_city+" City is not present in records, Please type differnet city.", Toast.LENGTH_LONG).show();
                    onDestroy();
                } else {
                    //RestaurantListView.setVisibility(View.VISIBLE);
                    Log.e("Aakash","inside else");
                    restaurants.addAll(outputList);
                    cityAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    public void make_toast(String s)
    {
        Toast toast = Toast.makeText(this, "City:- "+s, Toast.LENGTH_LONG);
        toast.show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.listView){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(restaurants.get(info.position).getName());
        }
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.contextmenu, menu);
        //menu.setHeaderIcon(R.drawable.ic_launcher);
        //menu.setHeaderTitle("More Options!");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String addr = restaurants.get(info.position).getAddr();
        String website = restaurants.get(info.position).getWebsite();
        switch(item.getItemId()){
            case R.id.navig:
                Toast.makeText(getApplicationContext(),
                        "Navigation is starting.....", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+addr));
                startActivity(i);
                break;
            case R.id.weburl:
                Toast.makeText(getApplicationContext(),
                        "Opening Website...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(website));
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
