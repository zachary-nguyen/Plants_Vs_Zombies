package View;

import Model.*;

import java.util.Arrays;

public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 18;

    private Sprite[][] map;

    public Backyard() {
        map = new Sprite[HEIGHT][WIDTH];
        for (Sprite[] row : map)
            Arrays.fill(row, null);
    }

    /**
     * Adds a new sprite to the map
     *
     * @param x      x coordinate for the plant
     * @param y      y coordinate for the plant
     * @param sprite Which type of plant is being added
     */
    public void addSprite(int x, int y, Sprite sprite) {
        map[y][x] = sprite;
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
        for (int row = 0; row < HEIGHT - 1; row++) {
            for (int col = 0; col < WIDTH - 1; col++) {
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
        for (int row = 0; row < HEIGHT - 1; row++) {
            for (int col = 0; col < WIDTH - 1; col++) {
                Sprite sprite = map[row][col];
                if (sprite != null) { // Null-pointer safeguard
                    sprite.decrementCounter();
                    if (sprite instanceof AbstractZombie) {
                        AbstractZombie zombie = (AbstractZombie) sprite;
                        //Move Zombie according to speed
                        map[row][col - zombie.getSpeed()] = zombie;
                        map[row][col] = null; //Reset the tile zombie was previously on
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
                        map[row][col + 1] = bullet;
                        map[row][col] = null;
                        col++;
                    } else {
                        //do nothing
                    }
                }
            }
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