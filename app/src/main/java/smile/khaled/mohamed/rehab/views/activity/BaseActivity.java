package smile.khaled.mohamed.rehab.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.RetrofitModule;
import smile.khaled.mohamed.rehab.service.ServiceApi;

public class BaseActivity extends AppCompatActivity {

    public static ServiceApi service= RetrofitModule.getInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
