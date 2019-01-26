package smile.khaled.mohamed.rehab.service;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import smile.khaled.mohamed.rehab.service.requests.both.SignInData;
import smile.khaled.mohamed.rehab.service.requests.patient.DoctorFilter;
import smile.khaled.mohamed.rehab.service.responses.EvaluationResponse;
import smile.khaled.mohamed.rehab.service.responses.both.activateaccount.ActivateAccountResponse;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.AllMessagesResponse;
import smile.khaled.mohamed.rehab.service.responses.both.cities.CityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.logindoctor.LoginDoctorResponse;
import smile.khaled.mohamed.rehab.service.responses.both.message.SendMessageResponse;
import smile.khaled.mohamed.rehab.service.responses.both.nationality.NationalityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.neighborhood.NeighborhoodResponse;
import smile.khaled.mohamed.rehab.service.responses.both.registeruser.ReigsterUserResponse;
import smile.khaled.mohamed.rehab.service.responses.both.signin.SignInResponse;
import smile.khaled.mohamed.rehab.service.responses.both.signupuser.Response;
import smile.khaled.mohamed.rehab.service.responses.both.specialty.SpecialtyResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.AddFavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.DeleteDateResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.DeleteFavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.dates.PatientDatesResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorfilter.DoctorFilterResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorprofile.DoctorDataResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.favourites.FavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.reviews.ReviewsResponse;

public interface ServiceApi {

    @POST("register-user")
    @FormUrlEncoded
    Call<ReigsterUserResponse> signUpAsPatientApi(@FieldMap Map<String,String> map);

    @POST("check-validate")
    @FormUrlEncoded
    Call<ActivateAccountResponse> activateAccountApi(@FieldMap Map<String,String> map);

    @POST("login-user.php")
    @FormUrlEncoded
    Call<SignInResponse> signInAsPatientApi(@FieldMap Map<String,String> map);

    @POST("login-doctor")
    @FormUrlEncoded
    Call<LoginDoctorResponse> signInAsDoctorApi(@FieldMap Map<String,String> map);

    @POST("register-doctor.php")
    @Multipart
    Call<ReigsterUserResponse> signUpDoctorApi(@Part("name") RequestBody name,
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

    @POST("countries")
    @FormUrlEncoded
    Call<NationalityResponse> nationality(@FieldMap Map<String,String> map);

    @POST("neighborhood")
    @FormUrlEncoded
    Call<NeighborhoodResponse> neighborhood(@FieldMap Map<String,String> map);

    @POST("doctor")
    @FormUrlEncoded
    Call<DoctorFilterResponse> doctorFilter(@FieldMap Map<String,String> map);

    @POST("favorites")
    @FormUrlEncoded
    Call<FavouriteResponse> getFavourites(@FieldMap Map<String,String> map);

    @POST("favorites")
    @FormUrlEncoded
    Call<AddFavouriteResponse> addFavourites(@FieldMap Map<String,String> map);

    @POST("favorites")
    @FormUrlEncoded
    Call<DeleteFavouriteResponse> deleteFavourites(@FieldMap Map<String,String> map);

    @POST("evaluation")
    @FormUrlEncoded
    Call<ReviewsResponse> getDoctorReviews(@FieldMap Map<String,String> map);

    @POST("patient_reservation")
    @FormUrlEncoded
    Call<PatientDatesResponse> getPatientDates(@FieldMap Map<String,String> map);

    @POST("patient_reservation")
    @FormUrlEncoded
    Call<DeleteDateResponse> deteteDate(@FieldMap Map<String,String> map);

    @POST("evaluation")
    @FormUrlEncoded
    Call<EvaluationResponse> setRateForDoctor(@FieldMap Map<String,String> map);

    @POST("message")
    @FormUrlEncoded
    Call<SendMessageResponse> sendMessage(@FieldMap Map<String,String> map);

    @POST("message")
    @FormUrlEncoded
    Call<AllMessagesResponse> getAllMessages(@FieldMap Map<String,String> map);

    @POST("doctor")
    @FormUrlEncoded
    Call<DoctorDataResponse> getDoctorData(@FieldMap Map<String,String> map);
}
