package com.example.exam2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv_user;
    ProgressDialog pd;
    User itemrow;
    ArrayList<User> itemlist;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_user = (ListView) findViewById(R.id.lv_user);
        pd = new ProgressDialog(this);

        new JsonTask().execute("https://picsum.photos/v2/list");
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }
                //Log.e("Line: ",line);
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            parseJSON(result);
        }

        private void parseJSON(String data){
            itemlist = new ArrayList<>();
            try{
                JSONArray jsonMainNode = new JSONArray(data);

                int jsonArrLength = jsonMainNode.length();

                for(int i=0; i < jsonArrLength; i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String id = jsonChildNode.getString("id");
                    String author = jsonChildNode.getString("author");
                    String width = jsonChildNode.getString("width");
                    String height = jsonChildNode.getString("height");
                    String url = jsonChildNode.getString("url");
                    String download_url = jsonChildNode.getString("download_url");

                    itemrow = new User(id, author, width, height, url, download_url);
                    itemlist.add(itemrow);
                }
                adapter = new UserAdapter(MainActivity.this, R.layout.listview_content, itemlist);
                lv_user.setAdapter(adapter);


            }catch(Exception e){
                Log.i("Message", "Error data" +e.getMessage());
            }
        }
    }

    public void OnClickView(View v){
        final Button n = (Button) v;
        final String array_string[] = n.getTag().toString().split("\\|");

        Intent i = new Intent(MainActivity.this, ImagePreview.class);
        i.putExtra("author", array_string[0]);
        i.putExtra("download_url", array_string[1]);
        i.putExtra("width", array_string[2]);
        i.putExtra("height", array_string[3]);
        startActivity(i);
    }
}