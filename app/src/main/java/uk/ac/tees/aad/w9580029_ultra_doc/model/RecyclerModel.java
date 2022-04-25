package uk.ac.tees.aad.w9580029_ultra_doc.model;

import android.graphics.Bitmap;

public class RecyclerModel {
    private String title_description;
    private String current_date;
    private Bitmap imageUrl;

    public boolean getImageCapture() {
        return isImageCapture;
    }

    public void setImageCapture(boolean imageCapture) {
        isImageCapture = imageCapture;
    }

    private boolean isImageCapture = false;

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

    public RecyclerModel(Bitmap imageUrl) {
        this.imageUrl = imageUrl;
        setImageCapture(true);

    }
}
