package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.rateit.data.DBHandler;
import com.example.sonu.rateit.gridView.GridViewAdapter;
import com.example.sonu.rateit.gridView.ImageItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class gridviewNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String url ;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ProgressDialog progress;
    DBHandler db;
    TextView userName , userEmail , branchTextView;
    String myDepartment = "computer";
    ImageView profilePic;
    String PassingName , passingEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null) {
//            PassingName = bundle.getString("name");
//            passingEmail = bundle.getString("email");
//        }



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
        userName = (TextView)headerView.findViewById(R.id.userName1);
        userEmail = (TextView)headerView.findViewById(R.id.userEmail1);
        branchTextView = (TextView) headerView.findViewById(R.id.branchTextView1);
        profilePic = (ImageView) headerView.findViewById(R.id.profilePic1);

        db = new DBHandler(this);
        int i = db.getUser();
        if(i > 0) {
            userName.setText(db.getDataName(1));
            userEmail.setText(db.getEmail(1));
            myDepartment = db.getDataDepartment(1);
            branchTextView.setText(myDepartment);
            Picasso.with(this)
                    .load(db.getDataPicture(1))
                    .fit().centerInside()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.placeholder)
                    .into(profilePic);
            url = "http://nkkumawat.me/rateit/selectData.php?department="+myDepartment;
        }
        gridView = (GridView) findViewById(R.id.gridView);
        try {
//            Toast.makeText(gridviewNavigationDrawer.this , url , Toast.LENGTH_LONG).show();
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                gridviewNavigationDrawer.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                imageItem.id= json_data.getString("id");
                imageItem.department= json_data.getString("department");
                imageItem.teacherName= json_data.getString("name");
                imageItem.qualification= json_data.getString("qualification");
                imageItem.email= json_data.getString("email");
                imageItem.phone= json_data.getString("phone");
                imageItem.about= json_data.getString("about");
                imageItem.experience= json_data.getString("experience");
                imageItem.points= json_data.getString("points");
                data.add(imageItem);
            }
            progress.dismiss();
            gridAdapter = new GridViewAdapter(this, R.layout.grid_layout, data);
            gridView.setAdapter(gridAdapter);
        }
        catch (JSONException e) {
            progress.dismiss();
            Toast.makeText(gridviewNavigationDrawer.this, "Loading Error...", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gridview_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.listView) {
            startActivity(new Intent(gridviewNavigationDrawer.this , home.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.computer) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=computer";
            onResume();
        }else if (id == R.id.information) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=computer";
            onResume();
        } else if (id == R.id.electical) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=electrical";
            onResume();
        } else if (id == R.id.electronics) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=electronics";
            onResume();
        } else if (id == R.id.civil) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=electronics";
            onResume();
        } else if (id == R.id.mechenical) {
            url = "http://nkkumawat.me/rateit/selectData.php?department=mechenical";
            onResume();
        } else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logout() {
        db.deleteAll();
        startActivity(new Intent(gridviewNavigationDrawer.this , login.class));
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
