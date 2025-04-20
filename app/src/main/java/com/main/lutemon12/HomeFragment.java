package com.main.lutemon12;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TextView tvTime, tvGreeting, tvLuckyColor;
    private ImageView ivColorSymbol;
    private final Handler timeHandler = new Handler();
    private final int[] colorResIds = {
            R.color.red, R.color.blue, R.color.green,
            R.color.yellow, R.color.purple
    };
    private final String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Purple"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvTime = view.findViewById(R.id.tvTime);
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvLuckyColor = view.findViewById(R.id.tvLuckyColor);
        ivColorSymbol = view.findViewById(R.id.ivColorSymbol);

        updateTime();
        setupLuckyColor();

        return view;
    }

    private void updateTime() {
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                tvTime.setText(currentTime);

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                String greeting = getGreeting(hour);
                tvGreeting.setText(greeting);

                timeHandler.postDelayed(this, 60000);
            }
        }, 0);
    }

    private String getGreeting(int hour) {
        if (hour >= 5 && hour < 12) {
            return "Good morning! Your lucky color is:";
        } else if (hour >= 12 && hour < 18) {
            return "Good afternoon! Your lucky color is:";
        } else {
            return "Good evening! Your lucky color is:";
        }
    }

    private void setupLuckyColor() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String date = sdf.format(new Date());
        int seed = Integer.parseInt(date);
        Random rand = new Random(seed);

        int index = rand.nextInt(colorResIds.length);
        int colorRes = colorResIds[index];
        String colorName = colorNames[index];


        ivColorSymbol.setColorFilter(ContextCompat.getColor(requireContext(), colorRes));
        tvLuckyColor.setText(colorName);
        tvLuckyColor.setTextColor(ContextCompat.getColor(requireContext(), colorRes));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timeHandler.removeCallbacksAndMessages(null);
    }
}