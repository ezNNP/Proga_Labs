public abstract class Animal implements Creature {

    @Override
    public abstract void move();

    @Override
    public abstract void sound();

    public abstract void goTo(int x, int y);

    public abstract void sniff();

}