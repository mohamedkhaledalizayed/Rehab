package smile.khaled.mohamed.rehab.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.widget.TextView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.Constants;


public class AppUtils {

    private static KProgressHUD progressHUD;


    public static void setHtmlText(@StringRes int text, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(textView.getContext().getString(text), Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(textView.getContext().getString(text)));
        }
    }

    public static void call(Context context,String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showToast(Context context, String text, @ColorRes int textColor,
                                  @ColorRes int backgroundColor, int icon, Typeface font) {
        final StyleableToast.Builder builder = new StyleableToast
                .Builder(context)
                .text(text)
                .textColor(context.getResources().getColor(textColor))
                .backgroundColor(context.getResources().getColor(backgroundColor));
        if (font != null) {
            builder.typeface(font);
        }
        if (icon != 0) {
            builder.icon(icon);
        }
        builder.build().show();
    }

    public static void showInfoToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.info_toast_color, 0, null);
    }

    public static void showErrorToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.fail_toast_color, 0, null);
    }

    public static void showSuccessToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.success_toast_color, 0, null);
    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }



    public static String getTextContent(TextView textView) {
        return textView.getText().toString().trim();
    }




    public static boolean isInternetAvailable(Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        boolean is=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean i=connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        if (i){
            return true;
        } else if (is){
            return true;
        } else {
            return false;
        }

    }

    public static void applyFontToMenuItem(Context context, MenuItem mi) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Cairo-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

}
