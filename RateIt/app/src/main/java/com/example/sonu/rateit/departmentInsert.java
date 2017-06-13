package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sonu.rateit.data.DBHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class departmentInsert extends AppCompatActivity {
    Spinner spinner;
    String name , department , email , picture;
    Button saveDepartment;
    DBHandler db;
    String url;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_insert);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        getSupportActionBar().hide();

        spinner = (Spinner) findViewById(R.id.department);
        List<String> list = new ArrayList<String>();
        list.add("computer");
        list.add("electronics");
        list.add("electrical");
        list.add("mechenical");
        list.add("civil");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        db = new DBHandler(this);
        saveDepartment = (Button)findViewById(R.id.saveDepartment);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            picture = bundle.getString("picture");
        }
        saveDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                department = String.valueOf(spinner.getSelectedItem());
                String password = "null";
                db.insert(email, password, name, department, picture);
                url = "http://nkkumawat.me/rateit/saveDepartment.php?email="+email+"&name="+name+"&department="+department;
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                departmentInsert.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Intent intent = new Intent(getApplicationContext(), gridviewNavigationDrawer.class);
                        intent.putExtra("email", db.getEmail(1));
                        intent.putExtra("name", db.getDataName(1));
                        startActivity(intent);
                        db.close();
                        finish();
                    }
                });
            }
        });
    }
}