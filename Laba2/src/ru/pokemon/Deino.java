package ru.pokemon;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import ru.pokemon.attacks.DoubleTeam;
import ru.pokemon.attacks.DragonPulse;
/**
 *
 * @author s265077
 *
 * Класс покемона Deino
 *
 */

public class Deino extends Pokemon {
    /**
     *
     * @param name имя покемона
     * @param level уровень покемона
     */
    public Deino(String name, int level) {
        super(name, level);
        this.setType(Type.DARK, Type.DRAGON);
        this.setStats(52D, 65D, 50D, 45D, 50D, 38D);
        this.setMove(new DoubleTeam(), new DragonPulse());
    }
}
