package Model;

public class Sunflower extends AbstractPlant {
    public Sunflower() {
        super("S", 100, 0, 0, 50);
    }

    public void generateSun() {
        setCounter(this.getCounter() + 1);
        if (getCounter() == 3) {
            setName("s");
            setCounter(0);
        }
    }
}
