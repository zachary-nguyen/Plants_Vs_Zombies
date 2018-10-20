package View;

import Model.*;

import java.util.*;

public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 18;
    private int spawnCounter = 5;

    private Sprite[][] map;

    public Backyard() {
        map = new Sprite[HEIGHT][WIDTH];
        for (Sprite[] row : map)
            Arrays.fill(row, null);
    }

    public int randomGenerator() {
        Random rand = new Random();
        return rand.nextInt(HEIGHT - 1) + 1;
    }

    public void spawnZombie() {
        Zombie z = new Zombie();
        addSprite(WIDTH - 1, randomGenerator(), z);
    }

    /**
     * Adds a new sprite to the map
     *
     * @param x      x coordinate for the plant
     * @param y      y coordinate for the plant
     * @param sprite Which type of plant is being added
     */
    public void addSprite(int x, int y, Sprite sprite) {
        if (map[y][x] == null) {
            map[y][x] = sprite;
        }
    }

    /**
     * Removes a sprite from the map
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removeSprite(int x, int y) {
        map[y][x] = null;
    }

    /**
     * Collects all the sun on the map
     *
     * Returns the total amount of money gathered by all the sunflower plants.
     */
    public int collectSun() {
        int money = 0;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (map[row][col] instanceof Sunflower) {
                    Sunflower sunflower = (Sunflower) map[row][col];
                    if (sunflower.isCollect()) {
                        money += sunflower.collectSun();
                    }
                }
            }
        }
        return money;
    }

    /**
     * Method that updates all the objects in the backyard and makes them perform actions
     */
    public void updateBackyard() {

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Sprite sprite = map[row][col];
                if (sprite != null) { // Null-pointer safeguard
                    sprite.decrementCounter();
                    if (sprite instanceof AbstractZombie) {
                        AbstractZombie zombie = (AbstractZombie) sprite;
                        //check if zombie reaches end of screen
                        if (col - zombie.getSpeed() < 0) {
                            map[row][col] = null;
                            //decrease lives or game over
                            continue;
                        }
                        //Check if zombie is walking into a bullet and decrease health if needed
                        if (map[row][col - zombie.getSpeed()] instanceof Bullet) {
                            Bullet bullet = (Bullet) map[row][col - zombie.getSpeed()];
                            map[row][col - zombie.getSpeed()] = zombie;
                            map[row][col] = null; //Reset the tile zombie was previously on
                            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                            if (zombie.getHealth() <= 0) {
                                map[row][col - zombie.getSpeed()] = null;
                            }
                        //Check if zombie can attack plant
                        }else if(map[row][col - zombie.getSpeed()] instanceof AbstractPlant){
                            AbstractPlant plant = (AbstractPlant) map[row][col - zombie.getSpeed()];
                            plant.setHealth(plant.getHealth()-zombie.getDamage());
                            if(plant.getHealth() <=0){
                                map[row][col - zombie.getSpeed()] = zombie;
                                map[row][col] = null;
                            }
                        } else { //else if there is no collision move the zombie
                            map[row][col - zombie.getSpeed()] = zombie;
                            map[row][col] = null; //Reset the tile zombie was previously on
                        }


                    } else if (sprite instanceof Sunflower) {
                        Sunflower sunflower = (Sunflower) sprite;
                        sunflower.generateSun();
                    } else if (sprite instanceof Peashooter) {
                        Peashooter peashooter = (Peashooter) sprite;
                        if (peashooter.canShoot()) {
                            map[row][col + 1] = peashooter.shoot();
                            col++;
                        }
                    } else if (sprite instanceof Bullet) {
                        Bullet bullet = (Bullet) sprite;
                        //check if bullet goes off screen
                        if (col + bullet.getSpeed() > WIDTH-1) {
                            map[row][col] = null;
                            continue;
                        }
                        //check if the bullet will collide with a zombie
                        if (map[row][col + bullet.getSpeed()] instanceof AbstractZombie) {
                            AbstractZombie zombie = (AbstractZombie) map[row][col + bullet.getSpeed()];
                            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                            if (zombie.getHealth() <= 0) {
                                map[row][col + bullet.getSpeed()] = null;
                            }
                            map[row][col] = null;
                        } else {
                            map[row][col + bullet.getSpeed()] = bullet;
                            map[row][col] = null;
                            col++;
                        }
                    } else {
                        //do nothing
                        continue;
                    }
                }
            }
        }
        //Spawn zombie when needed after the turn is done
        spawnCounter--;
        if (spawnCounter == 0) {
            spawnZombie();
            spawnCounter = randomGenerator();
        }
    }

    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (map[row][col] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(map[row][col].toString() + " ");
                }
            }
            System.out.println();
        }
    }
}