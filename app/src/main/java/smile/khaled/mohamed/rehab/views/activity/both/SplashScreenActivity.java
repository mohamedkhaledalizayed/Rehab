package smile.khaled.mohamed.rehab.views.activity.both;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.utils.LocaleHelper;
import smile.khaled.mohamed.rehab.views.activity.DoctorHomeActivity;
import smile.khaled.mohamed.rehab.views.activity.PatientHomeActivity;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR;
import static smile.khaled.mohamed.rehab.data.Constants.USER_STATUS;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        LocaleHelper.setLocale(this, CacheUtils.getUserLanguage(this, "language"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CacheUtils.checkUserState(SplashScreenActivity.this,USER_STATUS).equals("true")){
                    if (CacheUtils.checkUserType(SplashScreenActivity.this,USER_TYPE).equals(DOCTOR)){
                        startActivity(new Intent(SplashScreenActivity.this,DoctorHomeActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashScreenActivity.this,PatientHomeActivity.class));
                        finish();
                    }
                }else{
                    startActivity(new Intent(SplashScreenActivity.this,SignInActivity.class));
                    finish();
                }
            }
        },3000);
    }
}
