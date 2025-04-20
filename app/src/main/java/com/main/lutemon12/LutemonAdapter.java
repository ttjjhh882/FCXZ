package com.main.lutemon12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {
    private ArrayList<Lutemon> lutemons;
    private Context context;

    public LutemonAdapter(ArrayList<Lutemon> lutemons, Context context) {
        this.lutemons = lutemons;
        this.context = context;
    }

    //ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lutemon, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon lutemon = lutemons.get(position);
        holder.textName.setText(lutemon.getName());
        holder.textColor.setText("COLOR: " + lutemon.getColor());
        holder.textLevel.setText("EXP: " + lutemon.getExperience());
        holder.textHP.setText("HP: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());

        switch (lutemon.getColor()) {
            case "Red":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_red);
                break;
            case "Blue":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_blue);
                break;
            case "White":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_white);
                break;
            case "Green":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_green);
                break;
            case "Pink":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_pink);
                break;
            case "Orange":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_orange);
                break;
            case "Black":
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_black);
                break;
            default:
                holder.imageLutemon.setImageResource(R.drawable.ic_lutemon_default);
        }

        holder.itemView.setOnClickListener(v -> {
            LutemonDetailDialog dialog = new LutemonDetailDialog(
                    lutemon,
                    new LutemonDetailDialog.OnDeleteListener() {
                        @Override
                        public void onDeleteConfirmed(int lutemonId) {
                            int pos = findPositionById(lutemonId);
                            if (pos != -1) {
                                lutemons.remove(pos);
                                notifyItemRemoved(pos);
                                Storage.getInstance().removeLutemon(lutemonId);
                            }
                        }
                    }
            );
            dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "detail_dialog");
        });
    }

    private int findPositionById(int id) {
        for (int i = 0; i < lutemons.size(); i++) {
            if (lutemons.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLutemon;
        TextView textName, textColor, textLevel, textHP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLutemon = itemView.findViewById(R.id.imageLutemon);
            textName = itemView.findViewById(R.id.textName);
            textColor = itemView.findViewById(R.id.textColor);
            textLevel = itemView.findViewById(R.id.textLevel);
            textHP = itemView.findViewById(R.id.textHP);
        }
    }
}
