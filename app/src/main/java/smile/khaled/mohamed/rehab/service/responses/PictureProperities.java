package smile.khaled.mohamed.rehab.service.responses;

import android.net.Uri;

public class PictureProperities {

    public PictureProperities(String fileName, Uri fileUri) {
        this.fileName = fileName;
        this.fileUri = fileUri;
    }

    private String fileName;
    private Uri fileUri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }
}
