package smile.khaled.mohamed.rehab.service.requests.both;

import com.google.gson.annotations.SerializedName;

public class SignInData {
    public SignInData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SerializedName("username") String username;
    @SerializedName("password") String password;

}