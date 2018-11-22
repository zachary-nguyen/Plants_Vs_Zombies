package Model;

public class Wallnut extends AbstractPlant {

    //Defensive plant that acts as a shield against zombies. Does not do anything other than block zombie attacks.
    //High health, no damage capability to zombies.

    public Wallnut()  {
        super("src/images/WALLNUT.png", 300, 0, 2,50);
    }

    /**
     * Clone constructor
     * @param wallnut wallnut to be cloned
     */
    public Wallnut(Wallnut wallnut){
        super(wallnut);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
