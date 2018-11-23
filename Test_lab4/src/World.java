import java.util.ArrayList;
import java.util.Arrays;

public class World {

	Creature[][] coordinates;
	private ArrayList<Creature> creatures;
	private Weather weather;

	public World(Weather weather, int width, int height, Creature... creatures) {
        this.creatures = new ArrayList<>();
        coordinates = new Creature[width][height];
        for (Creature creature : creatures) {
            this.creatures.add(creature);

            int x = creature.getX();
            int y = creature.getY();

            if (coordinates[x][y] != null) {
                System.out.println("Извините, эт место уже занято");
                System.exit(666);
            } else {
                coordinates[x][y] = creature;
            }
        }
		this.setWeather(weather);
	}

    public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
		int ord = weather.ordinal();
		for (Creature creature : creatures) {
			if ((creature instanceof Hedgehog)) {
				if (ord < 4) {
                    ((Hedgehog) creature).setDumped(false);
                    ((Hedgehog) creature).setRuffled(false);
                    if (ord == 3) {
                        ((Hedgehog) creature).setRuffled(true);
                    }
				} else {
                    ((Hedgehog) creature).setDumped(true);
                    ((Hedgehog) creature).setRuffled(true);
                }
			}
		}
	}


    public Creature[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Creature[][] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        World world = (World) o;

        if (!Arrays.deepEquals(coordinates, world.coordinates)) return false;
        if (creatures != null ? !creatures.equals(world.creatures) : world.creatures != null) return false;
        return weather == world.weather;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(coordinates);
        result = 31 * result + (creatures != null ? creatures.hashCode() : 0);
        result = 31 * result + weather.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "World{" +
                "coordinates=" + Arrays.toString(coordinates) +
                ", creatures=" + creatures +
                ", weather=" + weather +
                '}';
    }
}