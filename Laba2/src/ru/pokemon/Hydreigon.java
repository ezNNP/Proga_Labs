package ru.pokemon;

import ru.pokemon.attacks.DoubleHit;
import ru.pokemon.attacks.DoubleTeam;
import ru.pokemon.attacks.DragonPulse;
import ru.pokemon.attacks.RockTomb;
/**
 *
 * @author s265077
 *
 * Класс покемона Hydreigon
 *
 */

public class Hydreigon extends Zweilous {
    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */
    public Hydreigon(String name, int level) {
        super(name, level);
        this.setStats(92D, 105D, 90D, 125D, 90D, 98D);
        this.setMove(new DoubleTeam(), new DragonPulse(), new DoubleHit(), new RockTomb());
    }
}
