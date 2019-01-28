package smile.khaled.mohamed.rehab.views.fragment;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.responses.doctor.addnewdate.AddDateResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.activity.SearchResultActivity;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorAddDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorAddDateFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String dateOfDay;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView textView;
    Date now;
    Calendar c;
    TimePickerDialog picker;
    public DoctorAddDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorAddDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorAddDateFragment newInstance(String param1, String param2) {
        DoctorAddDateFragment fragment = new DoctorAddDateFragment();
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

        View view=inflater.inflate(R.layout.fragment_doctor_add_date, container, false);
        textView=view.findViewById(R.id.date);




        c = Calendar.getInstance();
        dateOfDay=c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH);

        view.findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                if (sHour>12){
//                                    textView.setText(sHour-12 + ":" + sMinute+":PM");
//                                }else if (sHour==12){
//                                    textView.setText(sHour + ":" + sMinute+":PM");
//                                }else if (sHour==0){
//                                    textView.setText(sHour + ":" + sMinute+":AM");
//                                }else {
//                                    textView.setText(sHour + ":" + sMinute+":AM");
//                                }

                                addNewDate(dateOfDay,sHour+":"+sMinute+":"+"00");

                            }
                        }, hour, minutes, false);

                picker.show();
            }
        });
        DateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
        now = new Date();
        String dateStr = format.format(now);
        textView.setText(dateStr);

        Log.e("Date",dateOfDay);
        c.setTime(now);


        view.findViewById(R.id.decr_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE,-1);
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
                now=c.getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
                String output = sdf1.format(c.getTime());
                textView.setText(output);
            }
        });
        return view;
    }

    private void addNewDate(String dateOfDay, String time) {

        Map<String,String> map=new HashMap<>();
        map.put("type","set");
        map.put("token",CacheUtils.getUserToken(getActivity(),DOCTOR_DATA));
        map.put("date",dateOfDay);
        map.put("time","10:00:00");

        service.addNewDate(map).enqueue(new Callback<AddDateResponse>() {
            @Override
            public void onResponse(Call<AddDateResponse> call, Response<AddDateResponse> response) {
                if (response.body().getStatus().equals("201")){
                    AppUtils.showSuccessToast(getActivity(),"Date Added Successfully");
                }else {
                    AppUtils.showInfoToast(getActivity(),"Date Not Added");
                }
            }

            @Override
            public void onFailure(Call<AddDateResponse> call, Throwable t) {
                AppUtils.showErrorToast(getActivity(),"Date Not Added");
            }
        });

    }

}
