package com.main.lutemon12;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class LutemonDetailDialog extends DialogFragment {
    public interface OnDeleteListener {
        void onDeleteConfirmed(int lutemonId);
    }

    private final Lutemon lutemon;
    private final OnDeleteListener deleteListener;

    public LutemonDetailDialog(Lutemon lutemon, OnDeleteListener listener) {
        this.lutemon = lutemon;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_lutemon_detail, null);

        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtAttack = view.findViewById(R.id.txtAttack);
        TextView txtDefense = view.findViewById(R.id.txtDefense);
        TextView txtHealth = view.findViewById(R.id.txtHealth);
        TextView txtWins = view.findViewById(R.id.txtWins);
        TextView txtLosses = view.findViewById(R.id.txtLosses);
        TextView txtWinRate = view.findViewById(R.id.txtWinRate);

        // ATTRIBUTE
        imgAvatar.setImageResource(getAvatarResource(lutemon.getColor()));
        txtName.setText(lutemon.getName());
        txtAttack.setText("ATK: " + lutemon.getCurrentAttack());
        txtDefense.setText("DEF: " + lutemon.getDefense());
        txtHealth.setText("HP: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
        txtWins.setText("WIN: " + lutemon.getWins());
        txtLosses.setText("LOSE: " + lutemon.getLosses());
        int totalBattles = lutemon.getWins() + lutemon.getLosses();
        float winRate = totalBattles > 0 ? (float) lutemon.getWins() / totalBattles * 100 : 0;
        txtWinRate.setText(String.format("WR: %.1f%%", winRate));

        view.findViewById(R.id.btnDelete).setOnClickListener(v -> showDeleteConfirmation());

        builder.setView(view);
        return builder.create();
    }

    private int getAvatarResource(String color) {
        switch (color) {
            case "Red":
                return R.drawable.ic_lutemon_red;
            case "Blue":
                return R.drawable.ic_lutemon_blue;
            case "White":
                return R.drawable.ic_lutemon_white;
            case "Green":
                return R.drawable.ic_lutemon_green;
            case "Pink":
                return R.drawable.ic_lutemon_pink;
            case "Orange":
                return R.drawable.ic_lutemon_orange;
            case "Black":
                return R.drawable.ic_lutemon_black;
            default:
                return R.drawable.ic_lutemon_default;
        }
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete " + lutemon.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (deleteListener != null) {
                        deleteListener.onDeleteConfirmed(lutemon.getId());
                    }
                    dismiss();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
