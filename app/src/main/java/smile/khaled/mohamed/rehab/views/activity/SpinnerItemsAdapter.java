package smile.khaled.mohamed.rehab.views.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.Constants;

public class SpinnerItemsAdapter extends BaseAdapter {

    private List<String> items ;
    private int bgType ;
    public SpinnerItemsAdapter(List<String> items, int bgType){
        this.items=items;
        this.bgType=bgType;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            if (bgType==Constants.BACKGROUND_COLOR_DARCK){
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.spinner_item_layout_darck, viewGroup, false);
            }else {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.spinner_item_layout_light, viewGroup, false);
            }
            textView = (TextView) view;
        } else {
            textView = (TextView) view;
        }
        textView.setText(getItem(i).toString());
        return textView;
    }
}
