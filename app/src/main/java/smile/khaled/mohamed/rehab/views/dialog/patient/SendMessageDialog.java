package smile.khaled.mohamed.rehab.views.dialog.patient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smile.khaled.mohamed.rehab.R;

public class SendMessageDialog  extends DialogFragment {


    public static SendMessageDialog newInstance(String title) {
        SendMessageDialog frag = new SendMessageDialog();
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
        View view = inflater.inflate(R.layout.send_message_dialog, container, false);

        // Do all the stuff to initialize your custom view

        return view;
    }
}
