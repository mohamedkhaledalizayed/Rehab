package smile.khaled.mohamed.rehab.views.dialog.patient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.RetrofitModule;
import smile.khaled.mohamed.rehab.service.ServiceApi;
import smile.khaled.mohamed.rehab.service.responses.both.message.SendMessageResponse;
import smile.khaled.mohamed.rehab.utils.AppUtils;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR;
import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;
import static smile.khaled.mohamed.rehab.data.Constants.USER_TYPE;

public class SendMessageDialog  extends DialogFragment {


    public static ServiceApi service= RetrofitModule.getInstance().getService();
    private String message;
    private String receiverId;

    public static SendMessageDialog newInstance(String id,String message) {
        SendMessageDialog frag = new SendMessageDialog();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiverId=getArguments().getString("id");
        message=getArguments().getString("message");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_message_dialog, container, false);
        TextView textView=(TextView)view.findViewById(R.id.patients_review);

        if (message != null && !message.equals("0")){
            textView.setText(message);
        }

        Button button=view.findViewById(R.id.send_message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.getTextContent(textView)!=null && !AppUtils.getTextContent(textView).equals("")){
                    sendMessage(AppUtils.getTextContent(textView));
                }else {
                    AppUtils.showInfoToast(getActivity(),"Enter Your Message");
                }
            }
        });

        return view;
    }

    private void sendMessage(String message){

        Map<String,String> map= new HashMap<>();
        map.put("type","set");
        if (CacheUtils.checkUserType(getActivity(),USER_TYPE).equals(DOCTOR)){
            map.put("token",CacheUtils.getUserToken(getActivity(),DOCTOR_DATA));
        }else {
            map.put("token",CacheUtils.getUserToken(getActivity(),PATIENT_DATA));
        }
        map.put("receiver",receiverId);
        map.put("message",message);
        service.sendMessage(map).enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                if (response.body().getStatus().equals("200")){
                    AppUtils.showSuccessToast(getActivity(),"Message Send Successfully");
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
                AppUtils.showErrorToast(getActivity(),"Message Not Send");
            }
        });
    }
}
