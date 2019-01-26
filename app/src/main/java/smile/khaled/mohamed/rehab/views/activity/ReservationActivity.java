package smile.khaled.mohamed.rehab.views.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;
import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.ActivityReservationBinding;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorprofile.DoctorDataResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.dialog.patient.ConfirmReservationDialog;
import smile.khaled.mohamed.rehab.views.dialog.patient.PatientReviewDialog;
import smile.khaled.mohamed.rehab.views.dialog.patient.SendMessageDialog;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

public class ReservationActivity extends BaseActivity {

    private ActivityReservationBinding binding;
    private String doctorId;
    LocationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        doctorId = getIntent().getStringExtra(DOCTOR_ID);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        loadDoctorData();
    }

    private void loadDoctorData() {
        StateExecuterKt.setState(binding.layout, StatesConstants.LOADING_STATE);
        Map<String,String> map= new HashMap<>();
        map.put("token",CacheUtils.getUserToken(this,PATIENT_DATA));
        map.put("id",doctorId);
        service.getDoctorData(map).enqueue(new Callback<DoctorDataResponse>() {
            @Override
            public void onResponse(Call<DoctorDataResponse> call, Response<DoctorDataResponse> response) {
                StateExecuterKt.setState(binding.layout, StatesConstants.NORMAL_STATE);
                if (response.body().getStatus().equals("200")){
                    Glide.with(ReservationActivity.this).load(response.body().getData().get(0).getImg()).into(binding.doctorImage);
                    binding.doctorName.setText(response.body().getData().get(0).getName());
                    binding.doctorSpec.setText(response.body().getData().get(0).getSpecialtyName());
                    binding.numberReviews.setText("("+response.body().getData().get(0).getEvaluation().getCount()+" Review)");
                    binding.doctorRate.setRating(response.body().getData().get(0).getEvaluation().getRate());
                    binding.doctorNote.setText(response.body().getData().get(0).getNote());
                }
            }

            @Override
            public void onFailure(Call<DoctorDataResponse> call, Throwable t) {
                View v= StateExecuterKt.setState(binding.layout, StatesConstants.ERROR_STATE);
                Button errorBt = v.findViewById(R.id.textButton);
                errorBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadDoctorData();
                    }
                });
            }
        });
    }


    public void confirmReservation(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ConfirmReservationDialog editNameDialogFragment = ConfirmReservationDialog.newInstance("");
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void review(View view) {
        FragmentManager fm = getSupportFragmentManager();
        PatientReviewDialog editNameDialogFragment = PatientReviewDialog.newInstance(doctorId);
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
        Intent intent = new Intent(this,DoctorReviewsActivity.class);
        intent.putExtra(DOCTOR_ID,doctorId);
        startActivity(intent);
    }

    public void location(View view) {

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        Toast.makeText(ReservationActivity.this,"Done",Toast.LENGTH_LONG).show();
                        sendCurrentLocation();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//                        super.onDenied(context, deniedPermissions);
                        Toast.makeText(ReservationActivity.this,"Denied",Toast.LENGTH_LONG).show();
                    }
                }

        );
    }
    private FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint("MissingPermission")
    private void sendCurrentLocation() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager != null) {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Utilities.showBasicDialog(this, getString(R.string.enable_gps)
                        , getString(R.string.enable_gps_message)
                        , getString(R.string.settings), getString(R.string.cancel)
                        , positiveEnableGPS
                        , cancelDialog);
            } else{
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            Log.e("lat",location.getLatitude()+" : "+location.getLongitude());
                            Intent intent=new Intent(new Intent(ReservationActivity.this,MapActivity.class));
                            intent.putExtra("lat",location.getLatitude());
                            intent.putExtra("lan",location.getLongitude());

                            startActivity(intent);

                        }
                        else{
//                            requestLocationUpdates();
                        }

                    }
                });
            }
        } else
            Utilities.showShortToast(this, "something_went_wrong");

    }


    private DialogInterface.OnClickListener positiveEnableGPS = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    };




    private DialogInterface.OnClickListener cancelDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    };

}
