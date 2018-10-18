public class Hedgehog extends Animal {

    private String name;
    private int age;
    private long tall;

    public Hedgehog(String name, int age, long tall) {
        this.name = name;
        this.age = age;
        this.tall = tall;
    }

    @Override
    public void move() {
        System.out.println("Ежик по имени " + this.name + " куда-то идет");
    }

    @Override
    public void sound() {
        System.out.println(this.name + " издает звук *фрфрфрфрфр*");
    }

    @Override
    public void goTo(int x, int y) {
        System.out.println("Ежик по имени " + this.name + " идет на координаты " + x + "; " + y);
    }

    @Override
    public void sniff() {
        System.out.println("Ежик по имени " + this.name + " водить носом *sniff-sniff-sniff*");
    }

    public void tellJoke() {
        System.out.println("======== Анек ========");
        System.out.println("В чем отличие между ежиком и наждачной бумагой?");
        System.out.println("Такие звуки издает ежик *фрфрфрфрфрфр*");
        System.out.println("Wait for it");
        System.out.println("А вот наждачка *фрфрфр");
        System.out.println("С!@# сколько крови от этого ежа");
        System.out.println("==== Конец  анека ====");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hedgehog hedgehog = (Hedgehog) o;

        if (age != hedgehog.age) return false;
        if (tall != hedgehog.tall) return false;
        return name.equals(hedgehog.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + (int) tall;
        return result;
    }

    @Override
    public String toString() {
        return "Ежик{" +
                "имя=" + name +
                ", возраст=" + age +
                ", длина=" + tall +
                '}';
    }
}