package com.example.varun.androidphptutorial;

/**
 * Created by varun on 09/11/14.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RetrieveFromSQL extends Activity {

    String id;
    String name;
    InputStream is=null;
    String result=null;
    String line=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        final EditText e_id=(EditText) findViewById(R.id.editText1);
        Button select=(Button) findViewById(R.id.button1);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                id=e_id.getText().toString();
                new RetrieveData().execute("http://varunmishra.com/android-php-tutorial/select.php");
            }
        });
    }

    class RetrieveData extends AsyncTask<String, Void, String> {



        protected String doInBackground(String... urls) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id",id));

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urls[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");

            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
                return "Invalid IP Address";            }

            try
            {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", "connection success ");
            }
            catch(Exception e)
            {
                return e.toString();
            }

            try
            {
                JSONObject json_data = new JSONObject(result);
                name=(json_data.getString("name"));
                return "Name : "+name;
            }
            catch(Exception e)
            {
                return e.toString();
            }
        }

        protected void onPostExecute(String result) {
           Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
}