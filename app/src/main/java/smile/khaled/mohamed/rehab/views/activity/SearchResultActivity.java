package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.app.Rehab;
import smile.khaled.mohamed.rehab.views.adapter.PatientSearchResultAdapter;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_CITY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_DESTRIC;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_GENDER;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_NATIONALTY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_SPECIALTY;

public class SearchResultActivity extends AppCompatActivity implements ISearchResultHandler {


    private PatientSearchResultAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<Favourite> recentList = new ArrayList<>();
    private Map<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=findViewById(R.id.category_recycler);
        map.put("",getIntent().getStringExtra(FILTER_DOCTORS_BY_SPECIALTY));
        map.put("",getIntent().getStringExtra(FILTER_DOCTORS_BY_CITY));
        map.put("",getIntent().getStringExtra(FILTER_DOCTORS_BY_DESTRIC));
        map.put("",getIntent().getStringExtra(FILTER_DOCTORS_BY_GENDER));
        map.put("",getIntent().getStringExtra(FILTER_DOCTORS_BY_NATIONALTY));

        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                add();
            }
        },3000);
        mAdapter = new PatientSearchResultAdapter(this,recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//
//        View v= StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
//        Button errorBt = v.findViewById(R.id.textButton);


    }

    public void add(){
        StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);

        Favourite favourite=new Favourite("mohamed","https://api.androidhive.info/images/nature/david.jpg");
        recentList.add(favourite);

        favourite=new Favourite("Ahmed","https://api.androidhive.info/images/nature/david.jpg");
        recentList.add(favourite);

        favourite=new Favourite("Mahmoud","https://api.androidhive.info/images/nature/david.jpg");
        recentList.add(favourite);

        favourite=new Favourite("Ayman","https://api.androidhive.info/images/nature/david.jpg");
        recentList.add(favourite);

        favourite=new Favourite("Taher","https://api.androidhive.info/images/nature/david.jpg");
        recentList.add(favourite);
    }


    @Override
    public void onResevationClick(int id) {
        startActivity(new Intent(SearchResultActivity.this,ReservationActivity.class));
    }

    @Override
    public void onShareClick(int id) {

    }

    @Override
    public void onFavouriteClick(int id) {

    }
}
