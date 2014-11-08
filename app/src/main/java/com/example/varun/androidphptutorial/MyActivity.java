package com.example.varun.androidphptutorial;

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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends Activity {

    String name;
    String id;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final EditText e_id=(EditText) findViewById(R.id.editText1);
        final EditText e_name=(EditText) findViewById(R.id.editText2);
        Button insert=(Button) findViewById(R.id.button1);
        Button select = (Button) findViewById(R.id.button);
        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                id = e_id.getText().toString();
                name = e_name.getText().toString();

                new SendData().execute("http://varunmishra.com/android-php-tutorial/upload.php");
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getApplicationContext(), RetrieveFromSQL.class);
                startActivity(i);
            }
        });
    }


    class SendData extends AsyncTask<String, Void, Integer> {

        private Exception exception;

        protected Integer doInBackground(String... urls) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("name",name));

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
                return 0;
            }

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
                Log.e("Fail 2", e.toString());
                return 0;
            }

            try
            {
                JSONObject json_data = new JSONObject(result);
                code=(json_data.getInt("code"));

                if(code==1)
                {
                    Log.e("pass 3", "connection success ");
                    return 1;

                }
                else
                {
                    Log.e("pass 3", "connection faillll ");
                   return 0;
                }
            }
            catch(Exception e)
            {
                return 1;
            }
        }

        protected void onPostExecute(Integer result) {
            if(result==1)
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
}