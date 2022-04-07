package uk.ac.tees.aad.w9580029_ultra_doc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    //BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if user is already logged in then continue to the List Doc Activity

        //if the user is Signed Out or New to the app the show the Login Activity
        Intent i;
        i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

 }