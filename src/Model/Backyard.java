package Model;

import Controller.Game;

import java.io.Serializable;
import java.util.*;

/**
 * Backyard class contains the map with all the sprites.
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Mathew Smith
 */
public class Backyard implements Serializable {


    public static final int HEIGHT = 5;
    public static final int WIDTH = 9;
    private static final long serialVersionUID = 6173803228893696282L;

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

    /**
     * Returns a value between 0-4 to randomly select where to place the next zombie
     *
     * @return Return a number between 0-4
     */
    private int randomGenerator() {
        Random rand = new Random();
        return rand.nextInt(HEIGHT);
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
        if (x >= 0 && x < Backyard.WIDTH && y >= 0 && y < Backyard.HEIGHT && sprite != null) {
            for (Sprite curr : map[y][x]) {
                if (curr instanceof AbstractPlant || curr instanceof AbstractZombie) {
                    return false; //the tile has a plant or zombie so can't add(adding on bullets is fine)
                }
            }
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
        if (x >= 0 && x < Backyard.WIDTH && y >= 0 && y < Backyard.HEIGHT) {
            for (Sprite curr : map[y][x]) {
                if (curr instanceof AbstractPlant) {
                    return map[y][x].remove(curr);
                }
            }
        }
        return false;
    }

    /**
     * Collects sun from tile that was clicked
     */
    public void collectSun(int row, int col) {
        for (Sprite sprite : map[row][col]) {
            if ((sprite instanceof Sunflower)) {
                Sunflower sunflower = (Sunflower) sprite;
                if (sunflower.isCollect()) {
                    money += sunflower.collectSun();
                }
            }
        }

    }

    /**
     * Method that updates all the objects in the backyard and makes them perform actions
     */
    public void updateBackyard() {

        this.moveBullets(); //move all the bullets
        // checks for bullets hitting zombies
        this.collisionHelper();

        // all plants and zombies
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (Iterator<Sprite> iter = map[row][col].iterator(); iter.hasNext(); ) {
                    // for each sprite in the queue
                    Sprite sprite = iter.next();
                    sprite.decrementCounter(); //decrement counter for each sprite
                    if (sprite instanceof Sunflower) {
                        Sunflower sunflower = (Sunflower) sprite;
                        sunflower.generateSun();

                    } else if (sprite instanceof Peashooter) {
                        Peashooter peashooter = (Peashooter) sprite;
                        if (peashooter.canShoot()) {
                            Bullet newBullet = peashooter.shootBullet();
                            newBullet.setMove(false);
                            map[row][col + 1].add(newBullet);
                        }

                    } else if (sprite instanceof Repeater) {
                        Repeater repeater = (Repeater) sprite;
                        if (repeater.canShoot()) {
                            Bullet newBullet = repeater.shootBullet();
                            newBullet.setMove(false);
                            map[row][col + 1].add(newBullet);
                        }
                    } else if (sprite instanceof AbstractZombie) {
                        AbstractZombie zombie = (AbstractZombie) sprite;

                        AbstractPlant removePlant = null; // plant in next col
                        boolean moveZombie = true;
                        if (col - zombie.getSpeed() <= -1) { //end game if zombie reaches the end
                            Game.gameOver = true;
                        } else {
                            for (Sprite nextPlant : map[row][col - zombie.getSpeed()]) {
                                // for each sprite in the queue
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
                        }
                    }
                }
            }
        }

        // checks for zombie hits bullet
        this.collisionHelper();
        //Spawn zombie if required and current wave is not complete
        if (!currentWave.isComplete() && currentWave.spawnZombie()) {
            addSprite(WIDTH - 1, randomGenerator(), currentWave.getZombie());
        }
    }

    /**
     * Method that treats collision between bullets and zombies.
     * Currently checks all board positions for collision
     */
    private void collisionHelper() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                AbstractZombie deadZombie = null; // zombie to be removed

                //   if the tile contains a bullet and zombie
                for (Iterator<Sprite> sprite1 = map[row][col].iterator(); sprite1.hasNext(); ) {
                    Sprite ent1 = sprite1.next();
                    if (ent1 instanceof Bullet) {
                        for (Sprite ent2 : map[row][col]) { //iterate the priority queue
                            // zombie on same tile
                            if (ent2 instanceof AbstractZombie) {
                                Bullet bullet = (Bullet) ent1;
                                AbstractZombie zombie = (AbstractZombie) ent2;

                                zombie.setHealth(zombie.getHealth() - bullet.getDamage()); //reduce zombie health
                                sprite1.remove();
                                if (zombie.getHealth() <= 0) {
                                    currentWave.decrementZombiesAlive();
                                    updateScore();
                                    updateMoney();
                                    deadZombie = zombie;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
                map[row][col].remove(deadZombie);
            }
        }
    }

    /**
     * Helper method for updateBackyard to move all the bullets on the map
     */
    private void moveBullets() {
        // moves bullets
        for (int row = HEIGHT - 1; row >= 0; row--) {
            for (int col = WIDTH - 1; col >= 0; col--) {

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
                        }
                    }
                }
            }
        }
    }

    /**
     * Clone the backyard to use for undo/redo functionality.
     *
     * @return Return the cloned backyard.
     */
    public Backyard cloneBackyard() {
        Backyard backyard = new Backyard();
        backyard.money = this.money;
        backyard.score = this.score;

        PriorityQueue<Sprite>[][] newMap = new PriorityQueue[HEIGHT][WIDTH];

        //Create new map
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                newMap[row][col] = new PriorityQueue<>();
            }
        }

        //repopulate the new map with clones of sprites
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (this.map[row][col].peek() != null) {
                    Sprite sprite = this.map[row][col].peek();
                    if (sprite instanceof Sunflower) {
                        newMap[row][col].add(new Sunflower((Sunflower) sprite));
                    } else if (sprite instanceof Peashooter) {
                        newMap[row][col].add(new Peashooter((Peashooter) sprite));
                    } else if (sprite instanceof Bullet) {
                        newMap[row][col].add(new Bullet((Bullet) sprite));
                    } else if (sprite instanceof Zombie) {
                        newMap[row][col].add(new Zombie((Zombie) sprite));
                    } else if (sprite instanceof Walnut) {
                        newMap[row][col].add(new Walnut((Walnut) sprite));
                    } else if (sprite instanceof Repeater) {
                        newMap[row][col].add(new Repeater((Repeater) sprite));
                    } else if (sprite instanceof ConeheadZombie) {
                        newMap[row][col].add(new ConeheadZombie((ConeheadZombie) sprite));
                    } else if (sprite instanceof FlagZombie) {
                        newMap[row][col].add(new FlagZombie((FlagZombie) sprite));
                    }
                }
            }
        }

        //clone the current wave
        Wave wave = new Wave(this.currentWave.getNumZombiesSpawn());
        wave.setComplete(this.currentWave.isComplete());
        wave.setNumZombieAlive(this.currentWave.getNumZombieAlive());
        wave.setNumZombiesSpawn(this.currentWave.getNumZombiesSpawn());
        wave.setZombieSpawn(this.currentWave.getZombieSpawn());
        Wave.setWaveNumber(Wave.getWaveNumber());

        backyard.setMap(newMap);
        backyard.setCurrentWave(wave);

        return backyard;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Backyard backyard = (Backyard) o;

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (!Arrays.deepEquals(map[row][col].toArray(), backyard.getMap()[row][col].toArray())) {
                    return false;
                }
            }
        }

        return money == backyard.money &&
                score == backyard.score &&
        currentWave.equals(backyard.currentWave);
        // Arrays.deepEquals(map, backyard.map);

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentWave, money, score);
        result = 31 * result + Arrays.hashCode(map);
        return result;
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

    public void setCurrentWaveAmountOfZombies(int numOfZombies) {
        this.currentWave = new Wave(numOfZombies);
    }

    public void setCurrentWave(Wave currentWave) {
        this.currentWave = currentWave;
    }

    public void setMap(PriorityQueue<Sprite>[][] map) {
        this.map = map;
    }
}