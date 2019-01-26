package smile.khaled.mohamed.rehab.service.requests.patient;

import com.google.gson.annotations.SerializedName;

public class DocotorFavourite {

    public DocotorFavourite(String token, String type){
        this.token = token;
        this.type = type;
    }

    @SerializedName("token") String token;
    @SerializedName("type") String type;
}
