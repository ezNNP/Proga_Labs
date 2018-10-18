import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
		ArrayList<Cloth> cloths = new ArrayList<>();
		cloths.add(new Hat(false));
    	World world = new World(
    	        Weather.RAIN,
    	        new Human("Френкен Снорк", 55, 169, cloths),
                new Human("Мумми-тролль", 150, 204),
                new Human("Снифф", 39, 90),
                new Hedgehog("ежик", 5, 15)
        );

    	for (Creature creature: world.getCreatures()) {
            System.out.println("==========================");
    	    creature.move();
    	    creature.sound();
    	    if (creature instanceof Human) {
    	        ((Human) creature).beHuman();
    	        ((Human) creature).putOnCloth();
    	        ((Human) creature).takeOffCloth();
            } else if (creature instanceof Hedgehog) {
    	        ((Hedgehog) creature).goTo(100, 50);
    	        ((Hedgehog) creature).sniff();
    	        ((Hedgehog) creature).tellJoke();
            }
            System.out.println("==========================");
        }

        System.out.println(world.toString());
    }
}