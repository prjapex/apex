package com.example.apex.myapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.util.List;

/**
 * Created by Enda on 14/11/2014.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jsonObject = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST/GET method
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {

        // making HTTP request
        try {
            // check for request method
            if (method == "POST") {
                //request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            } else if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "UTF_8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF_8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            String x = "";
            if(reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    x = sb.toString();
                }
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer error", "Error converting result " + e.toString());

            // try to parse the string to a JSON object
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        // return JSON string
        return jsonObject;
    }
}
