package com.example.sonu.rateit;

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

public class MainActivity extends AppCompatActivity {
    String url ;
    private RecyclerView TeachersData;
    private teachersAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        url = "https://api.github.com/users/codepath";
        url = "http://nkkumawat.me/rateit/selectData.php";
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
            TeachersData = (RecyclerView)findViewById(R.id.fishPriceList);
            mAdapter = new teachersAdapter(getApplicationContext(), data);
            TeachersData.setAdapter(mAdapter);
            TeachersData.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        }
        catch (JSONException e) {
            for(int i = 0 ; i < 5 ; i++)
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

        }

    }

}
