package View;

import Controller.*;
import Model.*;

import java.util.*;

public class Backyard {

    public static final int HEIGHT = 5;
    public static final int WIDTH = 18;


    private static int spawnCounter = 5; // turns between zombie spawns
    private static int numZombieSpawn = 0; // number of zombies to end wave

    private static int numZombieAlive = 0; // number of zombies to end wave

    private int money;
    private int score;
    public boolean addFlag = false;


    private Sprite[][] map;

    public Backyard() {
        this.score = 0;
        this.money = 300;

        map = new Sprite[HEIGHT][WIDTH];
        for (Sprite[] row : map)
            Arrays.fill(row, null);
    }

    /**
     * Sets the number of zombies to spawn in a wave
     * @param zombies The number of zombies to spawn
     */
    public static void setNumZombiesSpawn(int zombies) {
        numZombieSpawn= zombies;
    }

    /**
     * The current number of Zombies alive
     * @return The number of zombies alive
     */
    public static int getNumZombieAlive() {
        return numZombieAlive;
    }

    /**
     * Used to spawn zombies randomly along the rightmost column.
     *
     * @return random int number between specified index.
     */
    public int randomGenerator() {
        Random rand = new Random();
        return rand.nextInt(HEIGHT - 1) + 1;
    }

    /**
     * Sets the number of turns until a zombie spawns
     * @param spawnCounter
     */
    public static void setSpawnCounter(int spawnCounter) {
        Backyard.spawnCounter = spawnCounter;
    }

    /**
     *  Spawns a zombie along the rightmost column of the map.
     */
    public void spawnZombie() {
        Zombie z = new Zombie();
        numZombieSpawn--;
        numZombieAlive++;
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
            addFlag = true;
        }
        else {
            System.out.println("Cannot add there!");
            addFlag = false;
        }
    }

    /**
     * Removes a plant sprite from the game.
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removeSprite(int x, int y) {
        if (!(map[x][y] instanceof AbstractZombie)) {
            map[y][x] = null;
        }
        else {
            System.out.println("Cannot remove from those coordinates!");
        }
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
                endGameDetection(row, col); //end game check

                if (sprite != null) { // Null-pointer safeguard

                    sprite.decrementCounter();

                    if (sprite instanceof AbstractZombie) {
                        AbstractZombie zombie = (AbstractZombie) sprite;

                        //check if zombie reaches end of screen
                        if (col - zombie.getSpeed() < 0) {
                            map[row][col] = null;
                            continue;
                        }
                        this.treatZombieCollision(row, col, zombie); //call collision helper method

                    }

                    else if (sprite instanceof Sunflower) {
                        Sunflower sunflower = (Sunflower) sprite;
                        sunflower.generateSun();


                    }

                    else if (sprite instanceof Peashooter) {
                        Peashooter peashooter = (Peashooter) sprite;
                        Game game = new Game();
                        if (peashooter.canShoot()) {
                            if ((game.getWaveNumber() % 3) == 0) { //slows down bullet fire rate tremendously.
                                map[row][col + 1] = peashooter.shootBullet();
                                col++;
                            }
                            else {
                                //do nothing
                            }
                        }
                    }

                    else if (sprite instanceof Bullet) {
                        Bullet bullet = (Bullet) sprite;

                        //check if bullet goes off screen
                        if (col + bullet.getSpeed() > WIDTH-1) {
                            map[row][col] = null;
                            continue;
                        }

                        //make bullet jump over plant in its path.
                        if (map[row][col + bullet.getSpeed()] instanceof AbstractPlant) {
                            map[row][col + (bullet.getSpeed() + 1)] = bullet;
                            if (!(map[row][col] instanceof AbstractPlant)) {
                                map[row][col] = null;
                            }
                            col = col + 2;
                        }

                        //TODO: make bullet jump over another bullet in its path (incomplete)
                        if (map[row][col + bullet.getSpeed()] instanceof Bullet) {
                            map[row][col + (bullet.getSpeed() + 1)] = bullet;
                            if (!(map[row][col] instanceof Sprite)) {
                                map[row][col] = null;
                            }
                            col = col + 2;
                        }

                        //check if the bullet will collide with a zombie
                        if (map[row][col + bullet.getSpeed()] instanceof AbstractZombie) {
                            AbstractZombie zombie = (AbstractZombie) map[row][col + bullet.getSpeed()];
                            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
                            if (zombie.getHealth() <= 0) {

                                numZombieAlive--;

                                updateScore();
                                updateMoney();

                                map[row][col + bullet.getSpeed()] = null;
                            }
                            map[row][col] = null;
                        }

                        else {
                            map[row][col + bullet.getSpeed()] = bullet;
                            map[row][col] = null;
                            col++;
                        }

                    }

                    else {
                        //do nothing
                        continue;
                    }
                }
            }
        }



        //Spawn zombie when needed after the turn is done
        spawnCounter--;
        if (spawnCounter == 0 && numZombieSpawn != 0) {
            spawnZombie();
            spawnCounter = randomGenerator();

    }

    /**
     * Generates a random delay number based on the current wave for use in
     * spawnZombieComplexity() method.
     *
     * @param wave Current wave number
     * @return int delay number
     */
    public int delayGenerator(int wave) {
        Random rand = new Random();

        int newWave = wave % 5;

        if (newWave != 0) {
           return rand.nextInt(newWave) + 1;
        }
        else {
           return delayGenerator(newWave + 1);
        }
    }

    /**
     * Generates zombies at a random and even pace based on random ints and the current round number.
     */
    public void spawnZombieComplexity() {
        Game game = new Game();

        int waveNum = game.getWaveNumber();

        int delay = delayGenerator(waveNum);

        while (delay != 0) {
            if ((delay % 2) == 0) {
                spawnZombie(); //spawn zombie if the number generate by the delay generator is even.
            }
            delay--;

        }

        //System.out.println("Num zombies Spawn : " + numZombieSpawn);
        //System.out.println("Num zombies Alive : " + numZombieAlive);
        //System.out.println("Spawn counter : " + spawnCounter);
        // all zombies have been killed and no more spawn
        if (numZombieAlive == 0 && numZombieSpawn == 0){
            Game.endRound = true;
        }
    }

    /**
     * Helper method to treat zombie collision
     * @param row row index on map
     * @param col col index on map
     * @param zombie Zombie being treated
     */
    public void treatZombieCollision(int row, int col, AbstractZombie zombie){
        //Check if zombie is walking into a bullet and decrease health if needed
        if (map[row][col - zombie.getSpeed()] instanceof Bullet) {
            Bullet bullet = (Bullet) map[row][col - zombie.getSpeed()];
            map[row][col - zombie.getSpeed()] = zombie;
            map[row][col] = null; //Reset the tile zombie was previously on
            zombie.setHealth(zombie.getHealth() - bullet.getDamage());
            if (zombie.getHealth() <= 0) {
                updateScore();
                updateMoney(); //Updates Money per zombie killed.
                map[row][col - zombie.getSpeed()] = null;
                numZombieAlive--;
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
    }

    /**
     * Determines when the player has lost the game based on Zombie position.
     *
     * @param row horizontal index on map
     * @param col vertical index on map
     */
    public void endGameDetection(int row, int col) {
        Game game = new Game();
        if (map[row][0] instanceof AbstractZombie) {
            game.endFlag = true;
        }
    }

    /**
     * Updates the player's money after each zombie killed.
     * +50 for every zombie killed.
     */
    public void updateMoney() {
        int currentMoney = getMoney();
        currentMoney += 50;
        setMoney(currentMoney);
    }

    /**
     * Updates the player's score after each zombie killed.
     * +1 for every zombie killed.
     */
    public void updateScore() {
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

    public int getScore() {
        return score;
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

}