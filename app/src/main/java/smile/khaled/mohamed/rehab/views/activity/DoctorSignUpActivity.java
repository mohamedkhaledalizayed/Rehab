package smile.khaled.mohamed.rehab.views.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ActivityDoctorSignUpBinding;
import smile.khaled.mohamed.rehab.service.responses.PictureProperities;
import smile.khaled.mohamed.rehab.service.responses.both.registeruser.ReigsterUserResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.FileUtils;
import smile.khaled.mohamed.rehab.utils.ValidateUtils;

import static smile.khaled.mohamed.rehab.data.Constants.ACCOUNT_TYPE;
import static smile.khaled.mohamed.rehab.data.Constants.BACKGROUND_COLOR_DARCK;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_ACCOUNT;
import static smile.khaled.mohamed.rehab.data.Constants.FILE_PICKER_REQUEST_CODE_CARD_HEALTH;
import static smile.khaled.mohamed.rehab.data.Constants.FILE_PICKER_REQUEST_CODE_IDENTITY;
import static smile.khaled.mohamed.rehab.data.Constants.FILE_PICKER_REQUEST_CODE_PERSONAL_PICTURE;
import static smile.khaled.mohamed.rehab.data.Constants.FILE_PICKER_REQUEST_CODE_UNIVERSTY_DEGREE;
import static smile.khaled.mohamed.rehab.data.Constants.PERMISSIONS_REQUEST_CODE;


public class DoctorSignUpActivity extends BaseActivity {


    private int REQUEST_CODE = 1;
    private String username;
    private String phone;
    private String email;
    private String password;
    private ActivityDoctorSignUpBinding binding;
    private List<PictureProperities> properities=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_sign_up);

        username = getIntent().getStringExtra("username");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");


    }




    public void next(View view) {

        final String doctorExperiences=AppUtils.getTextContent(binding.doctorExperiences);
        String gender;
        if (!AppUtils.isInternetAvailable(this)) {
            AppUtils.showErrorToast(this,"Check your internet connection");
            return;
        }

        if (ValidateUtils.missingInputs(doctorExperiences)) {
            AppUtils.showErrorToast(this,"Check your username and password");
            return;
        }

//        if (binding.citySpinner.getSelectedItemPosition() == 0) {
//            AppUtils.showInfoToast(this,"select your city");
//            return;
//        }
//
//        if (binding.districSpinner.getSelectedItemPosition() == 0) {
//            AppUtils.showInfoToast(this,"select your Destric");
//            return;
//        }
//
//        if (binding.specSpinner.getSelectedItemPosition() == 0) {
//            AppUtils.showInfoToast(this,"select your specialty");
//            return;
//        }
//
//        if (binding.genderSpinner.getSelectedItemPosition() == 0) {
//            AppUtils.showInfoToast(this,"select your city");
//            return;
//        }
//
//        if (binding.natSpinner.getSelectedItemPosition() == 0) {
//            AppUtils.showInfoToast(this,"select your city");
//            return;
//        }

        if (properities.size() != 4){
            AppUtils.showInfoToast(this," Upload All Files");
            return;
        }

//        if (binding.genderSpinner.getSelectedItemPosition() == 1){
//            gender = "0";
//        }else {
//            gender = "1";
//        }

        List<MultipartBody.Part> parts = new ArrayList<>();

        RequestBody nameBody = createPartFromString(doctorExperiences);
        RequestBody usernameBody = createPartFromString(username);
        RequestBody passwordBody = createPartFromString(password);
        RequestBody genderBody = createPartFromString("0");
        RequestBody mobileBody = createPartFromString("996"+phone);
        RequestBody emailBody = createPartFromString(email);
//        RequestBody cityBody = createPartFromString(binding.citySpinner.getSelectedItem().toString());
//        RequestBody neighborhoodBody = createPartFromString(binding.districSpinner.getSelectedItem().toString());
        RequestBody cityBody = createPartFromString("2");
        RequestBody neighborhoodBody = createPartFromString("3");
        if (properities != null) {
            for (int i = 0; i < 4; i++) {
                parts.add(prepareFilePart(properities.get(i).getFileName(), properities.get(0).getFileUri()));
            }
        }

        service.signUpDoctorApi(nameBody,usernameBody,passwordBody,
                genderBody,mobileBody,emailBody,cityBody,neighborhoodBody,parts)
                .enqueue(new Callback<ReigsterUserResponse>() {
            @Override
            public void onResponse(Call<ReigsterUserResponse> call, Response<ReigsterUserResponse> response) {
                if (response.body().getStatus().equals("201")){
                    Intent intent =new Intent(DoctorSignUpActivity.this,ActivateAccountActivity.class);
                    intent.putExtra("token",response.body().getData().getToken());
                    intent.putExtra(ACCOUNT_TYPE,DOCTOR_ACCOUNT);
                    startActivity(intent);
                    finish();
                }else if (response.body().getStatus().equals("400")){
                    AppUtils.showInfoToast(DoctorSignUpActivity.this,"User registered before");
                }else {
                    AppUtils.showInfoToast(DoctorSignUpActivity.this,"Check Your Data");
                }
            }

            @Override
            public void onFailure(Call<ReigsterUserResponse> call, Throwable t) {
                Log.e("error",t.getMessage());
                AppUtils.showErrorToast(DoctorSignUpActivity.this,t.getMessage());
            }
        });
    }


    @NonNull
    private RequestBody createPartFromString(String content) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, content);
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



    public void uploadIdentity(View view) {
        REQUEST_CODE = FILE_PICKER_REQUEST_CODE_IDENTITY;
        checkPermissionsAndOpenFilePicker();
    }

    public void uploadUniversityDegree(View view) {
        REQUEST_CODE = FILE_PICKER_REQUEST_CODE_UNIVERSTY_DEGREE;
        checkPermissionsAndOpenFilePicker();
    }

    public void uploadCardHealth(View view) {
        REQUEST_CODE = FILE_PICKER_REQUEST_CODE_CARD_HEALTH;
        checkPermissionsAndOpenFilePicker();
    }

    public void uploadPersonalPicture(View view) {
        REQUEST_CODE = FILE_PICKER_REQUEST_CODE_PERSONAL_PICTURE;
        checkPermissionsAndOpenFilePicker();
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "jjj");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && REQUEST_CODE != 1
                ) {

            Log.e("Lionel",data.getData()+"");

                if (REQUEST_CODE==FILE_PICKER_REQUEST_CODE_IDENTITY){
                    properities.add(new PictureProperities("nationality",data.getData()));
                    binding.identity.setImageResource(R.drawable.ic_check_circle_black_24dp);
                } else if (REQUEST_CODE==FILE_PICKER_REQUEST_CODE_UNIVERSTY_DEGREE){
                    properities.add(new PictureProperities("university_degree",data.getData()));
                    binding.universityDegree.setImageResource(R.drawable.ic_check_circle_black_24dp);
                } else if (REQUEST_CODE==FILE_PICKER_REQUEST_CODE_CARD_HEALTH){
                    properities.add(new PictureProperities("specialty",data.getData()));
                    binding.cardHealthSpecialties.setImageResource(R.drawable.ic_check_circle_black_24dp);
                } else if (REQUEST_CODE==FILE_PICKER_REQUEST_CODE_PERSONAL_PICTURE){
                    properities.add(new PictureProperities("image",data.getData()));
                    binding.personalPicture.setImageResource(R.drawable.ic_check_circle_black_24dp);
                }

        }
    }
}
