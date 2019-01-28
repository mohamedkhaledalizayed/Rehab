package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.SearchDoctorItemBinding;
import smile.khaled.mohamed.rehab.service.responses.patient.doctorfilter.DataItem;
import smile.khaled.mohamed.rehab.views.activity.ISearchResultHandler;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

public class PatientSearchResultAdapter extends RecyclerView.Adapter<PatientSearchResultAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
    private LayoutInflater layoutInflater;
        private ISearchResultHandler handler;
    public PatientSearchResultAdapter(Context context, List<DataItem> recentList) {
        this.recentList = recentList;
        this.context=context;
        handler=(ISearchResultHandler)context;
    }


    @Override
    public PatientSearchResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        SearchDoctorItemBinding binding=
                DataBindingUtil.inflate(layoutInflater, R.layout.search_doctor_item, parent, false);

        return new PatientSearchResultAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final PatientSearchResultAdapter.MyViewHolder holder, final int position) {
        holder.binding.setUser(recentList.get(position));
        holder.binding.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onResevationClick(recentList.get(position).getId());
            }
        });

        Log.e("number",recentList.get(position).getFavId()+" "+recentList.get(position).getId());
        if (recentList.get(position).getFavId().equals("1")){
            holder.binding.favourite.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }

        holder.binding.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recentList.get(position).getFavId().equals("1")){
                    handler.deleteFavourite(recentList.get(position).getId());
                }else {
                    handler.addFavourite(recentList.get(position).getId());
                }
            }
        });

        holder.binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onShareClick(recentList.get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SearchDoctorItemBinding binding;
        public MyViewHolder(SearchDoctorItemBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }

}