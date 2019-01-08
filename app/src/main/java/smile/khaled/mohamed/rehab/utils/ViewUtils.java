package smile.khaled.mohamed.rehab.utils;

import android.view.View;
import com.tripl3dev.prettystates.StateExecuterKt;
import com.tripl3dev.prettystates.StatesConstants;

import smile.khaled.mohamed.rehab.R;

import static smile.khaled.mohamed.rehab.data.Constants.SPINNER_ERROR;
import static smile.khaled.mohamed.rehab.data.Constants.SPINNER_LOADING;

public class ViewUtils {

  public static void showLoading(View v) {
    StateExecuterKt.setState(v, StatesConstants.LOADING_STATE);
  }

  public static void showError(View v, OnRetryClicked onRetryListener) {
    View view = StateExecuterKt.setState(v, StatesConstants.ERROR_STATE);
    view.findViewById(R.id.textButton).setOnClickListener(v1 -> {
      onRetryListener.onRetryClicked();
    });
  }

  public static void showNormal(View v) {
    StateExecuterKt.setState(v, StatesConstants.NORMAL_STATE);
  }

  public static void showSpinnerLoading(View v) {
    StateExecuterKt.setState(v, SPINNER_LOADING);
  }

  public static void showSpinnerError(View v, OnRetryClicked onRetryListener) {
    View view = StateExecuterKt.setState(v, SPINNER_ERROR);
    view.findViewById(R.id.errorBt).setOnClickListener(
        v1 -> onRetryListener.onRetryClicked());
  }

  public interface OnRetryClicked {
    public void onRetryClicked();
  }
}
