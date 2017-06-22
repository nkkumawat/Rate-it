package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.rateit.data.DBHandler;
import com.example.sonu.rateit.gridView.GridViewAdapter;
import com.example.sonu.rateit.gridView.ImageItem;

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

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    String url ;
    private RecyclerView TeachersData;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private teachersAdapter mAdapter;
    private ProgressDialog progress;
    DBHandler db;
    TextView userName , userEmail , branchTextView;
    String myDepartment = "computer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        url = "http://nkkumawat.me/rateit/selectData.php?department=computer";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db = new DBHandler(this);
        int i = db.getUser();
        if(i > 0) {
            userName.setText(db.getDataName(1));
            userEmail.setText(db.getEmail(1));
            myDepartment = db.getDataDepartment(1);
            branchTextView.setText(myDepartment);
            url = "http://nkkumawat.me/rateit/selectData.php?department="+myDepartment;
        }


        gridView = (GridView) findViewById(R.id.gridView);

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

                MainActivity.this.runOnUiThread(new Runnable() {
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
        final ArrayList<ImageItem> data = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(result);
            int ii = jArray.length();
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);



                ImageItem imageItem = new ImageItem();
                imageItem.image = json_data.getString("picture");
                imageItem.title = json_data.getString("name");

                data.add(imageItem);
//                Toast.makeText(getApplicationContext() , teachers.nameOfTeacher + teachers.department + teachers.phone , Toast.LENGTH_LONG).show();
            }
//             Setup and Handover data to recyclerview
            progress.dismiss();
            gridAdapter = new GridViewAdapter(this, R.layout.grid_layout, data);
            gridView.setAdapter(gridAdapter);

        }
        catch (JSONException e) {
            progress.dismiss();
            Toast.makeText(MainActivity.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

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
        startActivity(new Intent(MainActivity.this , login.class));
    }



    public void startIntentActivity(String department) {
        Intent intent = new Intent(MainActivity.this , departmentwiseSort.class);
        intent.putExtra("url" , department);
        startActivity(intent);
    }

}
