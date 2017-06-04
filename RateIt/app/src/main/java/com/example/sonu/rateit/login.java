package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sonu.rateit.data.DBHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {
    Button login , signup;
    String email , password , url;
    String name , department  , picture;
    EditText emailEd , passEd;
    int status;
    private ProgressDialog progress;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");



        db = new DBHandler(this);
        int i = db.getUser();
        if(i > 0) {
            Intent intent = new Intent(getApplicationContext() , home.class);
            intent.putExtra("email" , db.getEmail(1));
            intent.putExtra("pass" , db.getDataPass(1));
            startActivity(intent);
            finish();
        }

        login = (Button)findViewById(R.id.Login);
        emailEd = (EditText) findViewById(R.id.emailEd);
        passEd = (EditText) findViewById(R.id.passEd);
        signup = (Button)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEd.getText().toString();
                password = passEd.getText().toString();
                url = "http://nkkumawat.me/rateit/login.php?email="+email+"&password="+password;
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this , signup.class));
            }
        });
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

                login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext() , myResponse , Toast.LENGTH_LONG).show();
                    logMeIn(myResponse);

                    }
                });
            }
        });
    }
    public void logMeIn(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getInt("id");
            if(status >= 1) {
                name = jsonObject.getString("name");
                department = jsonObject.getString("department");
                picture = jsonObject.getString("picture");


                    db.insert(email, password , name , department , picture);
                    Intent intent = new Intent(getApplicationContext() , home.class);
                    intent.putExtra("email" , db.getEmail(1));
                    intent.putExtra("pass" , db.getDataPass(1));
                    startActivity(intent);
                    finish();
                    // new dataFetching(this , nk).execute();

            }
            else {
                Toast.makeText(login.this , "Password not match" , Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
        catch (JSONException e) {
            progress.dismiss();
            Toast.makeText(login.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}
