package Model;

import Controller.Game;

import java.util.Arrays;
import java.util.Random;

/**
 * Backyard class contains the map with all the sprites.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 18;

    private Wave currentWave;

    private int money;
    private int score;

    private Sprite[][] map;

    /**
     * Constructor for Backyard class
     */
    public Backyard() {
        this.score = 0;
        this.money = 300;

        map = new Sprite[HEIGHT][WIDTH];
        for (Sprite[] row : map)
            Arrays.fill(row, null);
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
        if (map[y][x] instanceof Bullet && sprite instanceof Zombie) {
            Bullet bullet = (Bullet) map[y][x];
            Zombie zombie = (Zombie) sprite;
            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
            map[y][x] = sprite;
            return true;
        }
        if (map[y][x] == null) {
            map[y][x] = sprite;
            return true;
        } else {
            System.out.println("Cannot add there!");
            return false;
        }
    }

    /**
     * Removes a plant sprite from the game.
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removePlant(int x, int y) {
        if ((map[y][x] instanceof AbstractPlant)) {
            map[y][x] = null;
        } else {
            System.out.println("Cannot remove from those coordinates!");
        }
    }

    /**
     * Collects all the sun on the map
     *
     */
    public void collectSun() {
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
                        this.treatZombieCollision(row, col, zombie); //call collision helper method

                    } else if (sprite instanceof Sunflower) {
                        Sunflower sunflower = (Sunflower) sprite;
                        sunflower.generateSun();

                    } else if (sprite instanceof Peashooter) {
                        Peashooter peashooter = (Peashooter) sprite;
                        if (peashooter.canShoot()) {
                            if (map[row][col + 1] instanceof AbstractZombie) {//check if bullet is spawning onto zombie
                                treatBulletCollidedWithZombie(row,col,peashooter.shootBullet());
                            }else {
                                map[row][col + 1] = peashooter.shootBullet();
                                col++;
                            }
                        }
                    } else if (sprite instanceof Bullet) {
                        Bullet bullet = (Bullet) sprite;
                        //check if bullet goes off screen
                        if (col + bullet.getSpeed() > WIDTH - 1) {
                            map[row][col] = null;
                            //make bullet jump over plant in its path.
                        } else if (map[row][col + bullet.getSpeed()] instanceof AbstractPlant) {
                            map[row][col + (bullet.getSpeed() + 1)] = bullet;
                            if (!(map[row][col] instanceof AbstractPlant)) {
                                map[row][col] = null;
                            }
                            col = col + 2;
                            //TODO: make bullet jump over another bullet in its path (incomplete)
                        } else if (map[row][col + bullet.getSpeed()] instanceof Bullet) {
                            map[row][col + (bullet.getSpeed() + 1)] = bullet;
                            if (!(map[row][col] instanceof Sprite)) {
                                map[row][col] = null;
                            }
                            col = col + 2;

                            //check if the bullet will collide with a zombie
                        } else if (map[row][col + bullet.getSpeed()] instanceof AbstractZombie) {
                            treatBulletCollidedWithZombie(row, col, bullet);
                            map[row][col] = null;
                        } else {
                            map[row][col + bullet.getSpeed()] = bullet;
                            map[row][col] = null;
                            col++;
                        }
                    }
                }
            }
        }

        //Spawn zombie if required and current wave is not complete
        if (!currentWave.isComplete() && currentWave.spawnZombie()) {
            addSprite(WIDTH - 1, randomGenerator(), new Zombie());
        }
    }

    /**
     * Method that treats collision between a bullet and zombie.
     * @param row Row of collision
     * @param col Col of collision
     * @param bullet Bullet being collided with
     */
    private void treatBulletCollidedWithZombie(int row, int col, Bullet bullet) {
        AbstractZombie zombie = (AbstractZombie) map[row][col + bullet.getSpeed()];
        zombie.setHealth(zombie.getHealth() - bullet.getDamage());
        if (zombie.getHealth() <= 0) {

            currentWave.decrementZombiesAlive();

            updateScore();
            updateMoney();

            map[row][col + bullet.getSpeed()] = null;
        }
    }

    /**
     * Helper method to treat zombie collision
     *
     * @param row    row index on map
     * @param col    col index on map
     * @param zombie Zombie being treated
     */
    private void treatZombieCollision(int row, int col, AbstractZombie zombie) {
        //Check if zombie is walking into a bullet and decrease health if needed
        //check if zombie reaches end of screen
        if (col - zombie.getSpeed() < 0) {
            map[row][col] = null;
            Game.gameOver = true;
            return;
        }
        if (map[row][col - zombie.getSpeed()] instanceof Bullet) {
            Bullet bullet = (Bullet) map[row][col - zombie.getSpeed()];
            map[row][col - zombie.getSpeed()] = zombie;
            map[row][col] = null; //Reset the tile zombie was previously on
            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
            if (zombie.getHealth() <= 0) {
                updateScore();
                updateMoney(); //Updates Money per zombie killed.
                map[row][col - zombie.getSpeed()] = null;
                currentWave.decrementZombiesAlive();
            }
            //Check if zombie can attack plant
        } else if (map[row][col - zombie.getSpeed()] instanceof AbstractPlant) {
            AbstractPlant plant = (AbstractPlant) map[row][col - zombie.getSpeed()];
            plant.setHealth(plant.getHealth() - zombie.getDamage());
            if (plant.getHealth() <= 0) {
                map[row][col - zombie.getSpeed()] = zombie;
                map[row][col] = null;
            }
        } else { //else if there is no collision move the zombie
            map[row][col - zombie.getSpeed()] = zombie;
            map[row][col] = null; //Reset the tile zombie was previously on
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

    /**
     * Prints the current state of the map for the player to see.
     */
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

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public int getScore() {
        return score;
    }

    public Sprite[][] getMap() {
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