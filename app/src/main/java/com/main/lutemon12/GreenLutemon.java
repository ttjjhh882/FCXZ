package com.main.lutemon12;

public class GreenLutemon extends Lutemon {
    public GreenLutemon(int id, String name) {
        super(id, name, "Green", 6, 3, 19,6);
    }
    @Override
    public void specialAbility() {
        this.health += 2;
        if (this.health > this.maxHealth) this.health = this.maxHealth;
    }
}