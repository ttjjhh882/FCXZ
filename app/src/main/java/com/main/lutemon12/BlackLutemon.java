package com.main.lutemon12;

public class BlackLutemon extends Lutemon {
    public BlackLutemon(int id, String name) {
        super(id, name, "Black", 9, 0, 16, 9);
    }


    @Override
    public void specialAbility() {
        this.defense = 0;
        this.attack += 3;
    }
}