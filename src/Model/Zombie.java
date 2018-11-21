package Model;

/**
 * Zombie class is a model class for a basic zombie. It has no special attributes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Zombie extends AbstractZombie {

    public Zombie() {
        super("src/images/ZOMBIE.png", 100, 50, 1, 0);
    }

    public Zombie(Zombie zombie){
        super(zombie);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
