package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ActivityAccountTypeBinding;
import smile.khaled.mohamed.rehab.utils.AppUtils;

import static smile.khaled.mohamed.rehab.data.Constants.ACCOUNT_TYPE;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_ACCOUNT;

public class AccountTypeActivity extends AppCompatActivity {

    private int accountType=1;
    private ActivityAccountTypeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_type);
    }

    public void createPatientAccount(View view) {
        accountType=PATIENT_ACCOUNT;
        binding.signUpAsPatient.setBackgroundResource(R.drawable.signin_bg_btn);
        binding.signUpAsDoctor.setBackgroundResource(R.drawable.signin_border_btn);
    }

    public void createDoctorAccount(View view) {
        accountType=DOCTOR_ACCOUNT;
        binding.signUpAsDoctor.setBackgroundResource(R.drawable.signin_bg_btn);
        binding.signUpAsPatient.setBackgroundResource(R.drawable.signin_border_btn);
    }

    public void next(View view) {
        if (accountType!=1){
            Intent intent=new Intent(this,SignUpActivity.class);
            intent.putExtra(ACCOUNT_TYPE,accountType);
            startActivity(intent);
            finish();
        }else {
            AppUtils.showInfoToast(this,getString(R.string.sgin_in_toast_select_user_type));
        }
    }
}
