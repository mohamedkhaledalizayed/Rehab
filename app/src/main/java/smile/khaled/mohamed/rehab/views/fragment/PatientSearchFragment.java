package smile.khaled.mohamed.rehab.views.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.FragmentPatientSearchBinding;
import smile.khaled.mohamed.rehab.service.responses.both.cities.CityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.specialty.SpecialtyResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.ViewUtils;
import smile.khaled.mohamed.rehab.views.activity.SearchResultActivity;
import smile.khaled.mohamed.rehab.views.activity.SpinnerItemsAdapter;

import static smile.khaled.mohamed.rehab.data.Constants.BACKGROUND_COLOR_LIGHT;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_CITY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_DESTRIC;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_GENDER;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_NATIONALTY;
import static smile.khaled.mohamed.rehab.data.Constants.FILTER_DOCTORS_BY_SPECIALTY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientSearchFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PatientSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientSearchFragment.
     */


    private FragmentPatientSearchBinding binding;
    // TODO: Rename and change types and number of parameters
    public static PatientSearchFragment newInstance(String param1, String param2) {
        PatientSearchFragment fragment = new PatientSearchFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_search, container, false);

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
//        setAllSpecSpinner(getGenderNames(getContext()));
//        setCitySpinner(getGenderNames(getContext()));
        setDestricSpinner(getGenderNames(getContext()));
        setGenderSpinner(getGender(getContext()));
        setNationalitySpinner(getGenderNames(getContext()));


                loadAllSpecialty();




        final List<String> cityList = new ArrayList<>();
        cityList.add("اختار المدينة");
        service.cityApi().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                for (int i = 0; i<response.body().getData().size(); i++){
                    cityList.add(response.body().getData().get(i).getNameAr());
                }
                setCitySpinner(cityList);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });


        return binding.getRoot();
    }

    private void loadAllSpecialty(){
        final List<String> specialtyList = new ArrayList<>();
        specialtyList.add("كل التخصصات");
        service.specialtyApi().enqueue(new Callback<SpecialtyResponse>() {
            @Override
            public void onResponse(Call<SpecialtyResponse> call, Response<SpecialtyResponse> response) {

                for (int i = 0; i<response.body().getData().size(); i++){
                    specialtyList.add(response.body().getData().get(i).getName());
                }
                setAllSpecSpinner(specialtyList);
            }

            @Override
            public void onFailure(Call<SpecialtyResponse> call, Throwable t) {

            }
        });
    }

    private void search() {

        if (!AppUtils.isInternetAvailable(getContext())) {
            AppUtils.showErrorToast(getContext(),"Check your internet connection");
            return;
        }

        if (binding.allSpecSpinner.getSelectedItemPosition() == 0){
            AppUtils.showInfoToast(getContext(),"Select Specialty");
            return;
        }

        if (binding.citySpinner.getSelectedItemPosition() == 0){
            AppUtils.showInfoToast(getContext(),"Select City");
            return;
        }

        if (binding.destricSpinner.getSelectedItemPosition() == 0){
            AppUtils.showInfoToast(getContext(),"Select City");
            return;
        }

        if (binding.genderSpinner.getSelectedItemPosition() == 0){
            AppUtils.showInfoToast(getContext(),"Select City");
            return;
        }

        if (binding.nationalitySpinner.getSelectedItemPosition() == 0){
            AppUtils.showInfoToast(getContext(),"Select City");
            return;
        }
        Intent intent = new Intent(getActivity(),SearchResultActivity.class);
        intent.putExtra(FILTER_DOCTORS_BY_SPECIALTY,binding.allSpecSpinner.getSelectedItem().toString());
        intent.putExtra(FILTER_DOCTORS_BY_CITY,binding.citySpinner.getSelectedItem().toString());
        intent.putExtra(FILTER_DOCTORS_BY_DESTRIC,binding.destricSpinner.getSelectedItem().toString());
        intent.putExtra(FILTER_DOCTORS_BY_GENDER,binding.genderSpinner.getSelectedItem().toString());
        intent.putExtra(FILTER_DOCTORS_BY_NATIONALTY,binding.nationalitySpinner.getSelectedItem().toString());
        startActivity(intent);

    }


    public void setAllSpecSpinner(List<String> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.allSpecSpinner.setAdapter(adapter);
    }

    public void setCitySpinner(List<String> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.citySpinner.setAdapter(adapter);
    }

    public void setDestricSpinner(List<String> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.destricSpinner.setAdapter(adapter);
    }

    public void setGenderSpinner(List<String> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.genderSpinner.setAdapter(adapter);
    }

    public void setNationalitySpinner(List<String> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.nationalitySpinner.setAdapter(adapter);
    }

    public static List<String> getGender(Context context){
        String[] genderArray = context.getResources().getStringArray(R.array.gender);
        final List<String> genders = new ArrayList<>(genderArray.length);
        genders.add(genderArray[0]);
        genders.add(genderArray[1]);
        genders.add(genderArray[2]);

        return genders;
    }

    public static List<String> getGenderNames(Context context) {
        String[] genderArray = context.getResources().getStringArray(R.array.city_names);
        final List<String> genders = new ArrayList<>(genderArray.length*3);
        genders.add(genderArray[0]);
        genders.add(genderArray[1]);
        genders.add(genderArray[2]);
        genders.add(genderArray[3]);
        genders.add(genderArray[4]);
        genders.add(genderArray[5]);
        genders.add(genderArray[6]);
        genders.add(genderArray[0]);
        genders.add(genderArray[1]);
        genders.add(genderArray[2]);
        genders.add(genderArray[3]);
        genders.add(genderArray[4]);
        genders.add(genderArray[5]);
        genders.add(genderArray[6]);
        genders.add(genderArray[0]);
        genders.add(genderArray[1]);
        genders.add(genderArray[2]);
        genders.add(genderArray[3]);
        genders.add(genderArray[4]);
        genders.add(genderArray[5]);
        genders.add(genderArray[6]);

        return genders;
    }

}
