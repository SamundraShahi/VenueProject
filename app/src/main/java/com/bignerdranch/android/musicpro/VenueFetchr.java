package com.bignerdranch.android.musicpro;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VenueFetchr
{
    private static final String TAG = "VenueFetchr";

    // used for maintaining connect with http
    public byte[] getUrlBytes(String urlSpec) throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    // to process the url
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // Used to fetch the JSON data from the url provided
    public List<Venue>  fetchItems() {

        // Putting this into array for future reference
        // where there might be array possible
        List<Venue> items = new ArrayList<>();

        try {
            // url in a string
            String url = Uri.parse("http://jellymud.com/venues/best_venue.json")
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);

            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString); // convert the string to object

            parseItems(items, jsonBody);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    // to parse the values obtained from json
    private void parseItems(List<Venue> items, JSONObject jsonBody)
            throws IOException, JSONException {

            Venue item = new Venue();
            item.setName(jsonBody.getString("name"));
            item.setAddress(jsonBody.getString("address"));
            item.setLat(jsonBody.getDouble("lat"));
            item.setLon(jsonBody.getDouble("lon"));

            items.add(item);
    }
}
