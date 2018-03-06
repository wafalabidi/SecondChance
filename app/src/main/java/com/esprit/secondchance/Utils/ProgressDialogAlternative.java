package com.esprit.secondchance.Utils;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.esprit.secondchance.R;

/**
 * Created by sofien on 13/01/2018.
 */

public class ProgressDialogAlternative {
    static Dialog dialog;
    static LottieAnimationView animationView ;
   public static void ShowDIalog(Context context) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wait_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        animationView = dialog.findViewById(R.id.animation_view);
        animationView.loop(false);
        dialog.show();
    }

    public static void Dissmiss() {
        animationView.cancelAnimation();
        animationView.setVisibility(View.GONE);
        //dialog.hide();
        dialog.dismiss();
    }


}
