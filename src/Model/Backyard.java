package Model;

import Controller.Game;

import java.io.IOException;
import java.util.*;

/**
 * Backyard class contains the map with all the sprites.
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 9;

    private Wave currentWave;

    private int money;
    private int score;

    // enum implements comparable
    // in the order declared from highest to lowest
    public enum Sprites {
        SUNFLOWER, PEASHOOTER, ZOMBIE, BULLET;

    }

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
        /*if (map[y][x].contains(instanceof Bullet) && sprite instanceof Zombie) {
            Bullet bullet = (Bullet) map[y][x];
            Zombie zombie = (Zombie) sprite;
            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
            map[y][x] = sprite;
            sprite.setX(x);
            sprite.setY(y);
            return true;
        }
        if (map[y][x] == null) {
            map[y][x] = sprite;
            sprite.setX(x);
            sprite.setY(y);
            return true;
        } else {
            System.out.println("Cannot add there!");
            return false;
        }*/

        System.out.println("adding");
        return map[y][x].add(sprite);
        //return true;
    }

    /**
     * Removes a plant sprite from the game.
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removePlant(int x, int y) {
        // check if plant is in queue
        for (Sprite sprite : map[y][x]) {
            if ((sprite instanceof AbstractPlant)) {
                map[y][x].remove(sprite);
            }
        }
        /*
        } else {
            System.out.println("Cannot remove from those coordinates!");
        }*/
    }

    /**
     * Collects all the sun on the map
     */
    public void collectSun() throws IOException {
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
    public void updateBackyard() throws IOException {
        // moves bullets
        for (int row = HEIGHT -1; row >= 0; row--) {
            for (int col = WIDTH -1; col >= 0; col--) {
                // need to run for all entities in the queue
                /*for (Sprite sprite : map[row][col]) {
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

                                boolean spawnBullet = true;
                                for (Sprite next : map[row][col + 1]) {
                                    if (next instanceof AbstractZombie) {//check if bullet is spawning onto zombie
                                        treatBulletCollidedWithZombie(row, col, peashooter.shootBullet(), (AbstractZombie) next);
                                        spawnBullet = false;
                                        break;
                                    }
                                }

                                if (spawnBullet) {
                                    Bullet newBullet = peashooter.shootBullet();
                                    newBullet.setMove(false);
                                    map[row][col + 1].add(newBullet);
                                }
                            }
                        } else if (sprite instanceof Bullet) {
                            Bullet bullet = (Bullet) sprite;
                            if (!bullet.isMove()) {
                                bullet.setMove(true);
                                continue;
                            }
                            //check if bullet goes off screen
                            if (col + bullet.getSpeed() > WIDTH - 1) {
                                map[row][col].remove(bullet);
                                continue;
                            }
                            // zombie in next column
                            for (Sprite next : map[row][col + 1]) {
                                if (next instanceof AbstractZombie) {
                                    treatBulletCollidedWithZombie(row, col, bullet, (AbstractZombie) next);
                                    map[row][col].remove(bullet);
                                }
                            }

                            // moves bullet
                            if (map[row][col].contains(bullet)) {
                                map[row][col + bullet.getSpeed()].add(bullet);
                                map[row][col].remove(bullet);
                                bullet.setMove(false);
                            }
                        }
                    }
                }*/

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
                            //collisionHelper(row, col + bullet.getSpeed());
                        }
                    }

                }
            }
        }

        collisionHelper(0,0);
        // all plants
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (Iterator<Sprite> iter = map[row][col].iterator(); iter.hasNext(); ) {
                    Sprite sprite = iter.next();
                    if (sprite instanceof Sunflower) {
                        System.out.println("Sun");
                        Sunflower sunflower = (Sunflower) sprite;
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
                        map[row][col - zombie.getSpeed()].add(zombie);
                        iter.remove();
                        //collisionHelper(row, col - zombie.getSpeed());
                    }


                }
            }
        }

        collisionHelper(0,0);
        //Spawn zombie if required and current wave is not complete
        if (!currentWave.isComplete() && currentWave.spawnZombie()) {
            addSprite(WIDTH - 1, randomGenerator(), new Zombie());
        }

    }

    private void collisionHelper(int row1, int col1){
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {

                for (Iterator<Sprite> sprite1 = map[row][col].iterator(); sprite1.hasNext(); ) {
                    Sprite ent1 = sprite1.next();
                    if (ent1 instanceof Bullet) {
                        for (Iterator<Sprite> sprite2 = map[row][col].iterator(); sprite2.hasNext(); ) {
                            Sprite ent2 = sprite2.next();
                            if (ent2 instanceof Zombie) {
                                Bullet bullet = (Bullet) ent1;
                                Zombie zombie = (Zombie) ent2;

                                zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                                sprite1.remove();
                                if (zombie.getHealth() <= 0) {
                                    currentWave.decrementZombiesAlive();
                                    updateScore();
                                    updateMoney();
                                    sprite2.remove();
                                    
                                }

                                //return;
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Method that treats collision between a bullet and zombie.
     *
     * @param row    Row of collision
     * @param col    Col of collision
     * @param bullet Bullet being collided with
     */
    private void treatBulletCollidedWithZombie(int row, int col, Bullet bullet, AbstractZombie zombie) {
        zombie.setHealth(zombie.getHealth() - bullet.getDamage());

        if (zombie.getHealth() <= 0) {
            currentWave.decrementZombiesAlive();

            updateScore();
            updateMoney();

            map[row][col + bullet.getSpeed()].remove(zombie);
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

        //check if zombie reaches end of screen
        if (col - zombie.getSpeed() < 0) {
            map[row][col].remove(zombie);
            Game.gameOver = true;
            return;
        }

        boolean moveZombie = true;
        for (Sprite sprite : map[row][col - zombie.getSpeed()]) {
            //Sprite sprite = iter.next();

            //Check if zombie is walking into a bullet and decrease health if needed
            if (sprite instanceof Bullet) {
                Bullet bullet = (Bullet) sprite;
                zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                map[row][col - zombie.getSpeed()].remove(bullet);
                if (zombie.getHealth() <= 0) {
                    moveZombie = false;
                    updateScore();
                    updateMoney(); //Updates Money per zombie killed.
                    map[row][col].remove(zombie);
                    currentWave.decrementZombiesAlive();
                }
                //Check if zombie can attack plant
            } else if (sprite instanceof AbstractPlant) {
                AbstractPlant plant = (AbstractPlant) sprite;
                plant.setHealth(plant.getHealth() - zombie.getDamage());
                if (plant.getHealth() <= 0) {
                    map[row][col - zombie.getSpeed()].remove(plant);
                } else {
                    moveZombie = false;
                }
            }
        }

        if (moveZombie) {
            map[row][col - zombie.getSpeed()].add(zombie);
            map[row][col].remove(zombie); //Reset the tile zombie was previously on
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