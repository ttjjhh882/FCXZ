package com.main.lutemon12;

public class WaterLutemon extends Lutemon {
    public WaterLutemon(int id, String name) {
        super(id, name, "Blue", 5, 5, 20, 5); // 参数：名称、颜色、攻击、防御、最大生命值
    }


    //abolish
    @Override
    public void specialAbility() {
        this.health += 3;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }
}
