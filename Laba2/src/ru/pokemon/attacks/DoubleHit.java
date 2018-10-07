package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;
/**
 *
 * @author s265077
 *
 * Тип атаки DoubleHit
 */

public class DoubleHit extends PhysicalMove {
    public DoubleHit() {
        super(Type.NORMAL, 35D, 0.9D);
    }
}
