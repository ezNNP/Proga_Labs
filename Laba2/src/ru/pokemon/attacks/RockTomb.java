package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки RockTomb
 */

public class RockTomb extends PhysicalMove {
    public RockTomb() {
        super(Type.ROCK, 60D, 0.95D);
    }
}
