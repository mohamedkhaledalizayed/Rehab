package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<Favourite> recentList;
    private Context context;
    //    private ICategoryHandler handler;
    public MessagesAdapter(Context context, List<Favourite> recentList) {
        this.recentList = recentList;
        this.context=context;
//        handler=(ICategoryHandler)context;
    }

    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        return new MessagesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MessagesAdapter.MyViewHolder holder, final int position) {
//        final Favourite price = recentList.get(position);
//        holder.views.setText(price.getViews());
//        Picasso.with(context).load(price.getPhoto()).into(holder.photo);
//
//        holder.photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handler.onClickItem(price.getId());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView views;
        public ImageView photo;

        public MyViewHolder(View view) {
            super(view);
//            views = view.findViewById(R.id.num_view);
//            photo=view.findViewById(R.id.item_bg);
        }
    }

}