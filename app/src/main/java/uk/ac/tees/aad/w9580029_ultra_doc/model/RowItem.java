package uk.ac.tees.aad.w9580029_ultra_doc.model;

import java.io.File;

public class RowItem {

    private String fileName;
    private File fileData;

    public RowItem(String fileName, File fileLocation) {
        this.fileName = fileName;
        this.fileData = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }
    public File getFileData() { return  fileData; };
}
