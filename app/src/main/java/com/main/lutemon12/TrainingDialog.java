package com.main.lutemon12;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TrainingDialog extends DialogFragment {

    private ProgressBar progressBar;
    private ValueAnimator progressAnimator;
    private ImageView imgLutemon;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_training, null);

        // INIT
        progressBar = view.findViewById(R.id.progressBar);
        imgLutemon = view.findViewById(R.id.imgLutemon);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        startAnimations();
        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            window.setLayout(600, 400);
            window.setGravity(Gravity.CENTER);
        }
    }


    private void startAnimations() {
        // SHAKE
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        imgLutemon.startAnimation(shake);

        // LOADING ANI
        progressAnimator = ValueAnimator.ofInt(0, 100);
        progressAnimator.setDuration(2500);
        progressAnimator.addUpdateListener(anim -> {
            int value = (int) anim.getAnimatedValue();
            progressBar.setProgress(value);
        });
        progressAnimator.start();

        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
                if(getActivity() instanceof TrainingCompleteListener){
                    ((TrainingCompleteListener)getActivity()).onTrainingComplete();
                }
            }
        });
    }

    public interface TrainingCompleteListener {
        void onTrainingComplete();
    }
}
