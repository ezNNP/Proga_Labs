package ru.pokemon;

import ru.pokemon.attacks.DoubleHit;
import ru.pokemon.attacks.DoubleTeam;
import ru.pokemon.attacks.DragonPulse;

/**
 *
 * @author s265077
 *
 * Класс покемона Zweilous
 *
 */

public class Zweilous extends Deino {
    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */
    public Zweilous(String name, int level) {
        super(name, level);
        this.setStats(72D, 85D, 70D, 65D, 70D, 58D);
        this.setMove(new DoubleTeam(), new DragonPulse(), new DoubleHit());
    }
}
