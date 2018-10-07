package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Blizzard
 */

public class Blizzard extends SpecialMove {
    public Blizzard() {
        super(Type.ICE, 110D, 0.7D);
    }
}
