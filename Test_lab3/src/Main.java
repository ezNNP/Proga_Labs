import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private int windowWidth;
    private int windowHeight;
    private final int cellSize = 30;

    public static void main(String[] args) {

        launch(args);

		//System.out.println(hedgehog.toString());
		//System.out.println(world.toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<Cloth> hats = new ArrayList<>();
        hats.add(new Hat(false, 30F, 10, HatType.ZYLINDER));

        Human human = new Human("ФC", 60, 180, hats, 3, Fear.CALM, 31F, 0, 0, 2);

        Troll troll = new Troll("М", 0, 1, 3, Fear.USUAL);

        Hedgehog hedgehog = new Hedgehog("E", 3, 15, 10, 4, Fear.SHOCK, 0, 2, 3);

        World world = new World(Weather.RAIN, 20, 15, human, troll, hedgehog);

        int result = (int) (3 * Math.PI/2);
/*
        System.out.println("Ежик мокрый: " + hedgehog.isDumped());
        System.out.println("Смена погоды на солнце");
        world.setWeather(Weather.SUN);
        System.out.println("Ежик мокрый: " + hedgehog.isDumped());
        hedgehog.move(world, 4, 6);
        System.out.println("Состояние ежика: " + hedgehog.getFear().name());
        human.beHuman(world, hedgehog);
        human.move(world, 4, 5);
        human.beHuman(world, hedgehog);
        System.out.println("Состояние ежика: " + hedgehog.getFear().name());
        human.sound(Sounds.NORMAL, "Всем привет, здарова, здарова, привет");
        hedgehog.tellJoke();
        hedgehog.tellJoke();
        human.sound(Sounds.SHOUT, "Хорошие шутки про людей, где метод kill(), о боже, какой-же туопй разраб.");*/

        Group root = new Group();
        Scene scene = new Scene(root, world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize, Paint.valueOf("#2F2F2F"));
        Canvas canvas = new Canvas(world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawCoords(gc, world.getCoordinates().length, world.getCoordinates()[0].length);
        primaryStage.setScene(scene);
        root.getChildren().add(canvas);

        primaryStage.setTitle("Лабка");
        //primaryStage.setResizable(false);

        for (Creature current : world.getCreatures()) {
            current.setGc(gc);
            current.update(current.getName(), cellSize);
        }

        //Platform.runLater(() -> moveCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length));

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        moveCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        updateCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);
                        drawCoords(gc, world.getCoordinates().length, world.getCoordinates()[0].length);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    Platform.runLater(updater);
                }
            }

        });

        primaryStage.show();

        thread.setDaemon(true);
        thread.start();

    }

    private void drawCoords(GraphicsContext gc, int width, int height) {
        gc.setFill(Color.WHITESMOKE);
        gc.setStroke(Color.WHITE);
        for (int i = 0; i < width+1; i++) {
            gc.strokeLine(i*cellSize, 0, i*cellSize, width*cellSize);
            gc.strokeLine(0, i*cellSize, width * cellSize, i*cellSize);
        }

    }

    private void moveCreatures(World world, int width, int height) {
        for (Creature creature : world.getCreatures()) {
            creature.move(world, (int)Math.round(Math.random() * (width - 1)), (int)Math.round(Math.random() * (height-1)));
        }
    }

    private void updateCreatures(World world, int width, int height) {
        for (Creature creature : world.getCreatures()) {
            creature.update(creature.getName(), cellSize);
        }
    }
}