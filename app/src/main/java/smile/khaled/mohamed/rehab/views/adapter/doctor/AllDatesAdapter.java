package smile.khaled.mohamed.rehab.views.adapter.doctor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.responses.doctor.getalldates.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.IFavouriteHandler;
import smile.khaled.mohamed.rehab.views.interfaces.doctor.IDoctorDataHandler;

public class AllDatesAdapter extends RecyclerView.Adapter<AllDatesAdapter.MyViewHolder> {

    private List<DataItem> dataItemList;
    private Context context;
    private IDoctorDataHandler handler;
    public AllDatesAdapter(Context context, List<DataItem> dataItemList) {
        this.dataItemList = dataItemList;
        this.context=context;
        handler=(IDoctorDataHandler)context;
    }

    @Override
    public AllDatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_item, parent, false);

        return new AllDatesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllDatesAdapter.MyViewHolder holder, final int position) {
        final DataItem item = dataItemList.get(position);
        holder.time.setText(item.getTime());

        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClick(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("ccoouunntt",dataItemList.size()+"");
        return dataItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;


        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);

        }
    }

}