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
    // TODO: 16.11.18 Переделать в id при наведении писать имя | @done 24.11.2018-14:00

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

        Hedgehog hedgehog = new Hedgehog("Yojik", 3, 15, 10, 4, Fear.SHOCK, 0, 2, 3); // транслитом потому-что с русскими буквами не работает roflan

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

        root = new Group();
        scene = new Scene(root, world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize, Paint.valueOf("#FFFFFF"));
        canvas = new Canvas(world.getCoordinates().length * cellSize, world.getCoordinates()[0].length * cellSize);

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

        primaryStage.setTitle("Doka 2 Trade");
        //primaryStage.setResizable(false);
        updateCreatures(world);
        primaryStage.show();
    }

    /**
     *  <p>Рандомно передвигает всех персонажей в данном мире</p>
     *
     * @param world мир в котором происходят события
     */
    private void randomMoveCreatures(World world) {
        int width = world.getWidth();
        int height = world.getHeight();
        for (Creature creature : world.getCreatures()) {
            creature.move(world, (int)Math.round(Math.random() * (width - 1)), (int)Math.round(Math.random() * (height-1)));
        }
    }


    /**
     * <p>Обновляет положение существ в окне</p>
     *
     * @param world
     */
    private void updateCreatures(World world) {
        int i = 0;
        for (Creature creature : world.getCreatures()) {
            mainGridPain.getChildren().remove(creature.getLabel());
            Label curLabel = creature.update(i, "[" + creature.getClass().toString().substring(6) + "] " + creature.getName(), cellSize);
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
        updateCreatures(world);
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

            Button sayButton = new Button("Сказать");
            TextField sayText = new TextField();
            sayButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    creature.sound(Sounds.NORMAL, sayText.getText());
                }
            });
            grid.add(new Label("Сказать:"), 0, 2, 1, 1);
            grid.add(sayText, 1, 2, 2, 1);
            grid.add(sayButton, 3, 2, 1, 1);

            grid.add(new Label("Поменять страх"), 0, 3, 1, 1);
            Button updateFearButton = new Button("Обновить");
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
            grid.add(cb, 1, 3, 2, 1);
            updateFearButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (cb.getValue() != null) {
                        creature.setFear(Fear.valueOf(cb.getValue()));
                        System.out.println(creature.getFear());
                    } else {
                        // Говорить что тут все плохо???
                    }
                }
            });
            grid.add(updateFearButton, 3, 3, 1, 1);

            grid.add(new Separator(), 0, 4, 5, 1);
            Text txt1 = new Text("Дополнительные действия");
            txt1.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
            grid.add(txt1, 0, 5, 1, 1);
            grid.add(new Separator(), 0, 6, 5, 1);

            if (creature instanceof  Human) {
                Label humanLabel1 = new Label("Быть человеком к ежику по имени:");
                TextField humanText1 = new TextField();
                Button humanButton1 = new Button("Надеть одежду");
                Button humanButton2 = new Button("Снять одежду");
                Button humanButton3 = new Button("Быть человеком");

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
                humanButton3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (Creature current : world.getCreatures()) {
                            if ((current.getName().toUpperCase().trim().equals(humanText1.getText().toUpperCase().trim())) && (current instanceof Hedgehog)) {
                                System.out.println("До " + current.getFear());
                                ((Human) creature).beHuman(world, (Hedgehog) current);
                                System.out.println("После " + current.getFear());
                            } else {
                                System.out.println(current.getName());
                            }
                        }
                    }
                }); // На нажатие вызывает метод beHuman()

                grid.add(humanLabel1, 0, 7, 2, 1);
                grid.add(humanText1, 2, 7, 3, 1);
                grid.add(humanButton3, 3, 8, 1, 1);

                grid.add(humanButton1, 0, 9, 2, 1);
                grid.add(humanButton2, 2, 9, 2, 1);
            } else if (creature instanceof Troll) {
                grid.add(new Label("Затроллить человека по имени:"), 0, 7, 2, 1);
                grid.add(new TextField(), 2, 7, 3, 1);
            } else if (creature instanceof Hedgehog) {
                Button hedgehogButton1 = new Button("Рассказать шутку из БД");
                Button hedgehogButton2 = new Button("Поводить носом");

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

                grid.add(hedgehogButton1, 0, 7, 2, 1);
                grid.add(hedgehogButton2, 3, 7, 2, 1);
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
        updateCreatures(world);
    }
}