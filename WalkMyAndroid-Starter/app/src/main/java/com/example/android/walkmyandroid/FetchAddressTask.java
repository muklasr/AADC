package com.example.android.walkmyandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressTask extends AsyncTask<Location, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private OnTaskCompleted mListener;

    FetchAddressTask(Context mContext, OnTaskCompleted mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(Location... locations) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        Location location = locations[0];
        List<Address> addresses = null;
        String resultMessage;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size() == 0) {
            resultMessage = "No Address Found";
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressParts = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressParts.add(address.getAddressLine(i));
            }
            resultMessage = TextUtils.join("\n", addressParts);
        }
        return resultMessage;
    }

    @Override
    protected void onPostExecute(String s) {
        mListener.onTaskCompleted(s);
        super.onPostExecute(s);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(String result);
    }
}
