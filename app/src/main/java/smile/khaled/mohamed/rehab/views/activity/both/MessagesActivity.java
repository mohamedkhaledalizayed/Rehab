package smile.khaled.mohamed.rehab.views.activity.both;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.AllMessagesResponse;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.DataItem;
import smile.khaled.mohamed.rehab.service.responses.both.message.SendMessageResponse;
import smile.khaled.mohamed.rehab.views.activity.BaseActivity;
import smile.khaled.mohamed.rehab.views.adapter.DoctorReviewsAdapter;
import smile.khaled.mohamed.rehab.views.adapter.MessagesAdapter;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

public class MessagesActivity extends BaseActivity {

    private MessagesAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<DataItem> recentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        recyclerView=findViewById(R.id.recycler_view);
        mAdapter = new MessagesAdapter(this,recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Map<String,String> map= new HashMap<>();
        map.put("type","select_receiver");
        map.put("token",CacheUtils.getUserToken(this,PATIENT_DATA));

        service.getAllMessages(map).enqueue(new Callback<AllMessagesResponse>() {
            @Override
            public void onResponse(Call<AllMessagesResponse> call, Response<AllMessagesResponse> response) {
                recentList.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AllMessagesResponse> call, Throwable t) {

            }
        });
    }
}
