package smile.khaled.mohamed.rehab.views.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ActivityPatientSignUpBinding;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.ValidateUtils;

import static smile.khaled.mohamed.rehab.data.Constants.ACCOUNT_TYPE;
import static smile.khaled.mohamed.rehab.data.Constants.BACKGROUND_COLOR_DARCK;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_ACCOUNT;

public class SignUpActivity extends BaseActivity {

    private int accountType;
    private ActivityPatientSignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_sign_up);
        accountType=getIntent().getIntExtra(ACCOUNT_TYPE,1);
    }


    public void next(View view) {
        if (accountType!=1){
            sginUp();
        }else {
            AppUtils.showErrorToast(this,"SomeThing is wrong");
        }
    }

    private void sginUp() {


        final String username=AppUtils.getTextContent(binding.signUpUsername);
        final String phone=AppUtils.getTextContent(binding.signUpPhone);
        final String email=AppUtils.getTextContent(binding.signUpEmail);
        final String password=AppUtils.getTextContent(binding.signUpPassword);

//        if (!AppUtils.isInternetAvailable(this)) {
//            AppUtils.showErrorToast(this,"Check your internet connection");
//            return;
//        }
//
//        if (ValidateUtils.missingInputs(username, phone, email, password)) {
//            AppUtils.showErrorToast(this,"Check your Data");
//            return;
//        }
//
//        if (!ValidateUtils.validPhone(phone)) {
//            AppUtils.showErrorToast(this,"Check your phone number");
//            return;
//        }
//
//        if (!ValidateUtils.validPassword(password)) {
//            AppUtils.showErrorToast(this,"Your password is too short");
//            return;
//        }

        if (accountType==DOCTOR_ACCOUNT){
            Intent intent = new Intent(this,DoctorSignUpActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("phone",phone);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            startActivity(intent);
        }else if (accountType==PATIENT_ACCOUNT){
            startActivity(new Intent(this,ActivateAccountActivity.class));
        }
    }
}
