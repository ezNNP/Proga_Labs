package ru.pokemon;

import ru.pokemon.attacks.Facade;
import ru.pokemon.attacks.IronDefense;
import ru.pokemon.attacks.RockSlide;
import ru.pokemon.attacks.SludgeBomb;

/**
 *
 * @author s265077
 *
 * Класс покемона Palossand
 *
 */

public class Palossand extends Sandygast {
    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */
    public Palossand(String name, int level) {
        super(name, level);
        this.setStats(85D, 75D, 110D, 100D, 75D, 35D);
        this.setMove(new IronDefense(), new Facade(), new RockSlide(), new SludgeBomb());
    }
}
