package com.example.akash.ebooksfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String adminurl="https://raw.githubusercontent.com/dominicsharma98/coupons/admin/admins.json";
    String id;

    ProgressBar progressBar;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);

         id= pref.getString("keyemail",null);
        if(id==null) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent in = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(in);

                }
            }, 5000);
        }
        else {

            getadmins();
            progressBar=findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);






        }
    }

    private void getadmins() {

         request = new StringRequest(Request.Method.GET, adminurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("admins");
                    TextView textView;
                    textView=findViewById(R.id.mail);
                    int flag=0;

                    for (int i= 0; i< array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        String admin = object.getString("email");


                        if(id.equals(admin))
                        {
                           flag=1;

                        }


                    }
                    if(flag==1){
                        textView.setText("Admin");
                    }
                    else {
                        textView.setText("user");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);



    }


}
