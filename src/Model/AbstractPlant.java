package Model;

/**
 * Class models a Plant to be implemented by other plants.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class AbstractPlant extends Sprite {

    private int cost;

    public AbstractPlant() {
        super();
    }

    public AbstractPlant(String imgPath, int health, int damage, int counter, int cost) {
        super(imgPath, health, damage, counter);
        this.cost  = cost;
    }

}