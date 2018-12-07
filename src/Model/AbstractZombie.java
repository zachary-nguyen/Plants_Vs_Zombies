package Model;

/**
 * Class models an abstract Zombie to be implemented by other zombie classes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class AbstractZombie extends Sprite {


    private static final long serialVersionUID = 8906148848434617667L;

    private int speed;
    private String imagePath;

    public AbstractZombie() {
        super();
        this.speed = 10;
    }

    public AbstractZombie(String imagePath, int health, int damage, int speed, int counter) {
        super(imagePath, health, damage,counter);
        this.imagePath = imagePath;
        this.speed = speed;
    }

    public AbstractZombie(AbstractZombie abstractZombie){
        super(abstractZombie);
        this.speed = abstractZombie.speed;
    }

    public String toXML(){
        String toXml = "\t\t <image>" + this.getImagePath() + "</image> \n";
        toXml += "\t\t <health>" + this.getHealth() + "</health> \n";
        toXml += "\t\t <damage>" + this.getDamage() + "</damage> \n";
        toXml += "\t\t <speed>" + this.getSpeed() + "</speed> \n";
        toXml += "\t\t <counter>" + this.getCounter() + "</counter> \n";

        return toXml;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}