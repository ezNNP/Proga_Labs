import javafx.scene.canvas.GraphicsContext;

import java.sql.*;

public class Hedgehog extends Animal {

    private String name;
    private int age;
    private long tall;
    private long height;
    private long weight;

    private Connection connection;
    private boolean ruffled;
    private boolean dumped;

    public Hedgehog(String name, int age, long tall, long height, long weight, Fear fear, int x, int y, int speed) {
        super(name, x, y, speed, fear);
        this.name = name;
        this.age = age;
        this.tall = tall;
        this.height = height;
        this.weight = weight;
        this.ruffled = false;
        this.dumped = false;
        connection = new Database_Connector("jdbc:postgresql://localhost/lab3", "grigoriy", "123qweasd").getConnection();
    }

    @Override
    public void move(World world, int x, int y) {
        int localSpeed = super.getSpeed() + super.getFear().ordinal();
        double time = 0D;
        if (localSpeed != 0) {
            if (world.getCoordinates()[x][y] == null) {
                world.getCoordinates()[super.getX()][super.getY()] = null;
                time = Math.hypot(Math.abs(super.getX() - x), Math.abs(super.getY() - y)) / localSpeed;
                System.out.println("Подождите " + Math.round(time) + " секунд");
                try {
                    Thread.sleep(Math.round(time) * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                world.getCoordinates()[x][y] = this;
                super.setX(x);
                super.setY(y);
                System.out.println(this.name + " передвинулся на координаты " + x + ":" + y + " за " + Math.round(time) + " секунд.");
            } else {
                System.out.println("Невозможно передвинуться на " + x + ":" + y + ", так как там кто-то стоит");
            }
        } else {
            System.out.println("Невозможно передвинуться, так как скорость равна 0");
        }
    }

    @Override
    public void sniff() {
        System.out.println("Ежик по имени " + this.name + " водить носом *sniff-sniff-sniff*");
    }

    public void tellJoke() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM jokes");
            int id_counter = -1;
            while (resultSet.next()) {
                id_counter++;
            }
            if (id_counter == -1) {
                System.out.println("В базе данных нет шуток, давайте попробуем другую, или добавьте в эту");
                System.exit(1);
            }

            int random = (int) Math.round(Math.random() * id_counter + 1);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT text FROM jokes WHERE id = ?");
            preparedStatement.setInt(1, random);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String joke = resultSet.getString(1);
            String[] lines = joke.split("ggg");
            System.out.println("Ежик рассказывает шутку, добытую из базы данных:");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public boolean isRuffled() {
        return ruffled;
    }

    public void setRuffled(boolean ruffled) {
        this.ruffled = ruffled;
    }

    public boolean isDumped() {
        return dumped;
    }

    public void setDumped(boolean dumped) {
        this.dumped = dumped;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTall() {
        return tall;
    }

    public void setTall(long tall) {
        this.tall = tall;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hedgehog hedgehog = (Hedgehog) o;

        if (age != hedgehog.age) return false;
        if (tall != hedgehog.tall) return false;
        if (height != hedgehog.height) return false;
        if (weight != hedgehog.weight) return false;
        if (ruffled != hedgehog.ruffled) return false;
        if (dumped != hedgehog.dumped) return false;
        if (!name.equals(hedgehog.name)) return false;
        if (connection != null ? !connection.equals(hedgehog.connection) : hedgehog.connection != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + (int) (tall);
        result = 31 * result + (int) (height);
        result = 31 * result + (int) (weight);
        result = 31 * result + (ruffled ? 1 : 0);
        result = 31 * result + (dumped ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hedgehog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", tall=" + tall +
                ", height=" + height +
                ", weight=" + weight +
                ", ruffled=" + ruffled +
                ", dumped=" + dumped +
                '}';
    }
}