package com.example.sonu.rateit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class signup extends AppCompatActivity {
    String[] departmentsAdapter = { "computer","electronics","electrical","mechnical","civil" };
    Spinner spinner;
    String url, name , email , department , password;
    EditText NameOfUser , EmailOfUser , PasswordOfUser;
    Button signup , login;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");

        spinner = (Spinner) findViewById(R.id.department);
        List<String> list = new ArrayList<String>();
        list.add("computer");
        list.add("electronics");
        list.add("electrical");
        list.add("mechnical");
        list.add("civil");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);




        NameOfUser = (EditText) findViewById(R.id.name);
        EmailOfUser = (EditText) findViewById(R.id.email);
        PasswordOfUser = (EditText) findViewById(R.id.passwordSignup);

        signup = (Button) findViewById(R.id.signUpMe);
        login = (Button) findViewById(R.id.loginMe);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                name = NameOfUser.getText().toString();
                email = EmailOfUser.getText().toString();
                password = PasswordOfUser.getText().toString();
                department = String.valueOf(spinner.getSelectedItem());
                if(name.length() != 0 && email.length() != 0 && password.length() != 0 && department.length() != 0) {
                    progress.show();
                    url = "http://nkkumawat.me/rateit/signup.php?email="+email+"&name="+name+"&department="+department+"&password="+password;
                    try {
                        run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(signup.this , "Fill all entries \n" , Toast.LENGTH_LONG).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this , login.class));
                finish();
            }
        });

    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();
//        progress.show();
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

                signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            progress.dismiss();
                            startActivity(new Intent(signup.this , login.class));
//                        Toast.makeText(getApplicationContext() , myResponse , Toast.LENGTH_LONG).show();
//                        logMeIn(myResponse);

                    }
                });
            }
        });
    }
}
