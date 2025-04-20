package com.main.lutemon12;

import java.util.Random;

public abstract class Lutemon {
    public int id;
    public String name;
    public String color;
    public int attack;
    public int defense;
    public int maxHealth;
    public int health;
    public int experience;
    private int baseAttack;
    private int wins;
    private int losses;


    public Lutemon(int id, String name, String color, int attack, int defense, int maxHealth, int baseAttack) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = 0;
        this.baseAttack = baseAttack;
        this.wins = 0;
        this.losses = 0;

    }
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public abstract void specialAbility();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void restoreHealth() {
        health = maxHealth;
    }

    public int getCurrentAttack() {
        return baseAttack + experience;
    }

    public int getBaseAttack() { return baseAttack; }
    public int getDefense() { return defense; }
    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }
    public int getLosses() { return losses; }
    public void setLosses(int losses) { this.losses = losses; }



    public String train() {
        Random random = new Random();
        int attributeIndex = random.nextInt(3);
        String attributeName = "";

        switch (attributeIndex) {
            case 0:
                baseAttack += 1;
                experience += 1;
                attributeName = "ATK";
                break;
            case 1:
                defense += 1;
                experience += 1;
                attributeName = "DEF";
                break;
            case 2:
                maxHealth += 1;
                health = Math.min(health + 1, maxHealth);
                experience += 1;
                attributeName = "MAXHP";
                break;
        }

        return attributeName;
    }

    public void setAttack(int originalAttack) {

    }

    public void setDefense(int originalDefense) {
    }
}


