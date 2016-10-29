package com.example.skylimitless.myapp3;

/**
 * Created by Aakash on 3/21/2016.
 */
import android.os.Parcel;
import android.os.Parcelable;
public class CityOutput implements Parcelable{

    private String locality;
    private String category;
    private String addr;
    private String name;
    private String rating;
    private String website;
    private String cuisine;
    private String price;
    private String tele;

    public CityOutput(String loc, String cat, String add, String nam, String rate, String Web, String cui, String pri, String tel)
    {
        locality =  loc;
        category = cat;
        addr = add;
        name = nam;
        rating = rate;
        website = Web;
        cuisine = cui;
        price = pri;
        tele = tel;
    }

    public String getLocality() {
        return locality;
    }

    public String getCategory() {
        return category;
    }

    public String getAddr() {
        return addr;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getWebsite() {
        return website;
    }

    public String getCuisine() { return cuisine;    }

    public String getPrice() { return price;    }

    public String getTele() {
        return tele;
    }

    protected CityOutput(Parcel in) {
    }

    public static final Creator<CityOutput> CREATOR = new Creator<CityOutput>() {
        @Override
        public CityOutput createFromParcel(Parcel in) {
            String loc = in.readString();
            String cat = in.readString();
            String add = in.readString();
            String nam = in.readString();
            String rate = in.readString();
            String Web = in.readString();
            String cui = in.readString();
            String pri = in.readString();
            String tel = in.readString();

            return new CityOutput(loc,cat,add,nam,rate,Web,cui,pri,tel);
        }

        @Override
        public CityOutput[] newArray(int size) {
            return new CityOutput[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locality);
        dest.writeString(category);
        dest.writeString(addr);
        dest.writeString(name);
        dest.writeString(rating);
        dest.writeString(website);
        dest.writeString(cuisine);
        dest.writeString(price);
        dest.writeString(tele);
    }
}
