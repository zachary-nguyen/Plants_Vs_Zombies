package Model;

/**
 * Zombie class is a model class for a basic zombie. It has no special attributes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class FlagZombie extends AbstractZombie {

    //Scout Zombie, Lower Damage, Lower Health. Should be spawned at a lower rate than regular Zombies.

    public FlagZombie() {
        super("src/images/FLAGZOMBIE.png", 50, 25, 1, 0);
    }

    public FlagZombie(FlagZombie zombie){
        super(zombie);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}