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
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
    //    private ICategoryHandler handler;
    public MessagesAdapter(Context context, List<DataItem> recentList) {
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
        DataItem item=recentList.get(position);
        holder.name.setText(item.getSenderName());
        holder.time.setText(item.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            time=view.findViewById(R.id.message_time);
            name=view.findViewById(R.id.message_name);
        }
    }

}
