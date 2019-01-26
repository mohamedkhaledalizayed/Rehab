package smile.khaled.mohamed.rehab.service.requests.patient;

import com.google.gson.annotations.SerializedName;

public class DoctorFilter {

    public DoctorFilter(String city, String nationality, String neighborhood, String specialty, String gender, String token){
        this.city = city;
        this.nationality = nationality;
        this.neighborhood = neighborhood;
        this.specialty = specialty;
        this.gender = gender;
        this.token = token;
    }

    @SerializedName("city") String city;
    @SerializedName("nationality") String nationality;
    @SerializedName("neighborhood") String neighborhood;
    @SerializedName("specialty") String specialty;
    @SerializedName("gender") String gender;
    @SerializedName("token") String token;
}
