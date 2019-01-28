package smile.khaled.mohamed.rehab.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.widget.RecyclerView;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.service.responses.both.allmessges.DataItem;
import smile.khaled.mohamed.rehab.views.fragment.Favourite;
import smile.khaled.mohamed.rehab.views.interfaces.both.IMessageHandler;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<DataItem> recentList;
    private Context context;
        private IMessageHandler handler;
    public MessagesAdapter(Context context, List<DataItem> recentList) {
        this.recentList = recentList;
        this.context=context;
        handler=(IMessageHandler)context;
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
        if (item.getStatus().equals("0")){
            holder.name.setTypeface(null,Typeface.BOLD);
            holder.time.setTypeface(null,Typeface.BOLD);
        }
        holder.viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClick(item.getId(),item.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView name;
        public View viewItem;
        public MyViewHolder(View view) {
            super(view);
            time=view.findViewById(R.id.message_time);
            name=view.findViewById(R.id.message_name);
            viewItem=view.findViewById(R.id.message_item);
        }
    }

}
