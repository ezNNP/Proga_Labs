import javafx.scene.canvas.GraphicsContext;

public abstract class Animal extends Creature {

    public Animal(String name, int x, int y, int speed, Fear fear) {
        super(name, x, y, speed, fear);
    }

    public abstract void move(World world, int x, int y);

    @Override
    public void update(String shortName, int cellSize) {
        super.update(shortName, cellSize);
    }

    public abstract void sniff();
}