package Model;

/**
 * Class models a Plant 
 */
public abstract class AbstractPlant extends Sprite{

    public AbstractPlant() {
        super();
    }

	public AbstractPlant(String name, int health, int damage){
		super(name, health, damage);
	}

}