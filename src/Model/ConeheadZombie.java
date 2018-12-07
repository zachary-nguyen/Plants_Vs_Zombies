package Model;

/**
 * Tank Zombie. Higher Health, Higher Damage, Lower Speed. Should be spawned at a way lower rate than regular Zombies.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class ConeheadZombie extends AbstractZombie {

    private static final long serialVersionUID = -5499976094330725593L;

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
