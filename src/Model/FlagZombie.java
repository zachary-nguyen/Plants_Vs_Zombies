package Model;

/**
 * Scout Zombie, Lower Damage, Lower Health. Should be spawned at a lower rate than regular Zombies.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class FlagZombie extends AbstractZombie {


    private static final long serialVersionUID = 4783730690368971247L;

    public FlagZombie() {
        super("src/images/FLAGZOMBIE.png", 50, 25, 1, 0);
    }

    public FlagZombie(FlagZombie zombie){
        super(zombie);
    }

    /**
     * Return the xml format of this sprite
     * @return The xml string format
     */
    public String toXML(){
        String toXml = "\t <FlagZombie> \n";
        toXml += super.toXML();
        toXml += "\t </FlagZombie> \n";
        return toXml;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}