import javafx.scene.canvas.GraphicsContext;

public class Troll extends Creature {

    private String name;
    private int charism;

    public void beTroll(Human human) {
        if ((Math.abs(super.getX() - human.getX()) <= 1) && (Math.abs(super.getY() - human.getY()) <= 1)) {
            int ordinal = human.getFear().ordinal();
            long method = Math.round(Math.random() * 6 - 3);
            System.out.println("Из-за метода троллинга страх изменился на " + method);
            if (ordinal > method) {
                ordinal -= method;
            } else {
                ordinal = 0;
            }

            System.out.println("Из-за харизмы страх изменился на " + charism);
            if (ordinal > charism) {
                ordinal -= charism;
            } else {
                ordinal = 0;
            }

            if (ordinal < 0) {
                ordinal = 0;
            } else if (ordinal > 5) {
                ordinal = 5;
            }

            human.setFear(Fear.values()[ordinal]);
        } else {
            System.out.println("Этот человек слишком далеко, чтобы его застроллить слишком далеко...");
        }
    }

    public Troll(String name, int x, int y) {
        super(name, x, y, 3, Fear.CALM);
        this.name = name;
        this.charism = 3;
    }

    public Troll(String name, int x, int y, int speed, Fear fear, int charism) {
        super(name, x, y, speed, fear);
        this.name = name;
        this.charism = charism;
    }

    public int getCharism() {
        return charism;
    }

    public void setCharism(int charism) {
        this.charism = charism;
    }
}