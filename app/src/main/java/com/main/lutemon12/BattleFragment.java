package com.main.lutemon12;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BattleFragment extends Fragment {
    private ImageView imgAttacker, imgDefender;
    private TextView tvAttackerName, tvDefenderName;
    private TextView tvAttackerStats, tvDefenderStats;
    private TextView tvBattleLog;
    private Button btnStart, btnNext;
    private Lutemon attacker, defender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle, container, false);


        imgAttacker = view.findViewById(R.id.img_attacker);
        imgDefender = view.findViewById(R.id.img_defender);
        tvAttackerName = view.findViewById(R.id.tv_attacker_name);
        tvDefenderName = view.findViewById(R.id.tv_defender_name);
        tvAttackerStats = view.findViewById(R.id.tv_attacker_stats);
        tvDefenderStats = view.findViewById(R.id.tv_defender_stats);
        tvBattleLog = view.findViewById(R.id.tv_battle_log);
        btnStart = view.findViewById(R.id.btn_start);
        btnNext = view.findViewById(R.id.btn_next);


        view.findViewById(R.id.card_attacker).setOnClickListener(v -> selectLutemon(true));
        view.findViewById(R.id.card_defender).setOnClickListener(v -> selectLutemon(false));

        btnStart.setOnClickListener(v -> validateAndStartBattle());
        btnNext.setOnClickListener(v -> executeBattleTurn());

        return view;
    }

    private void selectLutemon(boolean isAttacker) {
        ArrayList<Lutemon> lutemons = Storage.getInstance().getAllLutemons();
        String[] names = new String[lutemons.size()];
        for (int i = 0; i < lutemons.size(); i++) {
            names[i] = lutemons.get(i).getName();
        }
        new AlertDialog.Builder(getContext())
                .setTitle("Select Lutemon: " + (isAttacker ? "Attacker" : "Defender"))
                .setItems(names, (dialog, which) -> {
                    Lutemon selected = Storage.getInstance().getAllLutemons().get(which);
                    if (isAttacker) {
                        attacker = selected;
                        updateAttackerUI();
                    } else {
                        defender = selected;
                        updateDefenderUI();
                    }
                }).show();
    }

    private void updateAttackerUI() {
        imgAttacker.setImageResource(getAvatarResource(attacker.getColor()));
        tvAttackerName.setText(attacker.getName());
        tvAttackerStats.setText(String.format(
                "ATK: %d\nDEF: %d\nHP: %d/%d\nWR: %.1f%%",
                attacker.getCurrentAttack(),
                attacker.getDefense(),
                attacker.getHealth(),
                attacker.getMaxHealth(),
                calculateWinRate(attacker)
        ));
    }

    private void updateDefenderUI() {
        imgDefender.setImageResource(getAvatarResource(defender.getColor()));
        tvDefenderName.setText(defender.getName());
        tvDefenderStats.setText(String.format(
                "ATK: %d\nDEF: %d\nHP: %d/%d\nWR: %.1f%%",
                defender.getCurrentAttack(),
                defender.getDefense(),
                defender.getHealth(),
                defender.getMaxHealth(),
                calculateWinRate(defender)
        ));
    }

    private float calculateWinRate(Lutemon lutemon) {
        int total = lutemon.getWins() + lutemon.getLosses();
        return total > 0 ? (lutemon.getWins() * 100f / total) : 0;
    }

    private void validateAndStartBattle() {

        if (attacker == null || defender == null) {
            Toast.makeText(getContext(), "Please select the opponent first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (attacker.getId() == defender.getId()) {
            Toast.makeText(getContext(), "Cannot select the same Lutemon", Toast.LENGTH_SHORT).show();
            return;
        }
        attacker.restoreHealth();
        defender.restoreHealth();
        // INITBATTLE
        btnStart.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
        tvBattleLog.setText("⚔️ Battle Start！\n");
    }

    private void executeBattleTurn() {
        int randomBonus = (int)(Math.random() * 7) - 3;
        int attackPower = attacker.getCurrentAttack() + randomBonus;
        int defensePower = defender.getDefense();
        int damage = Math.max(attackPower - defensePower, 0);

        defender.setHealth(defender.getHealth() - damage);

        // LOG
        String logEntry = String.format("▶ %s deals %s %d damages！%s\n",
                attacker.getName(),
                defender.getName(),
                damage,
                getEffectivenessText(damage));

        tvBattleLog.append(logEntry);

        // UPDATE
        updateDefenderUI();

        // CHECK
        if (defender.getHealth() <= 0) {
            endBattle(true);
            return;
        }

        // SWAP
        Lutemon temp = attacker;
        attacker = defender;
        defender = temp;
        updateAttackerUI();
        updateDefenderUI();
    }

    private String getEffectivenessText(int randomBonus) {
        if (randomBonus > 0) {
            return "⚔️The effect is outstanding!⚔️";
        } else if (randomBonus == 0) {
            return "⚔️Average effect!⚔️";
        } else {
            return "⚔️No effect!⚔️";
        }
    }

    private void endBattle(boolean swapPositions) {
        attacker.setWins(attacker.getWins() + 1);
        defender.setLosses(defender.getLosses() + 1);
        attacker.train();
        String resultText = String.format("\n🏆 Winner：%s! %s get 1 EXP!\n", attacker.getName(),attacker.getName());
        tvBattleLog.append(resultText);

        btnNext.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);

        // SAVE
        Storage.getInstance().updateLutemon(attacker);
        Storage.getInstance().updateLutemon(defender);
    }

    private int getAvatarResource(String color) {
        switch (color) {
            case "Red": return R.drawable.ic_lutemon_red;
            case "Blue": return R.drawable.ic_lutemon_blue;
            case "Green": return R.drawable.ic_lutemon_green;
            case "Black": return R.drawable.ic_lutemon_black;
            case "White": return R.drawable.ic_lutemon_white;
            case "Pink": return R.drawable.ic_lutemon_pink;
            case "Orange": return R.drawable.ic_lutemon_orange;
            default: return R.drawable.ic_lutemon_default;
        }
    }
}

