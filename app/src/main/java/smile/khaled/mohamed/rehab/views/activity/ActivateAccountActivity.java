package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.ActivityActivateAccountBinding;
import smile.khaled.mohamed.rehab.service.responses.both.activateaccount.ActivateAccountResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.ValidateUtils;
import smile.khaled.mohamed.rehab.views.activity.both.SignInActivity;
import smile.khaled.mohamed.rehab.views.activity.both.SplashScreenActivity;

import static smile.khaled.mohamed.rehab.data.Constants.ACCOUNT_TYPE;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.USER_STATUS;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;

public class ActivateAccountActivity extends BaseActivity {

    private int accountType=1;
    private String token;
    private ActivityActivateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activate_account);
        AppUtils.setHtmlText(R.string.re_send,binding.activateCode);
        accountType=getIntent().getIntExtra(ACCOUNT_TYPE,1);
        token=getIntent().getStringExtra("token");
    }


    public void openHome(View view) {

        final String code = AppUtils.getTextContent(binding.code);

        if (!AppUtils.isInternetAvailable(this)) {
            AppUtils.showErrorToast(this,"Check your internet connection");
            return;
        }

        if (ValidateUtils.missingInputs(code)) {
            AppUtils.showErrorToast(this,"Check your code");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("validate",code);

        if (token != null && accountType != 1){
            activateAccount(map);
        }else {
            AppUtils.showErrorToast(this,"SomeThing is wrong");
        }

    }

    private void activateAccount(Map<String,String> map){

        service.activateAccountApi(map).enqueue(new Callback<ActivateAccountResponse>() {
            @Override
            public void onResponse(Call<ActivateAccountResponse> call, Response<ActivateAccountResponse> response) {
                if (response.body().getStatus().equals("201")){
                    saveData();
                }else {
                    AppUtils.showErrorToast(ActivateAccountActivity.this,"Check your code");
                }
            }

            @Override
            public void onFailure(Call<ActivateAccountResponse> call, Throwable t) {
                AppUtils.showErrorToast(ActivateAccountActivity.this,"Check your code");
            }
        });
    }

    private void saveData() {
        if (accountType==DOCTOR_ACCOUNT){
            CacheUtils.getSharedPreferences(this).edit().putString(DOCTOR_DATA, token).apply();
            CacheUtils.getSharedPreferences(this).edit().putString(USER_STATUS, "true").apply();
            CacheUtils.getSharedPreferences(this).edit().putString(USER_TYPE, DOCTOR).apply();
            startActivity(new Intent(this,DoctorHomeActivity.class));
            finish();
        }else {
            CacheUtils.getSharedPreferences(this).edit().putString(PATIENT_DATA, token).apply();
            CacheUtils.getSharedPreferences(this).edit().putString(USER_STATUS, "true").apply();
            CacheUtils.getSharedPreferences(this).edit().putString(USER_TYPE, PATIENT).apply();
            startActivity(new Intent(this,PatientHomeActivity.class));
            finish();
        }
    }
}
