package smile.khaled.mohamed.rehab.views.fragment;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Favourite {
    public String username;

    public Favourite(String username,String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
    }



    public String imageUrl;

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }


}
