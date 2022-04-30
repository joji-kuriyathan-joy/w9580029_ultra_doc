package uk.ac.tees.aad.w9580029_ultra_doc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFViewer extends AppCompatActivity {

    String filePath;
    PDFView pdfView;
    File pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_viewer);
        pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        filePath = intent.getExtras().getString("fileData");

        pdfFile = new File(filePath);
        pdfView.fromFile(pdfFile).load();

    }


}