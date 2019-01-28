package smile.khaled.mohamed.rehab.views.activity.both;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.AllMessagesResponse;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.DataItem;
import smile.khaled.mohamed.rehab.service.responses.both.message.SendMessageResponse;
import smile.khaled.mohamed.rehab.views.activity.BaseActivity;
import smile.khaled.mohamed.rehab.views.adapter.DoctorReviewsAdapter;
import smile.khaled.mohamed.rehab.views.adapter.MessagesAdapter;
import smile.khaled.mohamed.rehab.views.dialog.patient.SendMessageDialog;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;
import smile.khaled.mohamed.rehab.views.interfaces.both.IMessageHandler;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;

public class MessagesActivity extends BaseActivity implements IMessageHandler {

    private MessagesAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<DataItem> recentList = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.messages);

        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new MessagesAdapter(this, recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        if (CacheUtils.checkUserType(this, USER_TYPE).equals(DOCTOR)) {
            map.put("type", "select_receiver");
            map.put("token", CacheUtils.getUserToken(this, DOCTOR_DATA));
        } else {
            map.put("type", "select_receiver");
            map.put("token", CacheUtils.getUserToken(this, PATIENT_DATA));
        }


        loadMessages();

    }

    private void loadMessages() {
        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        service.getAllMessages(map).enqueue(new Callback<AllMessagesResponse>() {
            @Override
            public void onResponse(Call<AllMessagesResponse> call, Response<AllMessagesResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);

                if (response.body().getData().size() == 0) {
                    StateExecuterKt.setState(recyclerView, StatesConstants.EMPTY_STATE);
                } else if (response.body().getStatus().equals("200")) {
                    recentList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    setErrorView();
                }
            }

            @Override
            public void onFailure(Call<AllMessagesResponse> call, Throwable t) {
                setErrorView();
            }
        });
    }

    private void setErrorView() {
        View v = StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
        Button errorBt = v.findViewById(R.id.retryBt);
        errorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMessages();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String id, String message) {
        FragmentManager fm = getSupportFragmentManager();
        SendMessageDialog editNameDialogFragment = SendMessageDialog.newInstance("",message);
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }
}