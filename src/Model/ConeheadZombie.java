package Model;

/**
 * Zombie class is a model class for a basic zombie. It has no special attributes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class ConeheadZombie extends AbstractZombie {

    //Tank Zombie. Higher Health, Higher Damage, Lower Speed. Should be spawned at a way lower rate than regular Zombies.

    public ConeheadZombie() {
        super("src/images/CONEHEAD.png", 150, 75, 1, 0);
    }

    public ConeheadZombie(ConeheadZombie zombie){
        super(zombie);
    }

    /**
     * Return the xml format of this sprite
     * @return The xml string format
     */
    public String toXML(){
        String toXml = "\t <ConeheadZombie> \n";
        toXml += super.toXML();
        toXml += "\t </ConeheadZombie> \n";
        return toXml;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
