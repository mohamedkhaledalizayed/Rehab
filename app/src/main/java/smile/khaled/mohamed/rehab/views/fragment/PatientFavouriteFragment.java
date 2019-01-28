package smile.khaled.mohamed.rehab.views.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import smile.khaled.mohamed.rehab.service.RetrofitModule;
import smile.khaled.mohamed.rehab.service.ServiceApi;
import smile.khaled.mohamed.rehab.service.requests.patient.DocotorFavourite;
import smile.khaled.mohamed.rehab.service.responses.IFavouriteDeleteHandler;
import smile.khaled.mohamed.rehab.service.responses.patient.favourites.DataItem;
import smile.khaled.mohamed.rehab.service.responses.patient.favourites.FavouriteResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.adapter.PatientFavouriteAdapter;

import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;


public class PatientFavouriteFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private PatientFavouriteAdapter mAdapter;
    private RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PatientFavouriteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PatientFavouriteFragment newInstance(String param1, String param2) {
        PatientFavouriteFragment fragment = new PatientFavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<DataItem> recentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView=view.findViewById(R.id.category_recycler);
        mAdapter = new PatientFavouriteAdapter(getActivity(),recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        loadFavourites();

        return view;
    }

    private void loadFavourites() {
        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);


        Map<String,String> favourite=new HashMap<String,String>();
        favourite.put("token",CacheUtils.getUserToken(getActivity(),PATIENT_DATA));
        favourite.put("type","select");
        service.getFavourites(favourite).enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);
                if (response.body().getStatus().equals("200")){
                    recentList.clear();
                    recentList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    error();
                }

            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                Log.e("oooooo",t.getMessage());
                error();
            }
        });
    }

    private void error() {
        View v= StateExecuterKt.setState(recyclerView, StatesConstants.ERROR_STATE);
        Button errorBt = v.findViewById(R.id.textButton);
        errorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFavourites();
            }
        });
    }


}
