package smile.khaled.mohamed.rehab.service;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import smile.khaled.mohamed.rehab.service.requests.both.SignInData;
import smile.khaled.mohamed.rehab.service.responses.both.cities.CityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.signin.SignInResponse;
import smile.khaled.mohamed.rehab.service.responses.both.signupuser.Response;
import smile.khaled.mohamed.rehab.service.responses.both.specialty.SpecialtyResponse;

public interface ServiceApi {

    @POST("login-user.php")
    @FormUrlEncoded
    Call<SignInResponse> signInAsPatientApi(@FieldMap Map<String,String> map);

    @POST("user.php")
    Call<Response> signInAsDoctorApi(@Body SignInData data);

    @POST("register-doctor.php")
    @Multipart
    Call<ResponseBody> signUpDoctorApi(@Part("name") RequestBody name,
                                       @Part("username") RequestBody username,
                                       @Part("password") RequestBody password,
                                       @Part("gender") RequestBody gender,
                                       @Part("mobile") RequestBody mobile,
                                       @Part("email") RequestBody email,
                                       @Part("city") RequestBody city,
                                       @Part("neighborhood") RequestBody neighborhood,
                                       @Part List<MultipartBody.Part> files);

    @POST("cities.php")
    Call<CityResponse> cityApi();

    @POST("specialty.php")
    Call<SpecialtyResponse> specialtyApi();

}
