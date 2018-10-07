package ru.pokemon.attacks;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;
/**
 *
 * @author s265077
 *
 * Тип атаки IceBeam
 */


public class IceBeam extends SpecialMove {
    public IceBeam() {
        super(Type.ICE, 90D, 1.0D);
    }
}
