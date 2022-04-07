package uk.ac.tees.aad.w9580029_ultra_doc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateDocActivity extends CreateDocModelActivity implements  View.OnClickListener {

    private RecyclerView mCreateDocContainer ;
    private TextView mDocName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc);

        //views
        mCreateDocContainer = findViewById(R.id.doc_designer);
        mDocName = findViewById(R.id.ud_doc_name);

        //Button Click
        findViewById(R.id.title_pal).setOnClickListener(this);
        findViewById(R.id.image_pal).setOnClickListener(this);
        findViewById(R.id.contact_pal).setOnClickListener(this);
        findViewById(R.id.location_pal).setOnClickListener(this);
        findViewById(R.id.calender_pal).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}