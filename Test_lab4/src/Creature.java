import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public abstract class Creature implements Creatureable {

    private int x, y;
    private int speed;
    private String name;
    private GraphicsContext gc;
    private Fear fear;
    private Label label;

    public Creature(String name, int x, int y, int speed, GraphicsContext gc) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gc = gc;
    }

    public Creature(String name, int x, int y, int speed, Fear fear) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.fear = fear;
    }


    public void move(World world, int x, int y) {
        int localSpeed = getSpeed() + this.fear.ordinal();
        double time = 0D;

        if ((localSpeed != 0) && (world.getCoordinates().length >= x) && (world.getCoordinates()[0].length >= y)
            && (x >= 0) && (y >= 0)) {
            if (world.getCoordinates()[x][y] == null) {
                world.getCoordinates()[getX()][getY()] = null;
                time = Math.hypot(Math.abs(getX() - x), Math.abs(getY() - y)) / localSpeed;
                System.out.println("Подождите " + Math.round(time) + " секунд");
                try {
                    Thread.sleep(Math.round(time) * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                world.getCoordinates()[x][y] = this;
                setX(x);
                setY(y);
                System.out.println(this.name + " передвинулся на координаты " + x + ":" + y + " за " + Math.round(time) + " секунд.");
            } else {
                System.out.println("Невозможно передвинуться на " + x + ":" + y + ", так как там кто-то стоит");
            }
        } else {
            System.out.println("Невозможно передвинуться, так как скорость равна 0 или координат не существует в сетке");
        }
    }


    @Override
    public Label update(Integer id, String shortName, int cellSize) {
        //gc.setFont(new Font(cellSize));
        label = new Label(id.toString());
        Tooltip tooltip = new Tooltip(shortName);
        tooltip.setFont(new Font((double) (cellSize / 2)));
        label.setTooltip(tooltip);
        //bindTooltip(label, tooltip);
        label.setTextFill(Color.web("#000000"));
        label.setFont(new Font(cellSize));

        return label;
        //gc.fillText(shortName, x * cellSize, (y + 1)*cellSize, cellSize);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public void sound(Sounds type, String message) {
        switch (type) {
            case NORMAL:
                System.out.println(message);
                break;
            case SHOUT:
                System.out.println(message.toUpperCase());
                break;
            case WHISPER:
                System.out.println("*Шепотом*" + message.toLowerCase() + "...");
                break;
            case LOUD:
                System.out.println(firstUpperCase(message) + "!");
        }
    }

    private static void bindTooltip(final Node node, final Tooltip tooltip){


        node.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                tooltip.show(node, event.getScreenX(), event.getScreenY() + 15);
            }
        });

        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tooltip.show(node, event.getScreenX(), event.getScreenY() + 15);
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                tooltip.hide();
            }
        });
    }

    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public Fear getFear() {
        return fear;
    }

    public void setFear(Fear fear) {
        this.fear = fear;
    }
}