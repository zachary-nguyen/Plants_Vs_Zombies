package Model;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Backyard class contains the map with all the sprites.
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Mathew Smith
 */
public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 9;

    private Wave currentWave;

    private int money;
    private int score;

    private PriorityQueue<Sprite>[][] map;

    /**
     * Constructor for Backyard class
     */
    public Backyard() {
        this.score = 0;
        this.money = 300;

        map = new PriorityQueue[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                map[row][col] = new PriorityQueue<Sprite>();
            }
        }

    }

    public static int randomGenerator() {
        Random rand = new Random();
        return rand.nextInt(HEIGHT - 1) + 1;
    }

    /**
     * Adds a new sprite to the map
     *
     * @param x      x coordinate for the plant
     * @param y      y coordinate for the plant
     * @param sprite Which type of plant is being added
     * @return Return true if sprite was added successfully else false
     */
    public boolean addSprite(int x, int y, Sprite sprite) {
        if(x >= 0 && x<Backyard.WIDTH && y >= 0 && y < Backyard.HEIGHT && sprite != null) {
            for (Iterator<Sprite> iter = map[y][x].iterator(); iter.hasNext(); ) {
                Sprite curr = iter.next();
                if (curr != null) {
                    System.out.println("cant add");
                    return false;
                }
            }
            System.out.println("adding");
            return map[y][x].add(sprite);
        }
        return false;
    }

    /**
     * Removes a plant sprite from the game.
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     * @return Return true if plant was successfully removed
     */
    public boolean removePlant(int x, int y) {
        if(x >= 0 && x<Backyard.WIDTH && y >= 0 && y < Backyard.HEIGHT) {
            for (Iterator<Sprite> iter = map[y][x].iterator(); iter.hasNext(); ) {
                Sprite curr = iter.next();
                if (curr instanceof AbstractPlant) {
                    return map[y][x].remove(curr);
                }
            }
        }
        return false;
    }

    /**
     * Collects all the sun on the map
     */
    public void collectSun() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (Sprite sprite : map[row][col]) {
                    if ((sprite instanceof Sunflower)) {
                        Sunflower sunflower = (Sunflower) sprite;
                        if (sunflower.isCollect()) {
                            money += sunflower.collectSun();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method that updates all the objects in the backyard and makes them perform actions
     */
    public void updateBackyard() {
        // moves bullets
        for (int row = HEIGHT -1; row >= 0; row--) {
            for (int col = WIDTH -1; col >= 0; col--) {

                for (Iterator<Sprite> iter = map[row][col].iterator(); iter.hasNext(); ) {
                    Sprite sprite = iter.next();
                    if (sprite instanceof Bullet) {
                        Bullet bullet = (Bullet) sprite;
                        // bullet goes off map
                        if (col + bullet.getSpeed() > WIDTH - 1) {
                            iter.remove();
                        } else { // move bullet possible collision
                            map[row][col + bullet.getSpeed()].add(bullet);
                            iter.remove();
                            // could make collisions more efficient
                            //collisionHelper(row, col + bullet.getSpeed());
                        }
                    }

                }
            }
        }

        // checks for bullets hitting zombies
        collisionHelper(0,0);
        // all plants and zombies
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (Iterator<Sprite> iter = map[row][col].iterator(); iter.hasNext(); ) {
                    // for each sprite in the queue
                    Sprite sprite = iter.next();
                    if (sprite instanceof Sunflower) {
                        System.out.println("Sun");
                        Sunflower sunflower = (Sunflower) sprite;
                        sunflower.decrementCounter();
                        sunflower.generateSun();

                    } else if (sprite instanceof Peashooter) {
                        Peashooter peashooter = (Peashooter) sprite;
                        //if (peashooter.canShoot()) {
                        //boolean spawnBullet = true;
                        //if (spawnBullet) {
                        System.out.println("Shooting");
                        Bullet newBullet = peashooter.shootBullet();
                        //newBullet.setMove(false);
                        map[row][col + 1].add(newBullet);
                        //}
                        //}
                    } else if (sprite instanceof AbstractZombie) {
                        Zombie zombie = (Zombie) sprite;

                        AbstractPlant removePlant = null; // plant in next col
                        boolean moveZombie = true;
                        for (Iterator<Sprite> iter2 = map[row][col-zombie.getSpeed()].iterator(); iter2.hasNext(); ) {
                            // for each sprite in the queue
                            Sprite nextPlant = iter2.next();
                            if (nextPlant instanceof AbstractPlant) {
                                moveZombie = false;
                                AbstractPlant plant = (AbstractPlant) nextPlant;
                                plant.setHealth(plant.getHealth() - zombie.getDamage());
                                if (plant.getHealth() < 0) {
                                    removePlant = plant;
                                }
                            }
                        }

                        if (moveZombie) {
                            map[row][col - zombie.getSpeed()].add(zombie);
                            iter.remove();
                            } else {
                            map[row][col - zombie.getSpeed()].remove(removePlant);
                        }
                        // could make collisions more efficient
                        //collisionHelper(row, col - zombie.getSpeed());

                    }


                }
            }
        }

        // checks for zombie hits bullet
        collisionHelper(0,0);
        //Spawn zombie if required and current wave is not complete
        if (!currentWave.isComplete() && currentWave.spawnZombie()) {
            addSprite(WIDTH - 1, randomGenerator(), new Zombie());
        }

    }
    /**
     * Method that treats collision between bullets and zombies.
     * Currently checks all board positions for collision
     * @param row1    Row of collision
     * @param col1    Col of collision
     */
    private void collisionHelper(int row1, int col1){
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Zombie deadZombie = null; // zombie to be removed

                // if the tile contains a bullet and zombie
                for (Iterator<Sprite> sprite1 = map[row][col].iterator(); sprite1.hasNext(); ) {
                    Sprite ent1 = sprite1.next();
                    if (ent1 instanceof Bullet) {
                        for (Iterator<Sprite> sprite2 = map[row][col].iterator(); sprite2.hasNext(); ) {
                            Sprite ent2 = sprite2.next();
                            // zombie on same tile
                            if (ent2 instanceof Zombie) {
                                Bullet bullet = (Bullet) ent1;
                                Zombie zombie = (Zombie) ent2;

                                zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                                sprite1.remove();
                                if (zombie.getHealth() <= 0) {
                                    currentWave.decrementZombiesAlive();
                                    updateScore();
                                    updateMoney();
                                    deadZombie = zombie;
                                    break;

                                }
                            }
                        }

                    }

                }

                map[row][col].remove(deadZombie);
            }
        }
    }


    /**
     * Updates the player's money after each zombie killed.
     * +50 for every zombie killed.
     */
    private void updateMoney() {
        int currentMoney = getMoney();
        currentMoney += 50;
        setMoney(currentMoney);
    }

    /**
     * Updates the player's score after each zombie killed.
     * +1 for every zombie killed.
     */
    private void updateScore() {
        int currentScore = getScore();
        currentScore++;
        setScore(currentScore);
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public int getScore() {
        return score;
    }

    public PriorityQueue[][] getMap() {
        return map;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int numOfZombies) {
        this.currentWave = new Wave(numOfZombies);
    }
}