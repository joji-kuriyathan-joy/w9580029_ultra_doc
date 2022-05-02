package uk.ac.tees.aad.w9580029_ultra_doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.VisibleForTesting;

public class SplashActivity extends Activity  {
    Handler handler;
    @VisibleForTesting
    public ProgressBar mProgressBarSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setProgressBarSP(R.id.ProgressBarSplash);

        showProgressBarSP();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                hideProgressBarSP();
                finish();

            }
        },3000);

    }
    public void setProgressBarSP(int resId) {
        mProgressBarSP = findViewById(resId);
    }

    public void showProgressBarSP() {
        if (mProgressBarSP != null) {
            mProgressBarSP.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBarSP() {
        if (mProgressBarSP != null) {
            mProgressBarSP.setVisibility(View.INVISIBLE);
        }
    }
}

