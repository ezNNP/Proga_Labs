import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Human extends Creature {
	
	private String name;
	private int age;
	private long tall;
	private ArrayList<Cloth> cloths;
	private int charism;
	private float headDiametr;

	public Human(String name, int age, long tall, ArrayList<Cloth> cloths, int charism, Fear fear, float headDiametr, int x, int y, int speed) {
		super(name, x, y, speed, fear);
		this.name = name;
		this.age = age;
		this.tall = tall;
		this.cloths = cloths;
		this.charism = charism;
		this.headDiametr = headDiametr;
	}

	public Human(String name, int age, long tall, int charism, Fear fear, float headDiametr, int x, int y, int speed) {
		super(name, x, y, speed, fear);
		this.name = name;
		this.age = age;
		this.tall = tall;
		this.cloths = null;
		this.charism = charism;
		this.headDiametr = headDiametr;
	}




	public void beHuman(World world, Hedgehog hedgehog) {
        if ((Math.abs(super.getX() - hedgehog.getX()) <= 1) && (Math.abs(super.getY() - hedgehog.getY()) <= 1)) {
            int ordinal = hedgehog.getFear().ordinal();
            long method = Math.round(Math.random() * 6 - 3);
			System.out.println("Из-за метода успокаивания страх изменился на " + method);
            if (ordinal > method) {
                ordinal -= method;
            } else {
                ordinal = 0;
            }


            if (world.getWeather().ordinal() > 3) {
                ordinal += world.getWeather().ordinal();
				System.out.println("Из-за погоды страх изменился на -" + world.getWeather().ordinal());
            } else {
                ordinal -= world.getWeather().ordinal();
				System.out.println("Из-за погоды страх изменился на " + world.getWeather().ordinal());
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

            hedgehog.setFear(Fear.values()[ordinal]);
        } else {
            System.out.println("Этот ёжик слишком далеко...");
        }

	}

	public void putOnCloth() {
		if (cloths != null) {
			for (Cloth cloth : cloths) {
				cloth.putOn(this);
			}
		} else {
            System.out.println("У вас нет одежды");
        }
	}

	public void takeOffCloth() {
		if (cloths != null) {
			for (Cloth cloth : cloths) {
				cloth.takeOff(this);
			}
		} else {
            System.out.println("У вас и так не одежды");
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

	public void setAge(int age) {
		this.age = age;
	}

	public long getTall() {
		return tall;
	}

	public void setTall(long tall) {
		this.tall = tall;
	}

	public ArrayList<Cloth> getCloths() {
		return cloths;
	}

	public void setCloths(ArrayList<Cloth> cloths) {
		this.cloths = cloths;
	}

	public int getCharism() {
		return charism;
	}

	public void setCharism(int charism) {
		this.charism = charism;
	}

	public float getHeadDiametr() {
		return headDiametr;
	}

	public void setHeadDiametr(float headDiametr) {
		this.headDiametr = headDiametr;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Human human = (Human) o;

		if (age != human.age) return false;
		if (tall != human.tall) return false;
		if (charism != human.charism) return false;
		if (Float.compare(human.headDiametr, headDiametr) != 0) return false;
		if (!name.equals(human.name)) return false;
		if (cloths != null ? !cloths.equals(human.cloths) : human.cloths != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + age;
		result = 31 * result + (int) (tall);
		result = 31 * result + (cloths != null ? cloths.hashCode() : 0);
		result = 31 * result + charism;
		result = 31 * result + (Math.round(headDiametr));
		return result;
	}

	@Override
	public String toString() {
		return "Human{" +
				"name='" + name + '\'' +
				", age=" + age +
				", tall=" + tall +
				", cloths=" + cloths.toString() +
				", charism=" + charism +
				", headDiametr=" + headDiametr +
				'}';
	}
}