package com.main.lutemon12;

public class PinkLutemon extends Lutemon {
    public PinkLutemon(int id, String name) {
        super(id, name, "Pink", 7, 2, 18, 7);
    }

    @Override
    public void specialAbility() {
        this.attack += 1;
    }
}