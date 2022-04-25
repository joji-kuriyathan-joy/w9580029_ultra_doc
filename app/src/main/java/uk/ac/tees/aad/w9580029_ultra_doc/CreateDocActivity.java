package uk.ac.tees.aad.w9580029_ultra_doc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import uk.ac.tees.aad.w9580029_ultra_doc.model.RecyclerModel;

public class CreateDocActivity extends CreateDocModelActivity implements View.OnClickListener {

    private RecyclerView mCreateDocContainer;
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

    public ImageView imageView;
    // constant to compare
    // the activity result code
    private static final int SELECT_PICTURE = 200;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

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
        if (isNewDoc.toLowerCase().equalsIgnoreCase("true")) {
            cur_doc_id = generateDocID();
            Log.d("Doc_ID :: ", cur_doc_id);
            doc_map.put("doc_id", cur_doc_id);
        } else {
            //User has started creating the doc use the existing doc_id
            Log.d("Existing_Doc", cur_doc_id);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int i = view.getId();

        //clicked title
        if (i == R.id.title_pal) {
            Log.d("Pallet_Clicked", "Title Button clicked");
            AlertDialog.Builder title_popup_builder;
            AlertDialog title_dialog;
            EditText title_desc_textView;
            Button add_title_btn, cancel_title_btn;

            title_popup_builder = new AlertDialog.Builder(this);
            final View titlePopupView = getLayoutInflater().inflate(R.layout.title_info, null);

            title_desc_textView = (EditText) titlePopupView.findViewById(R.id.title_desc);
            add_title_btn = (Button) titlePopupView.findViewById(R.id.title_save);
            cancel_title_btn = (Button) titlePopupView.findViewById(R.id.title_cancel);

            title_popup_builder.setView(titlePopupView);
            title_dialog = title_popup_builder.create();
            title_dialog.setCanceledOnTouchOutside(false);
            title_dialog.show();

            add_title_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DesignerAdapter.setLayoutInflate("title");
                    recyclerModelList.add(new RecyclerModel(title_desc_textView.getText().toString(), ""));
                    adapter = new DesignerAdapter(recyclerModelList);
                    mCreateDocContainer.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mCreateDocContainer.scrollToPosition(recyclerModelList.size() - 1);
                    title_dialog.dismiss();
                }
            });


            cancel_title_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title_dialog.dismiss();
                }
            });

        } else if (i == R.id.image_pal) {
            Log.d("Pallet_Clicked", "Image Button clicked");
            AlertDialog.Builder image_popup_builder;
            AlertDialog image_dialog;

            Button add_image_btn, cancel_image_btn, gallery_btn, camera_btn;

            image_popup_builder = new AlertDialog.Builder(this);
            final View imagePopupView = getLayoutInflater().inflate(R.layout.image_picker, null);

            add_image_btn = (Button) imagePopupView.findViewById(R.id.addImage);
            cancel_image_btn = (Button) imagePopupView.findViewById(R.id.cancelImage);
            gallery_btn = (Button) imagePopupView.findViewById(R.id.imageGallery);
            camera_btn = (Button) imagePopupView.findViewById(R.id.imageCamera);
            imageView = (ImageView) imagePopupView.findViewById(R.id.imagePicker);


            image_popup_builder.setView(imagePopupView);
            image_dialog = image_popup_builder.create();
            image_dialog.setCanceledOnTouchOutside(false);
            image_dialog.show();

            add_image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DesignerAdapter.setLayoutInflate("image");
                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    recyclerModelList.add(new RecyclerModel(bm));
                    adapter = new DesignerAdapter(recyclerModelList);
                    mCreateDocContainer.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mCreateDocContainer.scrollToPosition(recyclerModelList.size() - 1);
                    image_dialog.dismiss();
                }
            });

            cancel_image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    image_dialog.dismiss();
                }
            });

            gallery_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                }
            });

            camera_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {

                        dispatchTakePictureIntent();

                        //------------------ Takes only thumbnail --------------
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            });

        } else if (i == R.id.contact_pal) {
            Log.d("Pallet_Clicked", "Contact Button clicked");
        } else if (i == R.id.location_pal) {
            Log.d("Pallet_Clicked", "Location Button clicked");
        } else if (i == R.id.calender_pal) {
            Log.d("Pallet_Clicked", "calender Button clicked");

            AlertDialog.Builder calenderDialogBuilder;
            AlertDialog calanderDialog;

            calenderDialogBuilder = new AlertDialog.Builder(this);
            final View calenderPopupView = getLayoutInflater().inflate(R.layout.calander_picker, null);
            //Calender controls declare
            DatePickerDialog datePickerDialog;
            DatePicker datePicker1;
            Button add_date, cancel_date;
            TextView cur_dateTextview = (TextView) calenderPopupView.findViewById(R.id.getDateTextView);
            datePicker1 = (DatePicker) calenderPopupView.findViewById(R.id.datePicker1);
            cancel_date = (Button) calenderPopupView.findViewById(R.id.dateCancel);
            add_date = (Button) calenderPopupView.findViewById(R.id.dateAdd);

            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    cur_dateTextview.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }

            }, year, month, day);
            datePickerDialog.show();

            calenderDialogBuilder.setView(calenderPopupView);
            calanderDialog = calenderDialogBuilder.create();
            calanderDialog.setCanceledOnTouchOutside(false);
            calanderDialog.show();

            add_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cur_dateTextview.setText(datePicker1.getDayOfMonth() + "/" + (datePicker1.getMonth() + 1) + "/" + datePicker1.getYear());
                    //get the position to display the date
                    String choosed_position = getCalander_position();
                    DesignerAdapter.setLayoutInflate("calender");
                    if (choosed_position.equalsIgnoreCase("")) {
                        //User has not choose the position so show a toast to choose
                        //any one of the postion
                        Toast.makeText(CreateDocActivity.this, "Warning!\n " +
                                        "Please choose where to position the date on the document",
                                Toast.LENGTH_SHORT).show();

                    } else if (cur_dateTextview.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(CreateDocActivity.this, "Warning!\n " +
                                        "Please choose date.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        recyclerModelList.add(new RecyclerModel("", cur_dateTextview.getText().toString() + "~~" + choosed_position));
                        Log.d("len", "+++++Len recyclerModelList : ++++" + String.valueOf(recyclerModelList.size()));
                        adapter = new DesignerAdapter(recyclerModelList);
                        mCreateDocContainer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mCreateDocContainer.scrollToPosition(recyclerModelList.size() - 1);
                        datePickerDialog.dismiss();
                        calanderDialog.dismiss();
                    }
                }
            });

            cancel_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.dismiss();
                    calanderDialog.dismiss();
                }
            });


        } else {
            Log.d("Unknown_Pallet", "Unable to find the pallet clicked");
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (reqCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        } else if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap photo = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else {
            Toast.makeText(context, "You haven't picked Image", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    public static String generateDocID() {
        String uuid = UUID.randomUUID().toString();
        return "UD_" + uuid.replace("-", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Existing_Doc + pause", cur_doc_id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Existing_Doc + restart", cur_doc_id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Existing_Doc + Stop", cur_doc_id);
    }


    protected void fillItemMap(int item_count, HashMap cur_item_map) {
        items_map.put(item_count, cur_item_map);
    }

    public static String getCalander_position() {
        return calander_position;
    }

    public static void setCalander_position(String calander_position) {
        CreateDocActivity.calander_position = calander_position;
    }

    public void onCalenderPositionClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        //by default date is checked left
        calander_position = "left";
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.date_center:
                if (checked)
                    calander_position = "center";
                setCalander_position(calander_position);
                break;
            case R.id.date_left:
                if (checked)
                    calander_position = "left";
                setCalander_position(calander_position);
                break;
            case R.id.date_right:
                if (checked)
                    calander_position = "right";
                setCalander_position(calander_position);
                break;
        }

    }
}