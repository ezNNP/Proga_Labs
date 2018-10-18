import java.util.ArrayList;

public class Human implements Creature {
	
	private String name;
	private int age;
	private long tall;
	private ArrayList<Cloth> cloths;

	public Human(String name, int age, long tall, ArrayList<Cloth> cloths) {
		this.name = name;
		this.age = age;
		this.tall = tall;
		this.cloths = cloths;
	}

	public Human(String name, int age, long tall) {
		this.name = name;
		this.age = age;
		this.tall = tall;
		this.cloths = null;
	}
	

	@Override
	public void move() {
		System.out.println(this.name + " прячет голову и попискивает от страха. СТРАШНА ВЫРУБАЙ! ");
	}

	@Override
	public void sound() {
		System.out.println(this.name + " издает непонятные звуки, ведь " + this.name + " " + this.age + " лет. Особенно плохо, что " + this.name + " имеет рост " + this.tall + " сантиметров.");
	}

	public void beHuman() {
		System.out.println("Толерантное общество, уважаем все меньшинства.");
	}

	public void putOnCloth() {
		if (cloths != null) {
			for (Cloth cloth : cloths) {
				cloth.putOn();
			}
		}
	}

	public void takeOffCloth() {
		if (cloths != null) {
			for (Cloth cloth : cloths) {
				cloth.takeOff();
			}
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Human human = (Human) o;

		if (age != human.age) return false;
		if (tall != human.tall) return false;
		if (!name.equals(human.name)) return false;
		return cloths != null ? cloths.equals(human.cloths) : human.cloths == null;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + age;
		result = 31 * result + (int) tall;
		result = 31 * result + (cloths != null ? cloths.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "Человек {" +
				"имя=" + name +
				", возраст=" + age +
				", рост=" + tall +
				'}';
	}
}