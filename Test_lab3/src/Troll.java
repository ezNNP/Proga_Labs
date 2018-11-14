import javafx.scene.canvas.GraphicsContext;

public class Troll extends Creature {

    private String name;

    public Troll(String name, int x, int y, int speed, Fear fear) {
        super(name, x, y, speed, fear);
        this.name = name;
    }
}