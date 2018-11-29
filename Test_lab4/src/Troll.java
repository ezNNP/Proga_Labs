import javafx.scene.canvas.GraphicsContext;

public class Troll extends Creature {

    private int charism;

    public Troll(String name, int x, int y) {
        super(name, x, y, 3, Fear.CALM);
        this.charism = 3;
    }

    public Troll(String name, int x, int y, int speed, Fear fear, int charism) {
        super(name, x, y, speed, fear);
        this.charism = charism;
    }

    public int getCharism() {
        return charism;
    }

    public void setCharism(int charism) {
        this.charism = charism;
    }
}