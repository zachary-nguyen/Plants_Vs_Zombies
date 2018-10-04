package Model;

public class Zombie extends AbstractZombie {
    public Zombie() {
        super("Z", 100, 20, 10, 0);
    }

    public void attack(Sprite plant) {
        plant.setHealth(plant.getHealth() - this.getDamage());
    }
}
