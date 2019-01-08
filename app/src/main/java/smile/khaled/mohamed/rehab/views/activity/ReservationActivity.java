package smile.khaled.mohamed.rehab.views.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ActivityReservationBinding;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.dialog.patient.ConfirmReservationDialog;
import smile.khaled.mohamed.rehab.views.dialog.patient.PatientReviewDialog;
import smile.khaled.mohamed.rehab.views.dialog.patient.SendMessageDialog;

public class ReservationActivity extends AppCompatActivity {

    private ActivityReservationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        AppUtils.setHtmlText(R.string._300_reviews,binding.reviews);
    }

    public void confirmReservation(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ConfirmReservationDialog editNameDialogFragment = ConfirmReservationDialog.newInstance("");
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void review(View view) {
        FragmentManager fm = getSupportFragmentManager();
        PatientReviewDialog editNameDialogFragment = PatientReviewDialog.newInstance("");
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void sendMessage(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SendMessageDialog editNameDialogFragment = SendMessageDialog.newInstance("");
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void call(View view) {
        AppUtils.call(this,"01113595439");
    }

    public void showReviews(View view) {
        startActivity(new Intent(this,DoctorReviewsActivity.class));
    }
}
