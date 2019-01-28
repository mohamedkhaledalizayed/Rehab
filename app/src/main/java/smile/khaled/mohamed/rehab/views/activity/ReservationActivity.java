package smile.khaled.mohamed.rehab.views.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.databinding.ActivityReservationBinding;
import smile.khaled.mohamed.rehab.service.responses.patient.reservation.DoctorDateResponse;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorprofile.DoctorDataResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.FileUtils;
import smile.khaled.mohamed.rehab.views.dialog.patient.PatientReviewDialog;
import smile.khaled.mohamed.rehab.views.dialog.patient.SendMessageDialog;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ID;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.PERMISSIONS_REQUEST_CODE;

public class ReservationActivity extends BaseActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityReservationBinding binding;
    private String doctorId;
    private String doctorNumber;
    private Uri imageUri;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.reservation_);


        doctorId = getIntent().getStringExtra(DOCTOR_ID);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        loadDoctorData();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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
                    doctorNumber=response.body().getData().get(0).getMobile();
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
        reservation();
//        FragmentManager fm = getSupportFragmentManager();
//        ConfirmReservationDialog editNameDialogFragment = ConfirmReservationDialog.newInstance("");
//        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
//        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void review(View view) {
        FragmentManager fm = getSupportFragmentManager();
        PatientReviewDialog editNameDialogFragment = PatientReviewDialog.newInstance(doctorId);
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void sendMessage(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SendMessageDialog editNameDialogFragment = SendMessageDialog.newInstance(doctorId,"0");
        editNameDialogFragment.show(fm, "Confirm Reservation Dialog");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    public void call(View view) {
        AppUtils.call(this,doctorNumber);
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

                            startActivityForResult(intent,GET_CURRENT_LOCATION_REQUEST);

                        }
                        else{
                            requestLocationUpdates();
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

    private void reservation(){

        MultipartBody.Part parts = null;

        RequestBody type = createPartFromString("set");
        RequestBody token = createPartFromString(CacheUtils.getUserToken(this,PATIENT_DATA));
        RequestBody doctor_id = createPartFromString("2");
        RequestBody resdate = createPartFromString("2019-01-29");
        RequestBody restime = createPartFromString("9:30:00");
        RequestBody note = createPartFromString("mmmmmm");
        RequestBody lat = createPartFromString("8776990");
        RequestBody lon = createPartFromString("40998765");
        RequestBody address = createPartFromString("jjjjjjjj");
        RequestBody schedule_id = createPartFromString("11");


//        parts=prepareFilePart("status", imageUri);


        service.makeReservation(type,token,doctor_id,resdate,restime,note,lat,lon,address,schedule_id,parts).enqueue(new Callback<DoctorDateResponse>() {
            @Override
            public void onResponse(Call<DoctorDateResponse> call, Response<DoctorDateResponse> response) {
                if (response.body().getStatus().equals("200")){
                    AppUtils.showSuccessToast(ReservationActivity.this,"تم تاكيد الحجز");
                }else {
                    AppUtils.showErrorToast(ReservationActivity.this,"لم يتم تاكيد الحجز");
                }
            }

            @Override
            public void onFailure(Call<DoctorDateResponse> call, Throwable t) {
                AppUtils.showErrorToast(ReservationActivity.this,"لم يتم تاكيد الحجز");
            }
        });

    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private RequestBody createPartFromString(String content) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, content);
    }
    private int REQUEST_CODE = 101;

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, "jjj");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            showChooser();
        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showChooser();
                } else {
                    showError();
                }
            }
        }
    }


    private double lat;
    private double lan;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Log.e("Lionel", data.getData() + "");
            imageUri = data.getData();
        }else if (requestCode == GET_CURRENT_LOCATION_REQUEST && resultCode == RESULT_OK && data != null) {
            lat = data.getDoubleExtra("lat",30.45633);
            lan = data.getDoubleExtra("lan",30.45633);
            binding.location.setText("( "+lat+":"+lan+" )");
        }
    }

    private int GET_CURRENT_LOCATION_REQUEST = 115;
    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        Log.e("lat",location.getLatitude()+" : "+location.getLongitude());
                        Intent intent=new Intent(new Intent(ReservationActivity.this,MapActivity.class));
                        intent.putExtra("lat",location.getLatitude());
                        intent.putExtra("lan",location.getLongitude());

                        startActivityForResult(intent,GET_CURRENT_LOCATION_REQUEST);
                        if (fusedLocationProviderClient != null)
                            fusedLocationProviderClient.removeLocationUpdates(locationCallback);

                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public void getDate(View view) {
    }
}
