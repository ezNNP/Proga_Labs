import javafx.scene.control.Label;

public abstract class Animal extends Creature {

    public Animal(String name, int x, int y, int speed, Fear fear) {
        super(name, x, y, speed, fear);
    }

    public abstract void move(World world, int x, int y);

    @Override
    public Label update(Integer id, String shortName, int cellSize) {
        return super.update(id, shortName, cellSize);
    }

    public abstract void sniff();
}