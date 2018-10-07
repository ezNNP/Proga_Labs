package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Sludge Bomb
 */


public class SludgeBomb extends SpecialMove {
    public SludgeBomb() {
        super(Type.POISON, 90D, 1.0D);
    }
}
