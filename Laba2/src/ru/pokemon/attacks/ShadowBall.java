package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Shadow Ball
 */


public class ShadowBall extends SpecialMove {
    public ShadowBall() {
        super(Type.GHOST, 80D, 1.0D);
    }
}