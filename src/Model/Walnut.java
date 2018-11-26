package Model;

public class Walnut extends AbstractPlant {

    //Defensive plant that acts as a shield against zombies. Does not do anything other than block zombie attacks.
    //High health, no damage capability to zombies.

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
