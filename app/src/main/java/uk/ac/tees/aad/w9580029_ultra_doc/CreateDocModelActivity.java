package uk.ac.tees.aad.w9580029_ultra_doc;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class CreateDocModelActivity extends AppCompatActivity {
    @VisibleForTesting
    public ProgressBar mProgressBarCD;

    public String getCurCameraPhotoPath() {
        return curCameraPhotoPath;
    }

    public void setCurCameraPhotoPath(String curCameraPhotoPath) {
        this.curCameraPhotoPath = curCameraPhotoPath;
    }

    public String curCameraPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void setProgressBarCD(int resId) {
        mProgressBarCD = findViewById(resId);
    }

    public void showProgressBarCD() {
        if (mProgressBarCD != null) {
            mProgressBarCD.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBarCD() {
        if (mProgressBarCD != null) {
            mProgressBarCD.setVisibility(View.INVISIBLE);
        }
    }


}