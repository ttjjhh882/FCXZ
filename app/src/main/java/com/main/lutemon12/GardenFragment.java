package com.main.lutemon12;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class GardenFragment extends Fragment {
    private RecyclerView recyclerView;
    private LutemonAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garden, container, false);

        // INIT RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewGarden);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Lutemon> lutemons = Storage.getInstance().getAllLutemons();
        adapter = new LutemonAdapter(lutemons, requireContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddLutemon);
        fabAdd.setOnClickListener(v -> showCreateLutemonDialog());
    }

    private void showCreateLutemonDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_lutemon, null);
        EditText editName = dialogView.findViewById(R.id.editName);
        Button btnColorRed = dialogView.findViewById(R.id.btnColorRed);
        Button btnColorBlue = dialogView.findViewById(R.id.btnColorBlue);
        Button btnColorWhite = dialogView.findViewById(R.id.btnColorWhite);
        Button btnColorGreen = dialogView.findViewById(R.id.btnColorGreen);
        Button btnColorPink = dialogView.findViewById(R.id.btnColorPink);
        Button btnColorOrange = dialogView.findViewById(R.id.btnColorOrange);
        Button btnColorBlack = dialogView.findViewById(R.id.btnColorBlack);

        AtomicReference<String> selectedColor = new AtomicReference<>("");

        View.OnClickListener colorClickListener = v -> {
            resetAllColorButtons(dialogView);
            if (v.getId() == R.id.btnColorRed) {
                selectedColor.set("Red");
                btnColorRed.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorBlue) {
                selectedColor.set("Blue");
                btnColorBlue.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorWhite) {
                selectedColor.set("White");
                btnColorWhite.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorGreen) {
                selectedColor.set("Green");
                btnColorGreen.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorPink) {
                selectedColor.set("Pink");
                btnColorPink.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorOrange) {
                selectedColor.set("Orange");
                btnColorOrange.setBackgroundResource(R.drawable.bg_selected_color);
            } else if (v.getId() == R.id.btnColorBlack) {
                selectedColor.set("Black");
                btnColorBlack.setBackgroundResource(R.drawable.bg_selected_color);
            }
        };

        btnColorRed.setOnClickListener(colorClickListener);
        btnColorBlue.setOnClickListener(colorClickListener);
        btnColorWhite.setOnClickListener(colorClickListener);
        btnColorGreen.setOnClickListener(colorClickListener);
        btnColorPink.setOnClickListener(colorClickListener);
        btnColorOrange.setOnClickListener(colorClickListener);
        btnColorBlack.setOnClickListener(colorClickListener);

        // DIALOG
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Creating a New Lutemon")
                .setView(dialogView)
                .setPositiveButton("YES", (dialogInterface, which) -> {
                    String name = editName.getText().toString().trim();
                    String color = selectedColor.get();

                    if (name.isEmpty() || color.isEmpty()) {
                        Toast.makeText(getContext(), "Please enter a name and select a color", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Lutemon newLutemon;
                    switch (color) {
                        case "Red":
                            newLutemon = new FireLutemon(Storage.getNextId(), name);
                            break;
                        case "Blue":
                            newLutemon = new WaterLutemon(Storage.getNextId(), name);
                            break;
                        case "White":
                            newLutemon = new WhiteLutemon(Storage.getNextId(), name);
                            break;
                        case "Green":
                            newLutemon = new GreenLutemon(Storage.getNextId(), name);
                            break;
                        case "Pink":
                            newLutemon = new PinkLutemon(Storage.getNextId(), name);
                            break;
                        case "Orange":
                            newLutemon = new OrangeLutemon(Storage.getNextId(), name);
                            break;
                        case "Black":
                            newLutemon = new BlackLutemon(Storage.getNextId(), name);
                            break;
                        default:
                            newLutemon = new FireLutemon(Storage.getNextId(), name);
                    }

                    Storage.getInstance().addLutemon(newLutemon);
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void resetAllColorButtons(View dialogView) {
        int[] colorButtonIds = {
                R.id.btnColorRed, R.id.btnColorBlue,
                R.id.btnColorWhite, R.id.btnColorGreen,
                R.id.btnColorPink, R.id.btnColorOrange,
                R.id.btnColorBlack
        };
        for (int id : colorButtonIds) {
            Button btn = dialogView.findViewById(id);
            btn.setBackground(null);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
