package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Rock Slide
 */

public class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75D, 0.9D);
    }
}
