package smile.khaled.mohamed.rehab.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.views.adapter.DoctorReviewsAdapter;
import smile.khaled.mohamed.rehab.views.adapter.PatientSearchResultAdapter;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

public class DoctorReviewsActivity extends AppCompatActivity {

    private DoctorReviewsAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<Favourite> recentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reviews);

        recyclerView=findViewById(R.id.category_recycler);
        mAdapter = new DoctorReviewsAdapter(this,recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
