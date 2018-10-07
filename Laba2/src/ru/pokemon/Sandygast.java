package ru.pokemon;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import ru.pokemon.attacks.*;

/**
 *
 * @author s265077
 *
 * Класс покемона Sandygast
 *
 */

public class Sandygast extends Pokemon {
    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */
    public Sandygast(String name, int level) {
        super(name, level);
        this.setStats(55D, 55D, 80D, 70D, 45D, 15D);
        this.setType(Type.GHOST, Type.GROUND);
        this.setMove(new IronDefense(), new Facade(), new RockSlide());
    }
}
