package com.main.lutemon12;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static Storage instance;
    private HashMap<Integer, Lutemon> lutemonsMap = new HashMap<>();
    private ArrayList<Lutemon> lutemons = new ArrayList<>();
    private static int nextId = 1;


    private Storage() {}



    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public static int getNextId() {
        return nextId;
    }
    public void addLutemon(Lutemon lutemon) {
        lutemon.setId(nextId);
        lutemon.setWins(0);
        lutemon.setLosses(0);
        lutemonsMap.put(nextId, lutemon);
        lutemons.add(lutemon);
        nextId++;
    }


    public ArrayList<Lutemon> getAllLutemons() {
        return lutemons;
    }

    public void updateLutemon(Lutemon updatedLutemon) {
        lutemonsMap.put(updatedLutemon.getId(), updatedLutemon);
        Lutemon existing = lutemonsMap.get(updatedLutemon.getId());
        if (existing != null) {
            existing.setWins(updatedLutemon.getWins());
            existing.setLosses(updatedLutemon.getLosses());
        }

        for (int i = 0; i < lutemons.size(); i++) {
            if (lutemons.get(i).getId() == updatedLutemon.getId()) {
                lutemons.set(i, updatedLutemon);
                break;
            }
        }

    }
    public void removeLutemon(int id) {
        lutemonsMap.remove(id);
        for (int i = 0; i < lutemons.size(); i++) {
            if (lutemons.get(i).getId() == id) {
                lutemons.remove(i);
                break;
            }
        }
    }
    public void restoreAllHealth() {
        for (Lutemon lutemon : lutemons) {
            lutemon.restoreHealth();
            updateLutemon(lutemon);
        }
    }
}
