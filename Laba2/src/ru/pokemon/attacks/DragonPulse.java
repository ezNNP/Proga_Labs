package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки DragonPulse
 */

public class DragonPulse extends SpecialMove {
    public DragonPulse() {
        super(Type.DRAGON, 85D,1.0D);
    }
}
