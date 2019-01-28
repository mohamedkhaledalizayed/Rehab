package smile.khaled.mohamed.rehab.views.fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.responses.doctor.getalldates.AllDatesResponse;
import smile.khaled.mohamed.rehab.service.responses.doctor.getalldates.DataItem;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.adapter.PatientFavouriteAdapter;
import smile.khaled.mohamed.rehab.views.adapter.doctor.AllDatesAdapter;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_NO_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorDatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorDatesFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView textView;
    Date now;
    Calendar c;
    TimePickerDialog picker;
    public DoctorDatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorDatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorDatesFragment newInstance(String param1, String param2) {
        DoctorDatesFragment fragment = new DoctorDatesFragment();
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

    private AllDatesAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<DataItem> dataItemList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_dates, container, false);
        textView=view.findViewById(R.id.date);


        recyclerView=view.findViewById(R.id.doctor_dates_recycler);
        mAdapter = new AllDatesAdapter(getActivity(),dataItemList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        c = Calendar.getInstance();


        DateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
        now = new Date();
        String dateStr = format.format(now);
        textView.setText(dateStr);


        c.setTime(now);
        getAllDates(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH));



        view.findViewById(R.id.decr_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE,-1);
                getAllDates(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH));
                now=c.getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
                String output = sdf1.format(c.getTime());
                textView.setText(output);
            }
        });



        view.findViewById(R.id.incr_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE,1);
                getAllDates(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH));
                now=c.getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
                String output = sdf1.format(c.getTime());
                textView.setText(output);
            }
        });
        return view;
    }

    private void getAllDates(String date){
        AppUtils.showSuccessToast(getActivity(),date);
        StateExecuterKt.setState(recyclerView, StatesConstants.LOADING_STATE);

        Map<String,String> map=new HashMap<>();
        map.put("type","select");
        map.put("token",CacheUtils.getUserToken(getActivity(),DOCTOR_DATA));
        map.put("date",date);
        service.getAllDates(map).enqueue(new Callback<AllDatesResponse>() {
            @Override
            public void onResponse(Call<AllDatesResponse> call, Response<AllDatesResponse> response) {
                StateExecuterKt.setState(recyclerView, StatesConstants.NORMAL_STATE);

                if (response.body().getStatus().equals("200") && response.body().getData().size()!=0){
                    dataItemList.clear();
                    dataItemList.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }else if (response.body().getData().size()==0){
                    StateExecuterKt.setState(recyclerView, CUSTOM_NO_DATA);
                }else {
                    View v= StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
                    Button errorBt = v.findViewById(R.id.retryBt);
                    errorBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getAllDates(date);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<AllDatesResponse> call, Throwable t) {
                AppUtils.showErrorToast(getActivity(),"Error");
                View v= StateExecuterKt.setState(recyclerView, CUSTOM_ERROR);
                Button errorBt = v.findViewById(R.id.retryBt);
                errorBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAllDates(date);
                    }
                });
            }
        });

    }

}
