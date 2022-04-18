package uk.ac.tees.aad.w9580029_ultra_doc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import uk.ac.tees.aad.w9580029_ultra_doc.model.RecyclerModel;

public class CreateDocActivity extends  CreateDocModelActivity implements  View.OnClickListener {

     private RecyclerView mCreateDocContainer ;
    private LinearLayoutManager layoutManager;
    private static List<RecyclerModel> recyclerModelList;
    private DesignerAdapter adapter;

    private TextView mDocName;
    private static String cur_doc_id;
    private static HashMap<String, String> doc_map = new HashMap<String, String>();
    //this data structure is used to get the order of the items added
    private static Map<Integer, HashMap> items_map = new TreeMap<Integer, HashMap>();
    private static int item_count = 0;
    private static String calander_position = "";

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc);
        setProgressBarCD(R.id.createDocProgressBar);

        context = this;
        //views
        mDocName = findViewById(R.id.ud_doc_name);

        mCreateDocContainer = findViewById(R.id.doc_designerr);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mCreateDocContainer.setLayoutManager(layoutManager);
        recyclerModelList = new ArrayList<>();

        //Button Click
        findViewById(R.id.title_pal).setOnClickListener(this);
        findViewById(R.id.image_pal).setOnClickListener(this);
        findViewById(R.id.contact_pal).setOnClickListener(this);
        findViewById(R.id.location_pal).setOnClickListener(this);
        findViewById(R.id.calender_pal).setOnClickListener(this);

        //check whether it is a new create doc
        Intent intent = getIntent();
        String isNewDoc = intent.getStringExtra("isNewDoc");

        //New Doc Create a unique Doc_Id
        if(isNewDoc.toLowerCase().equalsIgnoreCase("true")){
             cur_doc_id = generateDocID();
            Log.d("Doc_ID :: ",cur_doc_id);
            doc_map.put("doc_id",cur_doc_id);
        }
        else{
            //User has started creating the doc use the existing doc_id
            Log.d("Existing_Doc",cur_doc_id);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int i = view.getId();

        //clicked title
        if (i == R.id.title_pal) {
            Log.d("Pallet_Clicked","Title Button clicked");
            AlertDialog.Builder title_popup_builder;
            AlertDialog title_dialog;
             EditText title_desc_textView;
             Button add_title_btn, cancel_title_btn;

            title_popup_builder = new AlertDialog.Builder(this);
            final View titlePopupView = getLayoutInflater().inflate(R.layout.title_info,null);

            title_desc_textView = (EditText) titlePopupView.findViewById(R.id.title_desc);
            add_title_btn = (Button)  titlePopupView.findViewById(R.id.title_save);
            cancel_title_btn =(Button) titlePopupView.findViewById(R.id.title_cancel);

            title_popup_builder.setView(titlePopupView);
            title_dialog = title_popup_builder.create();
            title_dialog.show();

            add_title_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerModelList.add(new RecyclerModel(title_desc_textView.getText().toString()));
                    adapter = new DesignerAdapter(recyclerModelList);

                    mCreateDocContainer.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    title_dialog.dismiss();
                }
            });


            cancel_title_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title_dialog.dismiss();
                }
            });

        }
        else if (i == R.id.image_pal){
            Log.d("Pallet_Clicked","Image Button clicked");
        }
        else if (i== R.id.contact_pal){
            Log.d("Pallet_Clicked","Contact Button clicked");
        }
        else if (i == R.id.location_pal){
            Log.d("Pallet_Clicked","Location Button clicked");
        }
        else if(i == R.id.calender_pal){
            Log.d("Pallet_Clicked","calender Button clicked");

            AlertDialog.Builder calenderDialogBuilder;
            AlertDialog calanderDialog;

            calenderDialogBuilder = new AlertDialog.Builder(this);
            final View calenderPopupView = getLayoutInflater().inflate(R.layout.calander_picker,null);
            //Calender controls declare
            DatePickerDialog datePickerDialog;
            DatePicker datePicker1;
            Button add_date, cancel_date;
            TextView cur_dateTextview = (TextView)calenderPopupView.findViewById(R.id.getDateTextView);
            datePicker1 = (DatePicker)calenderPopupView.findViewById(R.id.datePicker1);
            cancel_date = (Button)calenderPopupView.findViewById(R.id.dateCancel);
            add_date = (Button) calenderPopupView.findViewById(R.id.dateAdd);

            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            datePickerDialog = new DatePickerDialog(this,      new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    cur_dateTextview.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                     }

            }, year, month, day);
            datePickerDialog.show();

            calenderDialogBuilder.setView(calenderPopupView);
            calanderDialog = calenderDialogBuilder.create();
            calanderDialog.show();

            add_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cur_dateTextview.setText("Selected Date: "
                            + datePicker1.getDayOfMonth()+"/"+ (datePicker1.getMonth() + 1)
                            +"/"+datePicker1.getYear());
                    String choosed_position = getCalander_position();
                    Toast.makeText(CreateDocActivity.this, "choosed_date: "
                            +cur_dateTextview.getText().toString()+" & Position : "
                            +choosed_position, Toast.LENGTH_SHORT).show();
                    datePickerDialog.dismiss();
                    calanderDialog.dismiss();
                }
            });

            cancel_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.dismiss();
                    calanderDialog.dismiss();
                }
            });


        }
        else{
            Log.d("Unknown_Pallet","Unable to find the pallet clicked");
        }
    }

    public static String generateDocID() {
        String uuid = UUID.randomUUID().toString();
        return "UD_" + uuid.replace("-", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Existing_Doc + pause",cur_doc_id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Existing_Doc + restart",cur_doc_id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Existing_Doc + Stop",cur_doc_id);
    }



    protected void fillItemMap(int item_count, HashMap cur_item_map){
        items_map.put(item_count,cur_item_map);
    }

    public static String getCalander_position() {
        return calander_position;
    }

    public static void setCalander_position(String calander_position) {
        CreateDocActivity.calander_position = calander_position;
    }

    public void onCalenderPositionClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        //by default date is checked left
        calander_position ="left";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.date_center:
                if (checked)
                    calander_position ="center";
                    setCalander_position(calander_position);
                    break;
            case R.id.date_left:
                if (checked)
                    calander_position ="left";
                    setCalander_position(calander_position);
                    break;
            case R.id.date_right:
                if (checked)
                    calander_position ="right";
                    setCalander_position(calander_position);
                    break;
        }

        Toast.makeText(CreateDocActivity.this, calander_position.toString()+"==>> ", Toast.LENGTH_SHORT).show();

    }
}