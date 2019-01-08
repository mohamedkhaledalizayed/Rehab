package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ActivityActivateAccountBinding;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.ValidateUtils;

import static smile.khaled.mohamed.rehab.data.Constants.ACCOUNT_TYPE;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_ACCOUNT;

public class ActivateAccountActivity extends BaseActivity {

    private int accountType=1;

    private ActivityActivateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activate_account);
        AppUtils.setHtmlText(R.string.re_send,binding.activateCode);
        accountType=getIntent().getIntExtra(ACCOUNT_TYPE,1);
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

        if (accountType==DOCTOR_ACCOUNT){
            startActivity(new Intent(this,DoctorHomeActivity.class));
        }else if (accountType==PATIENT_ACCOUNT){
            startActivity(new Intent(this,PatientHomeActivity.class));
        }else {
            AppUtils.showErrorToast(this,"SomeThing is wrong");
        }
    }
}
