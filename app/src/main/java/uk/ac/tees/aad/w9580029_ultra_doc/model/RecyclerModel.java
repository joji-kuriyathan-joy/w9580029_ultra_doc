package uk.ac.tees.aad.w9580029_ultra_doc.model;

import android.graphics.Bitmap;

public class RecyclerModel {
    private String title_description;
    private String current_date;
    private Bitmap imageUrl;
    private Bitmap locationURL;
    private boolean isImageCapture = false;

    public boolean getLocationCapture() {
        return isLocationCapture;
    }

    public void setLocationCapture(boolean locationCapture) {
        isLocationCapture = locationCapture;
    }

    private boolean isLocationCapture = false;

    public boolean getImageCapture() {
        return isImageCapture;
    }

    public void setImageCapture(boolean imageCapture) {
        isImageCapture = imageCapture;
    }



    public Bitmap getImageUrl() {
        return imageUrl;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public String getTitle_description() {
        return title_description;
    }

    public RecyclerModel(String title_description, String current_date) {
        this.title_description = title_description;
        this.current_date = current_date;

    }

    public RecyclerModel(Bitmap imageUrl,Bitmap locationURL) {
        this.imageUrl = imageUrl;
        this.locationURL = locationURL;
        if(imageUrl != null) {
            setImageCapture(true);
        }
        if(locationURL != null){
            setLocationCapture(true);
        }

    }

    public Bitmap getLocationURL() {
        return locationURL;
    }


}
