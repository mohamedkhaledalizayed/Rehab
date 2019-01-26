package smile.khaled.mohamed.rehab.views.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.FragmentPatientSearchBinding;
import smile.khaled.mohamed.rehab.model.SpinnerData;
import smile.khaled.mohamed.rehab.service.responses.both.cities.CityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.nationality.NationalityResponse;
import smile.khaled.mohamed.rehab.service.responses.both.neighborhood.NeighborhoodResponse;
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
                loadAllSpecialty();
                loadCities();
                loadNationality();
                loadNeighborhood();
        setGenderSpinner(getGender(getContext()));

        return binding.getRoot();
    }
    final List<SpinnerData> cityList = new ArrayList<>();

    private void loadCities() {
        cityList.add(new SpinnerData("0","اختار المدينة"));
        service.cityApi().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                for (int i = 0; i<response.body().getData().size(); i++){
                    cityList.add(new SpinnerData(response.body().getData().get(i).getId(),response.body().getData().get(i).getCityName()));
                }
                setCitySpinner(cityList);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }

    final List<SpinnerData> nationalityList = new ArrayList<>();

    private void loadNationality(){
        nationalityList.add(new SpinnerData("0","اختار الجنسية"));
        Configuration config = getActivity().getResources().getConfiguration();

        Map<String,String> map=new HashMap<>();
        map.put("lang",config.locale.getLanguage());
        service.nationality(map).enqueue(new Callback<NationalityResponse>() {
            @Override
            public void onResponse(Call<NationalityResponse> call, Response<NationalityResponse> response) {
                for (int i=0; i < response.body().getData().size();i++){
                    nationalityList.add(new SpinnerData(response.body().getData().get(i).getId(),response.body().getData().get(i).getName()));
                }
                setNationalitySpinner(nationalityList);
            }

            @Override
            public void onFailure(Call<NationalityResponse> call, Throwable t) {

            }
        });
    }
    final List<SpinnerData> neighborhoodList = new ArrayList<>();

    private void loadNeighborhood(){
        neighborhoodList.add(new SpinnerData("0","اختار الحى"));
        Configuration config = getActivity().getResources().getConfiguration();

        Map<String,String> map=new HashMap<>();
        map.put("lang",config.locale.getLanguage());
        service.neighborhood(map).enqueue(new Callback<NeighborhoodResponse>() {
            @Override
            public void onResponse(Call<NeighborhoodResponse> call, Response<NeighborhoodResponse> response) {
                for (int i=0; i < response.body().getData().size();i++){
                    neighborhoodList.add(new SpinnerData(response.body().getData().get(i).getId(),response.body().getData().get(i).getName()));
                }
                setDestricSpinner(neighborhoodList);
            }

            @Override
            public void onFailure(Call<NeighborhoodResponse> call, Throwable t) {

            }
        });
    }
    final List<SpinnerData> specialtyList = new ArrayList<>();

    private void loadAllSpecialty(){
        specialtyList.add(new SpinnerData("0","كل التخصصات"));
        service.specialtyApi().enqueue(new Callback<SpecialtyResponse>() {
            @Override
            public void onResponse(Call<SpecialtyResponse> call, Response<SpecialtyResponse> response) {

                for (int i = 0; i<response.body().getData().size(); i++){
                    specialtyList.add(new SpinnerData(response.body().getData().get(i).getId(),response.body().getData().get(i).getName()));
                }
                setAllSpecSpinner(specialtyList);
            }

            @Override
            public void onFailure(Call<SpecialtyResponse> call, Throwable t) {

            }
        });
    }

    private void search() {

        String city = "";
        String nationality = "";
        String neighborhood = "";
        String specialty = "";
        String gender = "";
        if (!AppUtils.isInternetAvailable(getContext())) {
            AppUtils.showErrorToast(getContext(),"Check your internet connection");
            return;
        }

        if (binding.allSpecSpinner.getSelectedItemPosition() != 0){
            specialty = specialtyList.get(binding.allSpecSpinner.getSelectedItemPosition()).getId()+"";
            Log.e("specialtyList",specialtyList.get(binding.allSpecSpinner.getSelectedItemPosition()).getId());
        }

        if (binding.citySpinner.getSelectedItemPosition() != 0){
            city = cityList.get(binding.citySpinner.getSelectedItemPosition()).getId()+"";
            Log.e("cityList",cityList.get(binding.citySpinner.getSelectedItemPosition()).getId());
        }

        if (binding.destricSpinner.getSelectedItemPosition() != 0){
            neighborhood = neighborhoodList.get(binding.destricSpinner.getSelectedItemPosition()).getId()+"";
            Log.e("neighborhoodList",neighborhoodList.get(binding.destricSpinner.getSelectedItemPosition()).getId());
        }

        if (binding.genderSpinner.getSelectedItemPosition() != 0){
            if (binding.genderSpinner.getSelectedItemPosition() == 1){
                gender = "0";
            }else {
                gender = "1";
            }
        }

        if (binding.nationalitySpinner.getSelectedItemPosition() != 0){
            nationality = nationalityList.get(binding.nationalitySpinner.getSelectedItemPosition()).getId()+"";
            Log.e("nationalityList",nationalityList.get(binding.nationalitySpinner.getSelectedItemPosition()).getId());
        }
        Intent intent = new Intent(getActivity(),SearchResultActivity.class);
        intent.putExtra(FILTER_DOCTORS_BY_SPECIALTY,specialty);
        intent.putExtra(FILTER_DOCTORS_BY_CITY,city);
        intent.putExtra(FILTER_DOCTORS_BY_DESTRIC,neighborhood);
        intent.putExtra(FILTER_DOCTORS_BY_GENDER,gender);
        intent.putExtra(FILTER_DOCTORS_BY_NATIONALTY,nationality);
        startActivity(intent);

    }


    public void setAllSpecSpinner(List<SpinnerData> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.allSpecSpinner.setAdapter(adapter);
    }

    public void setCitySpinner(List<SpinnerData> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.citySpinner.setAdapter(adapter);
    }

    public void setDestricSpinner(List<SpinnerData> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.destricSpinner.setAdapter(adapter);
    }

    public void setGenderSpinner(List<SpinnerData> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.genderSpinner.setAdapter(adapter);
    }

    public void setNationalitySpinner(List<SpinnerData> list){
        SpinnerItemsAdapter adapter=new SpinnerItemsAdapter(list,BACKGROUND_COLOR_LIGHT);
        binding.nationalitySpinner.setAdapter(adapter);
    }

    public static List<SpinnerData> getGender(Context context){
        String[] genderArray = context.getResources().getStringArray(R.array.gender);
        final List<SpinnerData> genders = new ArrayList<>(genderArray.length);
        genders.add(new SpinnerData("0",genderArray[0]));
        genders.add(new SpinnerData("0",genderArray[1]));
        genders.add(new SpinnerData("1",genderArray[2]));


        return genders;
    }


}
