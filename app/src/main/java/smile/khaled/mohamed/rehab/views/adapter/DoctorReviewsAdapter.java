package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

        import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.databinding.ReviewItemBinding;
import smile.khaled.mohamed.rehab.service.responses.patient.reviews.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

public class DoctorReviewsAdapter extends RecyclerView.Adapter<DoctorReviewsAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
    private LayoutInflater layoutInflater;

    public DoctorReviewsAdapter(Context context, List<DataItem> recentList) {
        this.recentList = recentList;
        this.context=context;
    }

    @Override
    public DoctorReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ReviewItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.review_item, parent, false);


        return new DoctorReviewsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DoctorReviewsAdapter.MyViewHolder holder, final int position) {
        holder.binding.setReview(recentList.get(position));
        holder.binding.rateBar.setRating(Float.parseFloat(recentList.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ReviewItemBinding binding;
        public MyViewHolder(ReviewItemBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }

}