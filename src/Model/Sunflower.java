package Model;

/**
 * Sunflower class generates sun for the user to collect in order to get money
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Sunflower extends AbstractPlant {

    private boolean collect;
    private static final int MONEY = 25;

    public Sunflower() {
        super("S", 100, 0, 4, 50);
        this.collect = false;
    }

    /**
     * Generate sun after the counter is 0
     */
    public void generateSun() {
        if (this.getCounter() == 0) {
            this.setName("C");
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
        this.setName("S");
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
}
