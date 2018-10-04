package Model;

public class Sunflower extends AbstractPlant {
    public Sunflower() {
        super("S", 100, 0, 3, 50);
    }

    public void generateSun() {
        if (getCounter() == 3) {
            setName("C");
            setCounter(0);
        }
    }
}
