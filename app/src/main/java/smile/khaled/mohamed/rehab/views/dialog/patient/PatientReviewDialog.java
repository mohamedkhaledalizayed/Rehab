package smile.khaled.mohamed.rehab.views.dialog.patient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.PatientReviewDialogBinding;

public class PatientReviewDialog extends DialogFragment {



    private PatientReviewDialogBinding binding;
    public static PatientReviewDialog newInstance(String title) {
        PatientReviewDialog frag = new PatientReviewDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.patient_review_dialog, container, false);

        // Do all the stuff to initialize your custom view

        return binding.getRoot();
    }
}
