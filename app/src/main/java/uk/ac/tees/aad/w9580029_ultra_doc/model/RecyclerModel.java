package uk.ac.tees.aad.w9580029_ultra_doc.model;

public class RecyclerModel {
    private String title_description;
    private String current_date;

    public String getCurrent_date() {
        return current_date;
    }
    public String getTitle_description() {
        return title_description;
    }

    public RecyclerModel(String title_description,String current_date) {
        this.title_description = title_description;
        this.current_date = current_date;
    }
}
