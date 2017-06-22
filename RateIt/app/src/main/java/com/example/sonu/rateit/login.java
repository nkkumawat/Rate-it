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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

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

    String email , password , url;
    String name , department  , picture;
    EditText emailEd , passEd;
    int status;
    private ProgressDialog progress;
    DBHandler db;
    SignInButton signInButton;
    int RC_SIGN_IN = 1;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, null /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
        db = new DBHandler(this);
        int i = db.getUser();
        if(i > 0) {
            Intent intent = new Intent(getApplicationContext() , gridviewNavigationDrawer.class);
            intent.putExtra("email" , db.getEmail(1));
            intent.putExtra("pass" , db.getDataPass(1));
            startActivity(intent);
            db.close();
            finish();
        }
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

                login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Intent intent = new Intent(login.this , departmentInsert.class);
                        intent.putExtra("name" , name);
                        intent.putExtra("email" , email);
                        intent.putExtra("picture" , picture);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
//    public void logMeIn(String result) {
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            status = jsonObject.getInt("id");
//            if(status >= 1) {
//                name = jsonObject.getString("name");
//                department = jsonObject.getString("department");
//                picture = jsonObject.getString("picture");
//
//
//                db.insert(email, password, name, department, picture);
//                Intent intent = new Intent(getApplicationContext(), gridviewNavigationDrawer.class);
//                intent.putExtra("email", db.getEmail(1));
//                intent.putExtra("pass", db.getDataPass(1));
//                startActivity(intent);
//                finish();
//            }
//            else {
//                Toast.makeText(login.this , "Password not match" , Toast.LENGTH_LONG).show();
//            }
//            progress.dismiss();
//        }
//        catch (JSONException e) {
//            progress.dismiss();
//            Toast.makeText(login.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }
    public void insertToDataBase() {

        url = "http://nkkumawat.me/rateit/signupNameEmailPic.php?email="+email+"&name="+name+"&picture="+picture;
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void signIn() {
        progress.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            name = acct.getDisplayName().toString();
            email = acct.getEmail().toString();
            picture = acct.getPhotoUrl().toString();
            insertToDataBase();
        } else {
            progress.dismiss();
            Toast.makeText(login.this , "Error , try again." + result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
