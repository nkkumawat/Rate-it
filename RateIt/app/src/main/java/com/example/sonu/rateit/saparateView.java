package com.example.sonu.rateit;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

//import okhttp3.Cache;
import okhttp3.Cache;
//import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class saparateView extends AppCompatActivity {
    TextView id1, name1 , phone1 , about1 , experience1 , email1 , points1, department1 , qualification1;
    ImageView picture1;
    String experience , phone , about  , url , id , name , qualification , email , points , department , picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saparate_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

         id = bundle.getString("id");
         name = bundle.getString("name");
//        String phone = bundle.getString("phone");
//        String about = bundle.getString("about");
//        String experience = bundle.getString("experience");
         qualification = bundle.getString("qualification");
         email = bundle.getString("email");
         points = bundle.getString("points");
         department = bundle.getString("department");
//        String picture = bundle.getString("picture");

        id1 = (TextView)findViewById(R.id.id1);
        id1 = (TextView)findViewById(R.id.id1);
        name1 = (TextView)findViewById(R.id.teacherName1);
        phone1 = (TextView)findViewById(R.id.phoneNo1);
        about1 = (TextView)findViewById(R.id.about1);
        qualification1 = (TextView)findViewById(R.id.qualification1);
        experience1 = (TextView)findViewById(R.id.experience1);
        email1 = (TextView)findViewById(R.id.email1);
        points1 = (TextView)findViewById(R.id.points1);
        department1 = (TextView)findViewById(R.id.department1);


        picture1 = (ImageView)findViewById(R.id.profilePic1);


        url = "http://nkkumawat.me/rateit/selectsingleData.php?id="+id;
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                saparateView.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext() , myResponse , Toast.LENGTH_LONG).show();
//                        setAdapter(myResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            experience = jsonObject.getString("experience");
                            about = jsonObject.getString("about");
                            phone = jsonObject.getString("phone");
                            //name = jsonObject.getString("name");
//                            department= jsonObject.getString("department");
//                            qualification= jsonObject.getString("qualification");
//                            email= jsonObject.getString("email");
                            points= jsonObject.getString("points");
                            picture= jsonObject.getString("picture");
                            setAlltext();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }
    public void setAlltext() {
        id1.setText(id);
        name1.setText(name);
        phone1.setText(phone);
        about1.setText(about);
        qualification1.setText(qualification);
        experience1.setText(experience);
        email1.setText(email);
        points1.setText(points);
        department1.setText(department);
        Picasso.with(getApplicationContext())
                .load(picture)
                .placeholder(R.drawable.loading)
                .error(R.drawable.placeholder)
                .into(picture1);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rateme: {
                Intent intent = new Intent(getApplicationContext() , rateTeacher.class);
                intent.putExtra("id" , id);
                startActivity(intent);
//                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
