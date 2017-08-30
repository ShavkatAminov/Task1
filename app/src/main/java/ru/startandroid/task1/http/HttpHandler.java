package ru.startandroid.task1.http;


import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }
    public String ReadHttpResponse(String url) {
        StringBuilder sb = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            StatusLine sl = response.getStatusLine();
            int sc = sl.getStatusCode();
            if (sc == 200) {
                HttpEntity ent = response.getEntity();
                InputStream inpst = ent.getContent();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inpst));
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } else {
                Log.e("log_tag", "I didn't  get the response!");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

