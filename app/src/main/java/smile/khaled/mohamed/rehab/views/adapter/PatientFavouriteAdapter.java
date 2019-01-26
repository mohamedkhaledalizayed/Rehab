package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.responses.patient.favourites.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;
import smile.khaled.mohamed.rehab.views.fragment.IFavouriteHandler;
import smile.khaled.mohamed.rehab.views.fragment.PatientFavouriteFragment;

public class PatientFavouriteAdapter extends RecyclerView.Adapter<PatientFavouriteAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
    private IFavouriteHandler handler;
    public PatientFavouriteAdapter(Context context, List<DataItem> recentList) {
        this.recentList = recentList;
        this.context=context;
        handler=(IFavouriteHandler) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DataItem price = recentList.get(position);
        holder.name.setText(price.getName());
        holder.spci.setText(price.getSpecialty());
//        Picasso.with(context).load(price.getImg()).into(holder.photo);
        Glide.with(context).load(price.getImg()).into(holder.photo);
        holder.note.setText(price.getNote());
        holder.ratingBar.setRating(price.getEvaluation().getRate());

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onClick(price.getId());
                recentList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView spci;
        public TextView note;
        public ImageView photo;
        public ImageView fav;
        public MaterialRatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            spci = view.findViewById(R.id.spec);
            note = view.findViewById(R.id.note);
            photo=view.findViewById(R.id.doctor_image);
            fav=view.findViewById(R.id.fav);
            ratingBar=view.findViewById(R.id.rate);
        }
    }

}