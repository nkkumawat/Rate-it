package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.rateit.data.DBHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String url ;
    String urlHalf ;
    private RecyclerView TeachersData;
    private teachersAdapter mAdapter;
    private ProgressDialog progress;
    ImageView profilePic;
    DBHandler db;
    TextView userName , userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.setCancelable(false);



//        Picasso.with(home.this).load(R.drawable.avatar).into(profilePic);

        url = "http://nkkumawat.me/rateit/selectData.php";
        urlHalf = "http://nkkumawat.me/rateit/sortByDepartment.php";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);


        userName = (TextView)headerView.findViewById(R.id.userName);
        userEmail = (TextView)headerView.findViewById(R.id.userEmail);
        db = new DBHandler(this);
        int i = db.getUser();
        if(i > 0) {
            userName.setText(db.getDataName(1));
            userEmail.setText(db.getEmail(1));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.computer) {
            startIntentActivity("computer");
        }else if (id == R.id.information) {
            startIntentActivity("computer");
        } else if (id == R.id.electical) {
            startIntentActivity("electrical");
        } else if (id == R.id.electronics) {
            startIntentActivity("electronics");
        } else if (id == R.id.civil) {
            startIntentActivity("civil-comming soon...");
        } else if (id == R.id.mechenical) {
            startIntentActivity("mechenical");
        } else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        db.deleteAll();
        startActivity(new Intent(home.this , login.class));
    }



    public void startIntentActivity(String department) {
        Intent intent = new Intent(home.this , departmentwiseSort.class);
        intent.putExtra("url" , department);
        startActivity(intent);
    }




    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();
        progress.show();
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

                home.this.runOnUiThread(new Runnable() {
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
        List<teachersData> data=new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(result);
            int ii = jArray.length();
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                teachersData teachers = new teachersData();
                teachers.id= json_data.getString("id");
                teachers.department= json_data.getString("department");
                teachers.nameOfTeacher= json_data.getString("name");
                teachers.qualification= json_data.getString("qualification");
                teachers.email= json_data.getString("email");
                teachers.phone= json_data.getString("phone");
                teachers.about= json_data.getString("about");
                teachers.experience= json_data.getString("experience");
                teachers.points= json_data.getString("points");
                teachers.picture= json_data.getString("picture");
                data.add(teachers);
//                Toast.makeText(getApplicationContext() , teachers.nameOfTeacher + teachers.department + teachers.phone , Toast.LENGTH_LONG).show();
            }
//             Setup and Handover data to recyclerview
            progress.dismiss();
            TeachersData = (RecyclerView)findViewById(R.id.fishPriceList);
            mAdapter = new teachersAdapter(getApplicationContext(), data);
            TeachersData.setAdapter(mAdapter);
            TeachersData.setLayoutManager(new LinearLayoutManager(home.this));

        }
        catch (JSONException e) {
            progress.dismiss();
            Toast.makeText(home.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//                        .setAction("Action", null).show();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
