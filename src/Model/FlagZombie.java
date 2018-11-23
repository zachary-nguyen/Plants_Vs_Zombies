package Model;

/**
 * Zombie class is a model class for a basic zombie. It has no special attributes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class FlagZombie extends AbstractZombie {

    //Scout Zombie. Higher Speed, Lower Damage, Lower Health. Should be spawned at a lower rate than regular Zombies.

    public FlagZombie() {
        super("src/images/FLAGZOMBIE.png", 50, 25, 2, 0);
    }

    public FlagZombie(Zombie zombie){
        super(zombie);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}