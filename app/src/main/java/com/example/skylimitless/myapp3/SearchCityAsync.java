package com.example.skylimitless.myapp3;

import android.content.Entity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.factual.driver.Circle;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.common.collect.Lists;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aakash on 3/19/2016.
 */
public abstract class SearchCityAsync extends AsyncTask<Query, Integer, List<CityOutput>>
{   protected Factual factual = new Factual("Kks5hV2AiBSR94s0eCRi8YVJZHppeyL3QPZDP2sQ", "CjBwUopBmOwVcveD7BWX4CYg8kRTrR7ju27MlABF");
    protected String output;
   @Override
    protected final List<CityOutput> doInBackground(Query... params)
   {
       List<CityOutput> outputList = new ArrayList<CityOutput>();
      if(params != null && params.length > 0)
      {
          List<ReadResponse> searchRes = Lists.newArrayList();
          Query url = params[0];
          try
          {
              Log.e("Aakash", "Before 26" + url + "hiee");

                ReadResponse resp = factual.fetch("restaurants-us", url);
                Log.e("Aakash",resp.toString());
              JSONArray cityArray = new JSONArray(resp.getData());
              String catout = "";
              String cuiout = "";
              String pri = "";
              for(int i = 0; i < cityArray.length(); i++)
              {
                  catout = "";
                  cuiout = "";
                  pri = "";
                  JSONObject jsonOutput = cityArray.getJSONObject(i);
                  Log.e("Aakash", i + "th record is :-" + cityArray.getJSONObject(i));
                  JSONArray catArray = jsonOutput.getJSONArray("category_labels");
                  Log.e("Aakash","catArray"+catArray.toString());
                  for(int j = 0;j < catArray.length();j++)
                  {
                      Log.e("Aakash","catArrNest start");
                      JSONArray catArrNest = catArray.getJSONArray(j);
                      Log.e("Aakash","catArrNest"+catArrNest.toString());
                      int k = 0;
                      for(k = 0; k < catArrNest.length()-1;k++)
                      {
                         catout = catout+catArrNest.getString(k)+"->";
                      }
                      catout = catout+catArrNest.getString(k)+"\n";
                      Log.e("Aakash",catout);

                  }
                  if(jsonOutput.has("cuisine")) {
                      JSONArray cuiArray = jsonOutput.getJSONArray("cuisine");
                      //Log.e("Aakash","catArray"+catArray.toString());
                      for (int j = 0; j < cuiArray.length(); j++) {
                          //  Log.e("Aakash","catArrNest start");
                          //JSONArray cuiArrNest = cuiArray.getJSONArray(j);
                          // Log.e("Aakash","catArrNest"+catArrNest.toString());
                          //int k = 0;
                          //for(k = 0; k < cuiArrNest.length()-1;k++)
                          //{
                          //  cuiout = cuiout+cuiArrNest.getString(k)+"->";
                          //}Los
                          cuiout = cuiout + cuiArray.getString(j) + "\n";
                          //Log.e("Aakash",catout);

                          if(jsonOutput.has("price")){ pri = jsonOutput.getString("price");}
                      }
                  }
                  CityOutput output_rec = new CityOutput(jsonOutput.getString("locality"),
                          catout,
                          jsonOutput.getString("address"),
                          jsonOutput.getString("name"),
                          jsonOutput.getString("rating"),
                          jsonOutput.getString("website"),
                          cuiout,
                          pri,
                          jsonOutput.getString("tel")
                          );
                  outputList.add(output_rec);
              }
             //String out = factual.fetch("restaurants-us", url).toString();
              //searchRes.add(factual.fetch("restaurants-us", url));
              //JSONArray cityArray =  new JSONArray()
              //Log.e("Aakash","Response returned :- "+resp+"  size   " +resp.getData());
          }
          catch(Exception e)
          {
                Log.e("Aakash","catch exception"+e.toString());
                return null;

          }
          return outputList;
      }
      return null;
   }


}
