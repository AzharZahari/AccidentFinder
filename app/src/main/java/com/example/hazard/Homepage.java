package com.example.hazard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Homepage extends AppCompatActivity implements View.OnClickListener {

    ImageView logout;
    GoogleSignInClient mGoogleSignInClient;
    String name;
    String email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        TextView tvName = (TextView) findViewById(R.id.tvname);
        TextView tvEmail = (TextView) findViewById(R.id.tvemail);

        String name = getIntent().getStringExtra("Name");
        String email = getIntent().getStringExtra("Email");

        tvName.setText(name);
        tvEmail.setText(email);

        Button logout = findViewById(R.id.button2);
        logout.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }
        });
        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Accident.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){


            case R.id.button2:
                signOut();
                break;
        }

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "User Signed Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.abouts:{
                Intent intent = new Intent(this, about.class);
                startActivity(intent);
            }break;

            case R.id.home:{
                Intent intent = new Intent(this, Homepage.class);
                startActivity(intent);
            }break;

            case R.id.mapst:{
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}


