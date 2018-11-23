import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    // TODO: 16.11.18 Cell size and width and height of world with variables
    // TODO: 16.11.18 Переделать в id при наведении писать имя

    private int windowWidth;
    private int windowHeight;
    private final int cellSize = 40;
    private boolean isChosen = false;
    private World world;
    private Group root;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc;
    private Thread thread;
    private Creature choosed;
    private Stage primaryStage;
    private GridPane mainGridPain;

    private static Integer parse(String x) {
        return Integer.parseInt(x);
    }

    private static int p() {
        return p();
    }

    private static RuntimeException ex() {
        return new RuntimeException();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.mainGridPain = new GridPane();


        ArrayList<Cloth> hats = new ArrayList<>();
        hats.add(new Hat(false, 32F, 10, HatType.ZYLINDER));

        Human human = new Human("Френкен Cнорк", 60, 180, hats, 3, Fear.CALM, 31F, 0, 0, 2);

        Troll troll = new Troll("Мумми Тролль", 0, 1, 3, Fear.USUAL, 2);

        Hedgehog hedgehog = new Hedgehog("Eжик", 3, 15, 10, 4, Fear.SHOCK, 0, 2, 3);

        Human human1 = new Human("Ж", 1, 1, 5, Fear.CALM, 50F, 6, 6, 10);
        Human human2 = new Human("О", 1, 1, 1, Fear.CALM, 30F, 7, 7, 8);
        Human human3 = new Human("П", 1, 1, 1, Fear.CALM, 30F, 10, 10, 8);
        Human human4 = new Human("А", 1, 1, 1, Fear.CALM, 30F, 1, 9, 8);

        world = new World(Weather.RAIN, 20, 15, human, troll, hedgehog, human1, human2, human3, human4);
        for (int i = 0; i < world.getCoordinates()[0].length; i++) {
            RowConstraints row = new RowConstraints(cellSize);
            mainGridPain.getRowConstraints().add(row);
        }


        for (int i = 0; i < world.getCoordinates().length; i++) {
            ColumnConstraints column = new ColumnConstraints(cellSize);
            mainGridPain.getColumnConstraints().add(column);
        }

        // Создаем JavaFX окно, добавляем сцену и канвас, на котором все будем рисовать
        root = new Group();
        scene = new Scene(root, world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize, Paint.valueOf("#FFFFFF"));
        canvas = new Canvas(world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize);

        // Добавляем Graphic Context, рисуем координаты, добавляем сцену, добалвяем канвас, добавляем обработчик нажатия мыши
        gc = canvas.getGraphicsContext2D();
        drawCoords(gc, world.getCoordinates().length, world.getCoordinates()[0].length);
        primaryStage.setScene(scene);
        mainGridPain.setVisible(true);
        mainGridPain.setGridLinesVisible(true);
        root.getChildren().addAll(mainGridPain);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // коориданты нажатия мыши
                int x = (int) (event.getSceneX() / cellSize);
                int y = (int) (event.getSceneY() / cellSize);
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    mouseLeftClickEventHandler(x, y);
                    System.out.println("Left mouse button clicked on " + (int) (event.getSceneX() / cellSize) + ":" + (int) (event.getSceneY() / cellSize));
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    mouseRightClickEventHandler(x, y);
                    System.out.println("Right mouse button clicked on " + (int) (event.getSceneX() / cellSize) + ":" + (int) (event.getSceneY() / cellSize));
                }
            }
        });

        // Настройки окна JavaFX
        primaryStage.setTitle("Лабка");
        //primaryStage.setResizable(false);


        // Добавляем Graphic Context для Creature, чтобы возможно было рисовать без n-ой вложенности и отрисовываем их
        for (Creature current : world.getCreatures()) {
            current.setGc(gc);
        }

        updateCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        //moveCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);
                        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                        //drawCoords(gc, world.getCoordinates().length, world.getCoordinates()[0].length);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(500);
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
        int i = 0;
        for (Creature creature : world.getCreatures()) {
            mainGridPain.getChildren().remove(creature.getLabel());
            Label curLabel = creature.update(i, creature.getName(), cellSize);
            curLabel.setVisible(true);
            mainGridPain.add(curLabel, creature.getX(), creature.getY());
            i++;
        }
    }


    /**
     *  <p>Обрабатывает нажатие мыши левой клавишей</p>
     *
     * @param x координата x мыши
     * @param y координата y мыши
     */
    private void mouseLeftClickEventHandler(int x, int y) {
        if (!isChosen) {
            if (world.getCoordinates()[x][y] != null) {
                isChosen = true;
                choosed = world.getCoordinates()[x][y];
            }
        } else {
            choosed.move(world, x, y);
            isChosen = false;

        }
        updateCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);
    }

    /**
     *  <p>Обрабатывает нажатие мыши правой клавишей</p>
     *
     * @param x координата x мыши
     * @param y координата y мыши
     */
    private void mouseRightClickEventHandler(int x, int y) {
        Creature creature = world.getCoordinates()[x][y];

        if (creature != null) {

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(5));
            grid.setVgap(10);
            grid.setHgap(10);

            Text txt = new Text("Основные действия");
            txt.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
            grid.add(txt, 0, 0, 1, 1);
            grid.add(new Separator(), 0, 1, 5, 1);

            grid.add(new Label("Вывести:"), 0, 2, 1, 1);
            grid.add(new TextField(), 1, 2, 3, 1);

            grid.add(new Label("Поменять страх"), 0, 3, 1, 1);
            ComboBox<String> cb = new ComboBox<>();
            cb.getItems().addAll(
                    "CALM",
                    "USUAL",
                    "ANXIETY",
                    "FOBIA",
                    "HORROR",
                    "SHOCK"
            );

            cb.setPrefWidth(500);
            grid.add(cb, 1, 3, 3, 1);

            grid.add(new Separator(), 0, 4, 5, 1);
            Text txt1 = new Text("Дополнительные действия");
            txt1.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
            grid.add(txt1, 0, 5, 1, 1);
            grid.add(new Separator(), 0, 6, 5, 1);
            Label humanLabel1 = new Label("Быть человеком к ежику по имени:");
            TextField humanText1 = new TextField();
            Button humanButton1 = new Button("Надеть одежду");
            Button humanButton2 = new Button("Снять одежду");

            Button hedgehogButton1 = new Button("Рассказать шутку из БД");
            Button hedgehogButton2 = new Button("Поводить носом");

            humanButton1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((Human) creature).putOnCloth();
                }
            }); // На нажатие надевает одежду
            humanButton2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((Human) creature).takeOffCloth();
                }
            }); // На нажатие снимает одежду

            hedgehogButton1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((Hedgehog) creature).tellJoke();
                }
            }); // на нажатие рассказывает шутку из БД
            hedgehogButton2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((Hedgehog) creature).sniff();
                }
            }); // на нажатие водит носом

            if (creature instanceof  Human) {
                grid.add(humanLabel1, 0, 7, 2, 1);
                grid.add(humanText1, 2, 7, 3, 1);

                grid.add(humanButton1, 0, 8, 2, 1);
                grid.add(humanButton2, 3, 8, 2, 1);
            } else if (creature instanceof Troll) {
                grid.add(new Label("Затроллить человека по имени:"), 0, 7, 2, 1);
                grid.add(new TextField(), 2, 7, 3, 1);
            } else if (creature instanceof Hedgehog) {
                grid.add(new Button("Рассказать шутку из БД"), 0, 7, 2, 1);
                grid.add(new Button("Поводить носом"), 3, 7, 2, 1);
            }

            Scene gridScene = new Scene(grid, 500, 300);
            Stage gridStage = new Stage();

            // New window (Stage)

            gridStage.setTitle("Выберите опцию из списка доступных");
            gridStage.setScene(gridScene);

            gridStage.setX(primaryStage.getX() + 200);
            gridStage.setY(primaryStage.getY() + 100);

            gridStage.initModality(Modality.WINDOW_MODAL);

            gridStage.initOwner(primaryStage);

            gridStage.show();
        } else {
            isChosen = false;
        }
        updateCreatures(world, world.getCoordinates().length, world.getCoordinates()[0].length);
    }
}