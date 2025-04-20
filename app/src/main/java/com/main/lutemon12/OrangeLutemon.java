package com.main.lutemon12;

public class OrangeLutemon extends Lutemon {
    public OrangeLutemon(int id, String name) {
        super(id, name, "Orange", 8, 1, 17, 8);
    }

    @Override
    public void specialAbility() {
        this.attack *= 1.5;
    }
}