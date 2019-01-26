package smile.khaled.mohamed.rehab.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import smile.khaled.mohamed.rehab.service.responses.patient.dates.DataItem;
import smile.khaled.mohamed.rehab.service.responses.patient.dates.PatientDatesResponse;
import smile.khaled.mohamed.rehab.views.adapter.PatientDatesAdapter;

import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientDatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientDatesFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private PatientDatesAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<DataItem> recentList = new ArrayList<>();

    public PatientDatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientDatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientDatesFragment newInstance(String param1, String param2) {
        PatientDatesFragment fragment = new PatientDatesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dates, container, false);
        recyclerView=view.findViewById(R.id.category_recycler);
        mAdapter = new PatientDatesAdapter(getActivity(),recentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        loadDates();
        return view;
    }

    private void loadDates() {
        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        Map<String,String> map=new HashMap<String,String>();
        map.put("token",CacheUtils.getUserToken(getActivity(),PATIENT_DATA));
        map.put("type","select");

        service.getPatientDates(map).enqueue(new Callback<PatientDatesResponse>() {
            @Override
            public void onResponse(Call<PatientDatesResponse> call, Response<PatientDatesResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);
                if (response.body().getStatus().equals("200")){
                    recentList.clear();
                    recentList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PatientDatesResponse> call, Throwable t) {

                View v= StateExecuterKt.setState(recyclerView, StatesConstants.ERROR_STATE);
                Button errorBt = v.findViewById(R.id.textButton);
                errorBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadDates();
                    }
                });
            }
        });
    }

}
