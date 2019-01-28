package smile.khaled.mohamed.rehab.views.dialog.patient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.PatientReviewDialogBinding;
import smile.khaled.mohamed.rehab.service.RetrofitModule;
import smile.khaled.mohamed.rehab.service.ServiceApi;
import smile.khaled.mohamed.rehab.service.responses.EvaluationResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

public class PatientReviewDialog extends DialogFragment {


    public static ServiceApi service= RetrofitModule.getInstance().getService();

    private PatientReviewDialogBinding binding;
    String doctorId;
    public static PatientReviewDialog newInstance(String doctorId) {
        PatientReviewDialog frag = new PatientReviewDialog();
        Bundle args = new Bundle();
        args.putString(DOCTOR_ID, doctorId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctorId = getArguments().getString(DOCTOR_ID);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.patient_review_dialog, container, false);

        binding.rateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (binding.note.getText()!=null && !binding.note.getText().equals("")){
                   addReview();
               }else {
                   AppUtils.showInfoToast(getActivity(),"Enter Your Review");
               }
            }
        });

        return binding.getRoot();
    }

    private void addReview(){
        Map<String,String> map= new HashMap<>();
        map.put("type","set");
        map.put("token",CacheUtils.getUserToken(getActivity(),PATIENT_DATA));
        map.put("doctor_id",doctorId);
        map.put("rate",binding.rateBar.getRating()+"");
        map.put("note",binding.note.getText()+"");
        service.setRateForDoctor(map).enqueue(new Callback<EvaluationResponse>() {
            @Override
            public void onResponse(Call<EvaluationResponse> call, Response<EvaluationResponse> response) {
                if (response.body().getStatus().equals("200")){
                    dismiss();
                    AppUtils.showSuccessToast(getContext(),"Review Added Successfully");
                }else {
                    AppUtils.showErrorToast(getContext(),"Review Not Added");
                }
            }

            @Override
            public void onFailure(Call<EvaluationResponse> call, Throwable t) {
                dismiss();
                AppUtils.showErrorToast(getContext(),"Review Not Added");
            }
        });
    }
}
