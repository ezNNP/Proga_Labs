package ru.pokemon;

import ru.ifmo.se.pokemon.*;
import ru.pokemon.attacks.Blizzard;
import ru.pokemon.attacks.IceBeam;
import ru.pokemon.attacks.RockTomb;
import ru.pokemon.attacks.ShadowBall;

/**
 *
 * @author s265077
 *
 * Класс покемона Corsola
 *
 */


public class Corsola extends Pokemon {

    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */

    public Corsola(String name, int level) {
        super(name, level);
        this.setStats(65D, 55D, 95D, 65D, 95D, 35D);
        this.setType(Type.WATER, Type.ROCK);
        this.setMove(new ShadowBall(), new IceBeam(), new Blizzard(), new RockTomb());
    }

}
