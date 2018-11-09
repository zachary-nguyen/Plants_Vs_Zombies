package Model;

import java.awt.*;
import java.io.IOException;

/**
 * Class models a Plant to be implemented by other plants.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class AbstractPlant extends Sprite {

    private int cost;

    public AbstractPlant() throws IOException {
        super();
    }

    public AbstractPlant(Image img, int health, int damage, int counter, int cost) {
        super(img, health, damage, counter);
        this.cost  = cost;
    }

}