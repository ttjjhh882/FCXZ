package com.main.lutemon12;

public class WhiteLutemon extends Lutemon {
    public WhiteLutemon(int id, String name) {
        super(id, name, "White", 5, 4, 20, 5);
    }

    @Override
    public void specialAbility() {
        this.defense += 1;
    }
}