package com.example.sonu.rateit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class rateTeacher extends AppCompatActivity {
    RatingBar rTeaching1,rTeaching2,rTeaching3,rTeaching4,rTeaching5;
    public float rating1 = 0 , rating2 = 0, rating3 =0 , rating4 = 0, rating5 = 0;
    Button rateButton;
    public float resultant= 0;
    String id;
    public String url = "http://nkkumawat.me/rateit/ratethenumber.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        id = bundle.getString("id");


        rTeaching1 = (RatingBar) findViewById(R.id.rateTeaching1);
        rTeaching2 = (RatingBar) findViewById(R.id.rateTeaching2);
        rTeaching3 = (RatingBar) findViewById(R.id.rateTeaching3);
        rTeaching4 = (RatingBar) findViewById(R.id.rateTeaching4);
        rTeaching5 = (RatingBar) findViewById(R.id.rateTeaching5);
        rateButton = (Button) findViewById(R.id.rateButton);

        rTeaching1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating1 = rating;
            }
        });
        rTeaching2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating2 = rating;
            }
        });
        rTeaching3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating3 = rating;
            }
        });
        rTeaching4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating4 = rating;
            }
        });
        rTeaching5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating5 = rating;
            }
        });
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultant = rating1 + rating2 + rating3 + rating4 + rating5;
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void run() throws IOException {
        url = "http://nkkumawat.me/rateit/ratethenumber.php?id="+id+"&&rating="+resultant;
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

                rateTeacher.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

            }
        });
    }
}
