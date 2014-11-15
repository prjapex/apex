package com.example.apex.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends Activity {

    // progress dialog
    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();
    Button btnRegister;

    //register url
    private static String registerURL = "http://192.168.0.17/android/apexdb/createcyclist.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";

    // cyclist details
    String firstName = "Dave";
    String lastName = "Phelan";
    String county = "Sligo";
    String email = "davephelan@gmail.com";
    String password = "w384930283";
    String birthDate = "283393";
    String gender = "Male";
    int height = 128;
    double weight = 77.5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // register button/create
        btnRegister = (Button) findViewById(R.id.btnRegister);

        // button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new cyclist in background thread
                new CreateNewCyclist().execute();
            }
        });
    }


    // background Async task to create new cyclist
    class CreateNewCyclist extends AsyncTask<String, String, String> {

        // before starting background thread show progress dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Creating new cyclist..");
            progressDialog.setIndeterminate(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        // creating cyclist
        protected String doInBackground(String... args) {
            // building parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("first_name", firstName));
            params.add(new BasicNameValuePair("last_name", lastName));
            params.add(new BasicNameValuePair("email_address", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("county", county));
            params.add(new BasicNameValuePair("birth_date", birthDate));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("height_cm", String.valueOf(height)));
            params.add(new BasicNameValuePair("weight_kg", String.valueOf(weight)));

            // getting JSON object
            JSONObject json = jsonParser.makeHttpRequest(registerURL, "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if(success == 1) {
                    // successfully created cyclist
                    Toast.makeText(getApplicationContext(),"Registration successful!", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), CyclistActivity.class);
                    startActivity(intent);

                    // close screen
                } else {
                    // failed to create product
                    Toast.makeText(getApplicationContext(),"Registration failed!", Toast.LENGTH_LONG);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // after completing background task dismiss progress dialog
        protected void onPostExecute(String file_url) {
            // dismiss dialog once its done
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
    // background Async task to create new cyclist
    class CreateNewCyclist extends AsyncTask<Void, Void, Boolean> {

        private void registerCyclist(String firstName, String lastName, String email,
                                     String password, String county, String birthDate,
                                     String gender, int height, double weight) {

            // building parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("first_name", firstName));
            params.add(new BasicNameValuePair("last_name", lastName));
            params.add(new BasicNameValuePair("email_address", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("county", county));
            params.add(new BasicNameValuePair("birth_date", birthDate));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("height_cm", Integer.toString(height)));
            params.add(new BasicNameValuePair("weight_kg", Double.toString(weight)));

            // getting JSON object
            JSONObject json = jsonParser.makeHttpRequest(registerURL, "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if(success == 1) {
                    // successfully created cyclist
                    Toast.makeText(getApplicationContext(),"Registration successful!", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), CyclistActivity.class);
                    startActivity(intent);

                    // close screen
                } else {
                    // failed to create product
                    Toast.makeText(getApplicationContext(),"Registration failed!", Toast.LENGTH_LONG);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            registerCyclist(firstName, lastName, email, password,
                    county, birthDate, gender, height, weight);
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    **/
}
