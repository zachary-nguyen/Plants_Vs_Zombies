package Model;

/*  Defensive plant that acts as a shield against zombies. Does not do anything other than block zombie attacks.
    High health, no damage capability to zombies.
*/
public class Walnut extends AbstractPlant {

    private static final long serialVersionUID = -8232567133824446841L;

    public Walnut()  {
        super("src/images/WALNUT.png", 300, 0, 0,50);
    }

    /**
     * Clone constructor
     * @param walnut walnut to be cloned
     */
    public Walnut(Walnut walnut){
        super(walnut);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
