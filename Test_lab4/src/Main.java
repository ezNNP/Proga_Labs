import exceptions.BusyCellException;
import exceptions.NullClassException;
import exceptions.TooLargeMapException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private int cellSize;
    private boolean isChosen = false;
    private World world;
    private Creature choosed;
    private Stage primaryStage;
    private GridPane mainGridPain;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        class MyButton extends Button {
            MyButton(String text) {
                super(text);
            }
        }
        GridPane startGridPane = new GridPane();
        startGridPane.setPadding(new Insets(5));
        startGridPane.setVgap(6);
        startGridPane.setHgap(2);

        Scene gridScene = new Scene(startGridPane, 500, 300);
        Stage gridStage = new Stage();

        // New window (Stage)

        gridStage.setTitle("Выберите опцию из списка доступных");
        gridStage.setScene(gridScene);

        gridStage.initOwner(primaryStage);

        startGridPane.add(new Text("Укажите размеры"), 0, 0, 1, 1);
        startGridPane.add(new Separator(), 0, 1, 2, 1);

        startGridPane.add(new Text("Длина: "), 0, 2, 1, 1);
        TextField widthText = new TextField();
        startGridPane.add(widthText, 1, 2, 1, 1);

        startGridPane.add(new Text("Высота: "), 0, 3, 1, 1);
        TextField heightText = new TextField();
        startGridPane.add(heightText, 1, 3, 1, 1);

        startGridPane.add(new Text("Размер клетки (px): "), 0, 4, 1, 1);
        TextField cellSizeText = new TextField();
        startGridPane.add(cellSizeText, 1, 4, 1, 1);

        Button button = new MyButton("Старт");
        button.setOnAction(event -> {
            try {
                int worldWidth = Integer.parseInt(widthText.getText());
                int worldHeight = Integer.parseInt(heightText.getText());
                cellSize = Integer.parseInt(cellSizeText.getText());
                if ((worldWidth * cellSize > 1920) || (worldHeight * cellSize > 1080)) {
                    throw new TooLargeMapException();
                }
                mainScreen(primaryStage, worldWidth, worldHeight);
                gridStage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Вы ввели некорректные данные,\nпопробуйте еще раз", ButtonType.OK);
                alert.setResizable(false);
                alert.showAndWait();
            } catch (TooLargeMapException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Слишком большой размер карты\nПопробуйте уменьшить размер клетки,\nвысоту или длину мира", ButtonType.OK);
                alert.setResizable(false);
                alert.showAndWait();
            }
        });
        startGridPane.add(button, 1, 5, 1, 1);

        gridStage.show();
    }

    private void mainScreen(Stage primaryStage, int worldWidth, int worldHeight) {
        this.primaryStage = primaryStage;
        this.mainGridPain = new GridPane();


        /*ArrayList<Cloth> hats = new ArrayList<>();
        hats.add(new Hat(false, 32F, 10, HatType.ZYLINDER));

        Human human = new Human("Френкен Cнорк", 60, 180, hats, 3, Fear.CALM, 31F, 0, 0, 2);

        Troll troll = new Troll("Мумми Тролль", 0, 1, 3, Fear.USUAL, 2);

        Hedgehog hedgehog = new Hedgehog("Yojik", 3, 15, 10, 4, Fear.SHOCK, 0, 2, 3); // транслитом потому-что с русскими буквами не работает roflan
*/
        //world = new World(Weather.RAIN, worldWidth, worldHeight, human, troll, hedgehog);
        world = new World(Weather.RAIN, worldWidth, worldHeight, new Human("EasyAnonymousClass", 0, 0));
        for (int i = 0; i < world.getCoordinates()[0].length; i++) {
            RowConstraints row = new RowConstraints(cellSize);
            mainGridPain.getRowConstraints().add(row);
        }

        for (int i = 0; i < world.getCoordinates().length; i++) {
            ColumnConstraints column = new ColumnConstraints(cellSize);
            mainGridPain.getColumnConstraints().add(column);
        }

        Group root = new Group();
        Scene scene = new Scene(root, world.getWidth() * cellSize, world.getHeight() * cellSize, Paint.valueOf("#FFFFFF"));

        primaryStage.setScene(scene);
        mainGridPain.setVisible(true);
        mainGridPain.setGridLinesVisible(true);
        root.getChildren().addAll(mainGridPain);

        scene.setOnMousePressed(event -> {
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
        });

        primaryStage.setTitle("Doka 2 Trade");
        primaryStage.setResizable(false);
        if (world.getCreatures() != null) {
            updateCreatures(world);
        }
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
     * @param world мир в котором происходят события
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
            if (world.getCell(x, y).getCreature() != null) {
                isChosen = true;
                choosed = world.getCoordinates()[x][y].getCreature();
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
        World.Cell cell = world.getCell(x, y);
        Creature creature = cell.getCreature();

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
            sayButton.setOnAction(event -> creature.sound(Sounds.NORMAL, sayText.getText()));
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
            updateFearButton.setOnAction(event -> {
                if (cb.getValue() != null) {
                    creature.setFear(Fear.valueOf(cb.getValue()));
                    System.out.println(creature.getName() + ": " + creature.getFear());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали новый тип страха\nПопробуйте еще раз", ButtonType.OK);
                    alert.setResizable(false);
                    alert.showAndWait();
                }
            });
            grid.add(updateFearButton, 3, 3, 1, 1);

            if (!(creature instanceof Troll)) {
                grid.add(new Separator(), 0, 4, 5, 1);
                Text txt1 = new Text("Дополнительные действия");
                txt1.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
                grid.add(txt1, 0, 5, 1, 1);
                grid.add(new Separator(), 0, 6, 5, 1);
            }

            if (creature instanceof  Human) {
                Label humanLabel1 = new Label("Быть человеком к ежику по имени:");
                TextField humanText1 = new TextField();
                Button humanButton1 = new Button("Надеть одежду");
                Button humanButton2 = new Button("Снять одежду");
                Button humanButton3 = new Button("Быть человеком");

                humanButton1.setOnAction(event -> ((Human) creature).putOnCloth()); // На нажатие надевает одежду
                humanButton2.setOnAction(event -> ((Human) creature).takeOffCloth()); // На нажатие снимает одежду
                humanButton3.setOnAction(event -> {
                    for (Creature current : world.getCreatures()) {
                        if ((current.getName().toUpperCase().trim().equals(humanText1.getText().toUpperCase().trim())) && (current instanceof Hedgehog)) {
                            System.out.println("До " + current.getFear());
                            ((Human) creature).beHuman(world, (Hedgehog) current);
                            System.out.println("После " + current.getFear());
                        } else {
                            System.out.println(current.getName());
                        }
                    }
                }); // На нажатие вызывает метод beHuman()

                grid.add(humanLabel1, 0, 7, 2, 1);
                grid.add(humanText1, 2, 7, 3, 1);
                grid.add(humanButton3, 3, 8, 1, 1);

                grid.add(humanButton1, 0, 9, 2, 1);
                grid.add(humanButton2, 2, 9, 2, 1);
            } else if (creature instanceof Hedgehog) {
                Button hedgehogButton1 = new Button("Рассказать шутку из БД");
                Button hedgehogButton2 = new Button("Поводить носом");

                hedgehogButton1.setOnAction(event -> ((Hedgehog) creature).tellJoke()); // на нажатие рассказывает шутку из БД
                hedgehogButton2.setOnAction(event -> ((Hedgehog) creature).sniff()); // на нажатие водит носом

                grid.add(hedgehogButton1, 0, 7, 2, 1);
                grid.add(hedgehogButton2, 2, 7, 2, 1);
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
            Stage createStage = new Stage();
            GridPane createGrid = new GridPane();
            Scene createScene = new Scene(createGrid, 300, 500);
            createGrid.setPadding(new Insets(5));
            createGrid.setVgap(7);
            createGrid.setHgap(2);
            createStage.setScene(createScene);
            createStage.initModality(Modality.WINDOW_MODAL);
            createStage.initOwner(primaryStage);

            createGrid.add(new Text("Создание персонажа"), 0, 0, 2, 1);
            createGrid.add(new Separator(), 0, 1, 2, 1);

            createGrid.add(new Text("Выберите класс"), 0, 2, 1, 1);
            ComboBox<String> classes = new ComboBox<>();
            classes.getItems().addAll(
                    "Human",
                    "Troll",
                    "Hedgehog"
            );
            createGrid.add(classes, 1, 2, 1, 1);

            createGrid.add(new Text("Введите имя:"), 0, 3, 1, 1);
            TextField nameField = new TextField();
            createGrid.add(nameField, 1, 3, 1, 1);

            createGrid.add(new Text("Введите X:"), 0, 4, 1, 1);
            TextField xField = new TextField(String.valueOf(x));
            createGrid.add(xField, 1, 4, 1, 1);

            createGrid.add(new Text("Введите Y:"), 0, 5, 1, 1);
            TextField yField = new TextField(String.valueOf(y));
            createGrid.add(yField, 1, 5, 1, 1);

            Button createButton = new Button("Создать персонажа");
            createButton.setOnAction(event -> {
                Creature createdCreature;
                String name = nameField.getText();
                try {
                    int x1 = Integer.parseInt(xField.getText());
                    int y1 = Integer.parseInt(yField.getText());

                    if ((x1 >= world.getWidth()) || (y1 >= world.getHeight())) {
                        throw new NumberFormatException();
                    }

                    switch (classes.getValue()) {
                        case "Human":
                            createdCreature = new Human(name, x1, y1);
                            break;
                        case "Troll":
                            createdCreature = new Troll(name, x1, y1, new Troll.Dybina(10));
                            break;
                        case "Hedgehog":
                            createdCreature = new Hedgehog(name, x1, y1);
                            break;
                        default:
                            throw new NullClassException();
                    }

                    world.addCreature(createdCreature); // может выбросить BusyCellException
                    updateCreatures(world);
                    createStage.close();
                } catch (NullClassException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не ввели данные при выборе класса\nПопробуйте еще раз", ButtonType.OK);
                    alert.setResizable(false);
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Вы ввели некорректные данные для полей\nx и/или y, попробуйте еще раз", ButtonType.OK);
                    alert.setResizable(false);
                    alert.showAndWait();
                } catch (BusyCellException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Выбранная вами клетка занята\nПопробуйте еще раз", ButtonType.OK);
                    alert.setResizable(false);
                    alert.showAndWait();
                }
            });
            createGrid.add(createButton, 1, 6, 1, 1);

            createStage.show();
        }
        updateCreatures(world);
    }
}