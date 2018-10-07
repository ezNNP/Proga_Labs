package ru.pokemon;

import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();
        battle.addAlly(new Corsola("Kiki", 39));
        battle.addAlly(new Sandygast("KB", 80));
        battle.addFoe(new Palossand("Resha", 80));
        battle.addFoe(new Deino("JT", 32));
        battle.addAlly(new Zweilous("Lil Peep", 32));
        battle.addFoe(new Hydreigon("XTENTACION", 39));
        battle.go();
    }
}