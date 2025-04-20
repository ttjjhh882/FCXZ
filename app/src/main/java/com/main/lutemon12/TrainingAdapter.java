package com.main.lutemon12;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {
    private ArrayList<Lutemon> lutemons;
    private Context context;

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
    public TrainingAdapter(ArrayList<Lutemon> lutemons, Context context) {
        this.lutemons = lutemons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon lutemon = lutemons.get(position);
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

        holder.textName.setText(lutemon.getName());
        holder.textAttack.setText("ATK: " + lutemon.getCurrentAttack());
        holder.textDefense.setText("DEF: " + lutemon.getDefense());
        holder.textHealth.setText("HP: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
        holder.textExperience.setText("EXP: " + lutemon.getExperience());


        holder.btnTrain.setOnClickListener(v -> {
            MediaPlayer.create(context, R.raw.train_sound).start();

            // DISPLAY TRAINING DIALOG
            TrainingDialog dialog = new TrainingDialog();
            dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "training_dialog");

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                String attribute = lutemon.train();
                Storage.getInstance().updateLutemon(lutemon);

                // UPDATE
                notifyItemChanged(position);
                Toast.makeText(context, "TRAINING COMPLETE! " + attribute + "+1",
                        Toast.LENGTH_SHORT).show();
            }, 2500);
        });

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLutemon;
        TextView textName, textAttack, textDefense, textHealth, textExperience;
        Button btnTrain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLutemon = itemView.findViewById(R.id.imageLutemon);
            textName = itemView.findViewById(R.id.textName);
            textAttack = itemView.findViewById(R.id.textAttack);
            textDefense = itemView.findViewById(R.id.textDefense);
            textHealth = itemView.findViewById(R.id.textHealth);
            textExperience = itemView.findViewById(R.id.textExperience);
            btnTrain = itemView.findViewById(R.id.btnTrain);
        }
    }
}
