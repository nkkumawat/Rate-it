package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sonu.rateit.data.DBHandler;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    String url1 , url2;
    private RecyclerView pastCommentsData;
    private commentsAdapter mAdapter;
    EditText commentsText;
    Button saveComment;
    String commentsString;
    String NameOfUser;
    DBHandler db;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saparate_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");

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


        commentsText = (EditText) findViewById(R.id.commentsEditTect);
        saveComment = (Button) findViewById(R.id.saveComment);



        db = new DBHandler(this);

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                commentsString = commentsText.getText().toString();
                int i = db.getUser();
                if(i > 0) {
                    NameOfUser = db.getDataName(1);
                }
                commentsText.setText("");
                try {
                    run2();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });










        url = "http://nkkumawat.me/rateit/selectsingleData.php?id="+id;
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            run1();
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
                .fit().centerInside()
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
            run1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void run1() throws IOException {

        OkHttpClient client = new OkHttpClient();
//        progress.show();
//        url1 = "http://nkkumawat.me/rateit/selectsingleData.php?teacherid="+id;
        url1 = "http://nkkumawat.me/rateit/showComments.php?teacherid="+id;
        Request request = new Request.Builder()
                .url(url1)
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
                        setAdapter(myResponse);

                    }
                });
            }
        });
    }

    public void  setAdapter(String result){
        List<commentsData> data=new ArrayList<>();
//        Toast.makeText(saparateView.this , result , Toast.LENGTH_LONG).show();
        try {
            JSONArray jArray = new JSONArray(result);
            int ii = jArray.length();
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                commentsData comments = new commentsData();
                comments.nameOfUser= json_data.getString("name");
                comments.commentText= json_data.getString("comments");

                data.add(comments);
//                Toast.makeText(getApplicationContext() , teachers.nameOfTeacher + teachers.department + teachers.phone , Toast.LENGTH_LONG).show();
            }
//             Setup and Handover data to recyclerview
//            progress.dismiss();
            pastCommentsData = (RecyclerView)findViewById(R.id.pastCommentsRecyclerView);
            mAdapter = new commentsAdapter(getApplicationContext(), data);
            pastCommentsData.setAdapter(mAdapter);
            pastCommentsData.setLayoutManager(new LinearLayoutManager(saparateView.this));

        }
        catch (JSONException e) {
//            progress.dismiss();
//            Toast.makeText(saparateView.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    void run2() throws IOException {

        OkHttpClient client = new OkHttpClient();
//        progress.show();
//        url1 = "http://nkkumawat.me/rateit/selectsingleData.php?teacherid="+id;
        url2 = "http://nkkumawat.me/rateit/saveComments.php?teacherid="+id+"&comments="+commentsString+"&name="+NameOfUser;
        Request request = new Request.Builder()
                .url(url2)
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
                        progress.dismiss();
                        onResume();
//                        Toast.makeText(getApplicationContext() , myResponse , Toast.LENGTH_LONG).show();
//                        setAdapter(myResponse);

                    }
                });
            }
        });
    }

}
