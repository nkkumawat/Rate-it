package com.example.sonu.rateit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class rateTeacher extends AppCompatActivity {
    RatingBar rTeaching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);
        rTeaching = (RatingBar) findViewById(R.id.rateTeaching);





        rTeaching.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

//                txtRatingValue.setText(String.valueOf(rating));
                Toast.makeText(getApplicationContext() , String.valueOf(rating), Toast.LENGTH_LONG).show();

            }
        });
    }
}
