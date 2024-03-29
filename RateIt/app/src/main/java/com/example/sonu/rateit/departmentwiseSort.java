package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

public class departmentwiseSort extends AppCompatActivity {
    String url , urlHalf;
    private RecyclerView TeachersData;
    private teachersAdapter mAdapter;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmentwise_sort);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        urlHalf = "http://nkkumawat.me/rateit/sortByDepartment.php";

        getSupportActionBar().setTitle(url.toUpperCase());
        url = urlHalf+"?department="+url;
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.setCancelable(false);
        try {
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

                departmentwiseSort.this.runOnUiThread(new Runnable() {
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
            TeachersData.setLayoutManager(new LinearLayoutManager(departmentwiseSort.this));

        }
        catch (JSONException e) {
            progress.dismiss();
            Toast.makeText(departmentwiseSort.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
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
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
