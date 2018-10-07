package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Facade
 */

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70D, 1.0D);
    }
}
