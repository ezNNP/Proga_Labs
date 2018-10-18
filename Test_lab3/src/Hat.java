public class Hat implements Cloth {

    private boolean isOn;

    public Hat(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void putOn() {
        if (!isOn) {
            System.out.println("Шляпа надета");
            isOn = true;
        } else {
            System.out.println("Шляпа уже надета, не стоит одевать вторую");
        }
    }

    @Override
    public void takeOff() {
        if (isOn) {
            System.out.println("Шляпа снята");
            isOn = false;
        } else {
            System.out.println("Нечего больше снимать, подумой!");
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hat hat = (Hat) o;

        return isOn == hat.isOn;
    }

    @Override
    public String toString() {
        return "Hat{" +
                "isOn=" + isOn +
                '}';
    }
}