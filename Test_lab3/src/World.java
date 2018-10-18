import java.util.ArrayList;

public class World {

	private ArrayList<Creature> creatures;
	private Weather weather;

	public World(Weather weather, Creature... creatures) {
		this.weather = weather;
		this.creatures = new ArrayList<>();
		for (Creature creature : creatures) {
			this.creatures.add(creature);
		}
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
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		World world = (World) o;

		return creatures.equals(world.creatures);
	}

	@Override
	public int hashCode() {
		return creatures.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder returnStr = new StringBuilder("World {\n");
		returnStr.append("Погода: " + weather + "\n");
		for (Creature creature : creatures) {
			returnStr.append(creature.toString() + "\n");
		}
		returnStr.append('}');
		return returnStr.toString();
	}
}