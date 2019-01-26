package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

        import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.responses.patient.dates.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;
import smile.khaled.mohamed.rehab.views.fragment.IPatientDateHandler;

public class PatientDatesAdapter extends RecyclerView.Adapter<PatientDatesAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
    private IPatientDateHandler handler;
    public PatientDatesAdapter(Context context, List<DataItem> recentList) {
        this.recentList = recentList;
        this.context=context;
        handler=(IPatientDateHandler)context;
    }

    @Override
    public PatientDatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dates_item, parent, false);

        return new PatientDatesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PatientDatesAdapter.MyViewHolder holder, final int position) {
        DataItem item=recentList.get(position);
        holder.name.setText(item.getDoctorData().get(0).getName());
        holder.spec.setText(item.getDoctorData().get(0).getSpecialty());
        holder.date.setText(item.getResdate());
        holder.time.setText(item.getRestime());
        holder.cost.setText(item.getDoctorData().get(0).getCost());

        Glide.with(context).load(item.getDoctorData().get(0).getImg()).into(holder.photo);

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClickCanceled(item.getId());
                recentList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClickCall(item.getDoctorData().get(0).getMobile());
            }
        });

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClickMessage(item.getDoctorData().get(0).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView spec;
        public TextView date;
        public TextView time;
        public TextView cost;
        public ImageView photo;
        public Button cancel;
        public Button call;
        public Button message;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            spec = view.findViewById(R.id.spec);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            cost = view.findViewById(R.id.cost);
            photo=view.findViewById(R.id.doctor_image);
            cancel=view.findViewById(R.id.cancel);
            call=view.findViewById(R.id.call);
            message=view.findViewById(R.id.message);
        }
    }

}