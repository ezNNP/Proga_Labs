package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

/**
 *
 * @author s265077
 *
 * Тип атаки Iron Defence
 */

public class IronDefense extends StatusMove {
    public IronDefense() {
        super();
        this.type = Type.STEEL;
    }
}
