package smile.khaled.mohamed.rehab.repository.both;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.BaseServiceApi;
import smile.khaled.mohamed.rehab.service.ServiceApi;
import smile.khaled.mohamed.rehab.service.responses.both.signin.SignInResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.activity.DoctorHomeActivity;
import smile.khaled.mohamed.rehab.views.activity.both.SignInActivity;

import static smile.khaled.mohamed.rehab.data.Constants.PATIENT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.USER_STATUS;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;


public class SignInRepository  {

    private ServiceApi service;
    private Application application;
    private LiveData<SignInResponse> responseLiveData;
    public SignInRepository(Application application){
        this.application=application;
        service=BaseServiceApi.getInstance();
    }

    SignInResponse signInResponse;

    public SignInResponse sigiIn(String username,String password){
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        service.signInAsPatientApi(map).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                signInResponse = response.body();
//                if (response.code()==200){
//                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(PATIENT_DATA, response.body().getData().toString()).apply();
//                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_STATUS, "true").apply();
//                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_TYPE, PATIENT).apply();
//                    mView.dismiss();
//                    startActivity(new Intent(SignInActivity.this,DoctorHomeActivity.class));
//                    finish();
//                }else if (response.code()==401){
//                    AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
//                }else if (response.code()==2){
//                    validate();
//                }else {
//
//                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
//                AppUtils.showErrorToast(SignInActivity.this,"Something wrong");
            }
        });

        return signInResponse;
    }
}
