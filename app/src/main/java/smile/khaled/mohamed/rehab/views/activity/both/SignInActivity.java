package smile.khaled.mohamed.rehab.views.activity.both;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.ActivitySignInBinding;
import smile.khaled.mohamed.rehab.service.requests.both.SignInData;
import smile.khaled.mohamed.rehab.service.responses.both.logindoctor.LoginDoctorResponse;
import smile.khaled.mohamed.rehab.service.responses.both.signin.Data;
import smile.khaled.mohamed.rehab.service.responses.both.signin.SignInResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.ValidateUtils;
import smile.khaled.mohamed.rehab.viewmodel.both.SignInViewModel;
import smile.khaled.mohamed.rehab.views.activity.AccountTypeActivity;
import smile.khaled.mohamed.rehab.views.activity.BaseActivity;
import smile.khaled.mohamed.rehab.views.activity.DoctorHomeActivity;
import smile.khaled.mohamed.rehab.views.activity.PatientHomeActivity;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.USER_STATUS;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;

public class SignInActivity extends BaseActivity {


    private SignInViewModel viewModel;
    private int accountType=1;
    private ActivitySignInBinding binding;
    private CatLoadingView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

         AppUtils.setHtmlText(R.string.create_account,binding.createNewAccount);


    }


    public void createNewAccount(View view) {
        startActivity(new Intent(this,AccountTypeActivity.class));
    }

    public void signInPatientAccount(View view) {
        accountType=PATIENT_ACCOUNT;
        binding.signInAsPatient.setBackgroundResource(R.drawable.signin_bg_btn);
        binding.signInAsDoctor.setBackgroundResource(R.drawable.signin_border_btn);
    }

    public void signInDoctorAccount(View view) {
        accountType=DOCTOR_ACCOUNT;
        binding.signInAsDoctor.setBackgroundResource(R.drawable.signin_bg_btn);
        binding.signInAsPatient.setBackgroundResource(R.drawable.signin_border_btn);
    }

    public void signIn(View view) {
        final String username=AppUtils.getTextContent(binding.signInUsername);
        final String password=AppUtils.getTextContent(binding.signInPassword);

        if (!AppUtils.isInternetAvailable(this)) {
            AppUtils.showErrorToast(this,"Check your internet connection");
            return;
        }

        if (ValidateUtils.missingInputs(username, password)) {
            AppUtils.showErrorToast(this,"Check your username and password");
            return;
        }

//        if (!ValidateUtils.validPassword(password)) {
//            AppUtils.showErrorToast(this,"Your password is too short");
//            return;
//        }


                if (accountType==PATIENT_ACCOUNT){
                    signInAsPatient(username,password);
                }else if (accountType==DOCTOR_ACCOUNT){
                    signInAsDoctor(username,password);
                }else {
                    AppUtils.showInfoToast(SignInActivity.this,getString(R.string.sgin_in_toast_select_user_type));
                }


//        viewModel.signIn(new SignInData("assad","12345")).observe(this,
//                new Observer<SignInResponse>() {
//                    @Override
//                    public void onChanged(@Nullable SignInResponse signInResponse) {
//                        Log.e("Eeeeeeeee",signInResponse.getData().getMobile());
//                    }
//                });



    }

    private void signInAsDoctor(String username, String password) {
//        startActivity(new Intent(SignInActivity.this,DoctorHomeActivity.class));

        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        service.signInAsDoctorApi(map).enqueue(new Callback<LoginDoctorResponse>() {
            @Override
            public void onResponse(Call<LoginDoctorResponse> call, Response<LoginDoctorResponse> response) {
                if (response.body().getStatus().equals("200")){
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(DOCTOR_DATA, response.body().getData().getToken()).apply();
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_STATUS, "true").apply();
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_TYPE, DOCTOR).apply();
                    startActivity(new Intent(SignInActivity.this,DoctorHomeActivity.class));
                    finish();
                }else if (response.code()==401){
                    AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
                }else if (response.code()==2){
                    validate();
                }else {
                    AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
                }
            }

            @Override
            public void onFailure(Call<LoginDoctorResponse> call, Throwable t) {
                AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
            }
        });
    }

    private void signInAsPatient(String username, String password) {

//                 viewModel=ViewModelProviders.of(this).get(SignInViewModel.class);
//
//        viewModel.getSelected(username,password).observe(this,
//                new Observer<SignInResponse>() {
//                    @Override
//                    public void onChanged(@Nullable SignInResponse signInResponse) {
//                        AppUtils.showSuccessToast(SignInActivity.this,signInResponse.getData().getMobile());
//                    }
//                });

        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);//d3ee0294e4e9007bfd0fa1dd9d06e8eb8
        service.signInAsPatientApi(map).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.body().getStatus().equals("200")){
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(PATIENT_DATA, response.body().getData().getToken()).apply();
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_STATUS, "true").apply();
                    CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString(USER_TYPE, PATIENT).apply();
                    startActivity(new Intent(SignInActivity.this,PatientHomeActivity.class));
                    finish();
                }else if (response.code()==401){
                    AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
                }else if (response.code()==2){
                    validate();
                }else {
                    AppUtils.showErrorToast(SignInActivity.this,"Check Your Data");
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                AppUtils.showErrorToast(SignInActivity.this,"Something wrong");
            }
        });
    }

    private void validate() {

    }

}
