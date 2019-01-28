package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.roger.catloadinglibrary.CatLoadingView;
import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.app.Rehab;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.requests.patient.DoctorFilter;
import smile.khaled.mohamed.rehab.service.responses.patient.AddFavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.DeleteFavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorfilter.DataItem;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorfilter.DoctorFilterResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.adapter.PatientSearchResultAdapter;
import smile.khaled.mohamed.rehab.views.fragment.BaseFragment;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

import static smile.khaled.mohamed.rehab.data.Constants.BASE_URL;
import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_NO_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_CITY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_DESTRIC;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_GENDER;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_NATIONALTY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_SPECIALTY;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

public class SearchResultActivity extends BaseActivity implements ISearchResultHandler {


    private PatientSearchResultAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<DataItem> recentList = new ArrayList<>();
    private Map<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search);
        recyclerView=findViewById(R.id.category_recycler);
        map.put("city",getIntent().getStringExtra(FILTER_DOCTORS_BY_SPECIALTY));
        map.put("nationality",getIntent().getStringExtra(FILTER_DOCTORS_BY_CITY));
        map.put("neighborhood",getIntent().getStringExtra(FILTER_DOCTORS_BY_DESTRIC));
        map.put("specialty",getIntent().getStringExtra(FILTER_DOCTORS_BY_GENDER));
        map.put("gender",getIntent().getStringExtra(FILTER_DOCTORS_BY_NATIONALTY));
        map.put("token",CacheUtils.getUserToken(this,PATIENT_DATA));



        mAdapter = new PatientSearchResultAdapter(this,recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        loadResult();
    }

    private void loadResult() {
        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        service.doctorFilter(map).enqueue(new Callback<DoctorFilterResponse>() {
            @Override
            public void onResponse(Call<DoctorFilterResponse> call, Response<DoctorFilterResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);

                if (response.body().getStatus().equals("200") && response.body().getData().size()!=0){
                    recentList.clear();
                    recentList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }else if (response.body().getData().size()==0){
                    StateExecuterKt.setState(recyclerView, CUSTOM_NO_DATA);
                }else {
                    error();
                }
            }

            @Override
            public void onFailure(Call<DoctorFilterResponse> call, Throwable t) {
                Log.e("RRRRRRRR",t.getMessage());
                error();
            }
        });
    }

    private void error() {
        View v= StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
        Button errorBt = v.findViewById(R.id.retryBt);
        errorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadResult();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResevationClick(String id) {
        Intent intent = new Intent(SearchResultActivity.this,ReservationActivity.class);
        intent.putExtra(DOCTOR_ID,id);
        startActivity(intent);
    }

    @Override
    public void onShareClick(String id) {
        String url = BASE_URL + "share/" + id +"/en";
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Share...")
                .setText(url)
                .startChooser();
    }

    @Override
    public void addFavourite(String id) {

        Map<String,String> map=new HashMap<String,String>();
        map.put("token",CacheUtils.getUserToken(this,PATIENT_DATA));
        map.put("type","set");
        map.put("doctor_id",id);
        service.addFavourites(map).enqueue(new Callback<AddFavouriteResponse>() {
            @Override
            public void onResponse(Call<AddFavouriteResponse> call, Response<AddFavouriteResponse> response) {
                if (response.body().getStatus().equals("200")){
                    AppUtils.showSuccessToast(SearchResultActivity.this,"Successfully Added");
                }else {
                    AppUtils.showErrorToast(SearchResultActivity.this,"Not Added");
                }
            }

            @Override
            public void onFailure(Call<AddFavouriteResponse> call, Throwable t) {
                AppUtils.showErrorToast(SearchResultActivity.this,"Not Added");
            }
        });
    }

    @Override
    public void deleteFavourite(String id) {
        Map<String,String> map= new HashMap<>();
        map.put("type","del");
        map.put("token",CacheUtils.getUserToken(this,PATIENT_DATA));
        map.put("doctor_id",id);

        service.deleteFavourites(map).enqueue(new Callback<DeleteFavouriteResponse>() {
            @Override
            public void onResponse(Call<DeleteFavouriteResponse> call, Response<DeleteFavouriteResponse> response) {
                if (response.body().getStatus().equals("200")){
                    AppUtils.showSuccessToast(SearchResultActivity.this,"Successfully Deleted");
                }else {
                    AppUtils.showErrorToast(SearchResultActivity.this,"Not Deleted");
                }
            }

            @Override
            public void onFailure(Call<DeleteFavouriteResponse> call, Throwable t) {
                AppUtils.showErrorToast(SearchResultActivity.this,"Not Deleted");
            }
        });
    }
}
