package smile.khaled.mohamed.rehab.views.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.FragmentDoctorProfileBinding;
import smile.khaled.mohamed.rehab.service.responses.doctor.doctorprofile.DoctorProfileResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.activity.DoctorReviewsActivity;

import static smile.khaled.mohamed.rehab.data.Constants.CUSTOM_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorProfileFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorProfileFragment newInstance(String param1, String param2) {
        DoctorProfileFragment fragment = new DoctorProfileFragment();
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

    private String doctorId;
    private FragmentDoctorProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_profile, container, false);

        binding.patientsReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),DoctorReviewsActivity.class);
                intent.putExtra(DOCTOR_ID,doctorId);
                startActivity(intent);
            }
        });

        getDoctorData();
        return binding.getRoot();
    }

    private void getDoctorData(){

        StateExecuterKt.setState(binding.doctorProfile, StatesConstants.LOADING_STATE);

        service.getDoctorProfileData(CacheUtils.getUserToken(getActivity(),DOCTOR_DATA)).enqueue(new Callback<DoctorProfileResponse>() {
            @Override
            public void onResponse(Call<DoctorProfileResponse> call, Response<DoctorProfileResponse> response) {
                StateExecuterKt.setState(binding.doctorProfile, StatesConstants.NORMAL_STATE);
                if (response.body().getStatus().equals("200")){
                    Glide.with(getActivity()).load(response.body().getData().get(0).getImg()).into(binding.doctorImage);
                    binding.doctorName.setText(response.body().getData().get(0).getName());
                    binding.doctorNote.setText(response.body().getData().get(0).getNote());
                    binding.reviews.setText("( "+response.body().getData().get(0).getEvaluation().getCount()+" Review )");
                    binding.doctorRate.setRating(response.body().getData().get(0).getEvaluation().getRate());
                    binding.doctorSpec.setText(response.body().getData().get(0).getSpecialty());
                    doctorId = response.body().getData().get(0).getId();
                }else {
                    error();
                }
            }

            @Override
            public void onFailure(Call<DoctorProfileResponse> call, Throwable t) {
                error();
            }
        });
    }

    private void error(){
        AppUtils.showErrorToast(getActivity(),"Error");
        View v= StateExecuterKt.setState(binding.doctorProfile, CUSTOM_ERROR);
        Button errorBt = v.findViewById(R.id.retryBt);
        errorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDoctorData();
            }
        });
    }

}
