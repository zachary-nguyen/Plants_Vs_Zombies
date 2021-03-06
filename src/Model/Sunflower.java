package Model;

/**
 * Sunflower class generates sun for the user to collect in order to get money
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Sunflower extends AbstractPlant {


    private static final long serialVersionUID = 7081326709614274967L;

    private boolean collect;
    private static final int MONEY = 25;

    public Sunflower() {
        super("src/images/Sunflower.png", 100, 0, 4, 50);
        this.collect = false;
    }

    /**
     * Clone constructor
     * @param sunflower Sunflower to be cloned
     */
    public Sunflower(Sunflower sunflower){
        super(sunflower);
    }

    /**
     * Generate sun after the counter is 0
     */
    public void generateSun() {
        if (this.getCounter() == 0) {
            this.setImage("src/images/Sun.png");
            this.setCounter(4);
            this.collect = true;
        }
    }

    /**
     * Collect the sun that is generated and set plant back to normal state.
     *
     * @return the amount of money generated by the sunflower
     */
    public int collectSun(){
        this.setImage("src/images/Sunflower.png");
        this.collect = false;
        return MONEY;
    }

    /**
     * Checks if sun can be collected
     * @return Return true if sun can be collected else false
     */
    public boolean isCollect() {
        return collect;
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
