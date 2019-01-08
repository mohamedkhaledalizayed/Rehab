package smile.khaled.mohamed.rehab.app;

import android.app.Application;

import com.tripl3dev.prettystates.StatesConfigFactory;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.RetrofitModule;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_NO_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_NO_INTERNET;
import static smile.khaled.mohamed.rehab.data.Constants.SPINNER_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.SPINNER_LOADING;


public class Rehab extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitModule.intialize(this);
        StatesConfigFactory.Companion.intialize()
                .initDefaultViews();
        StatesConfigFactory.Companion.get().addStateView(CUSTOM_ERROR,R.layout.custom_height_error);
        StatesConfigFactory.Companion.get().addStateView(CUSTOM_NO_DATA,R.layout.custom_no_data_found);
        StatesConfigFactory.Companion.get().addStateView(CUSTOM_NO_INTERNET,R.layout.custom_no_internet);
        StatesConfigFactory.Companion.get().addStateView(SPINNER_LOADING,R.layout.spinner_loading_view);
        StatesConfigFactory.Companion.get().addStateView(SPINNER_ERROR,R.layout.spinner_error_view);
    }
}
