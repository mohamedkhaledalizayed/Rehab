package smile.khaled.mohamed.rehab.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import smile.khaled.mohamed.rehab.service.responses.patient.DeleteFavouriteResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.reviews.DataItem;
import smile.khaled.mohamed.rehab.service.responses.patient.reviews.ReviewsResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.adapter.DoctorReviewsAdapter;
import smile.khaled.mohamed.rehab.views.adapter.PatientSearchResultAdapter;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;

public class DoctorReviewsActivity extends BaseActivity {

    private DoctorReviewsAdapter mAdapter;
    private RecyclerView recyclerView;
    private String doctorId;
    private List<DataItem> recentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reviews);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.reviews);

        doctorId = getIntent().getStringExtra(DOCTOR_ID);
        recyclerView=findViewById(R.id.category_recycler);
        mAdapter = new DoctorReviewsAdapter(this,recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getDoctorReviews();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void error(){
        AppUtils.showErrorToast(this,"Error");
        View v= StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
        Button errorBt = v.findViewById(R.id.retryBt);
        errorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDoctorReviews();
            }
        });
    }

    private void getDoctorReviews() {

        Map<String,String> map=new HashMap<>();
        map.put("type","select-doctor-evaluation-user");
        map.put("doctor_id",doctorId);

        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        service.getDoctorReviews(map).enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);
                if (response.body().getStatus().equals("200")){
                    recentList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    error();
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Log.e("reviews",t.getMessage());
            }
        });
    }
}
