package Model;

import Controller.Game;

import java.util.Random;

/**
 * Wave class to be passed to backyard. Contains the amount of zombies to be killed for the wave.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Wave {
    private int numZombieSpawn; // number of zombies to end wave
    private int numZombieAlive; // number of zombies to end wave
    private boolean complete;

    public Wave(int numZombieSpawn) {
        this.numZombieSpawn = numZombieSpawn;
        this.numZombieAlive = 0;
        complete = false;
    }

    /**
     * Spawns a zombie along the rightmost column of the map.
     */
    public boolean spawnZombie() {
        Random rand = new Random();
        if (numZombieSpawn == 0) {
            return false;
        }

        // if (spawnCounter == 0) {
        int spawnProbability = rand.nextInt(101);
        if (spawnProbability > 85 || spawnProbability < 15) {
            numZombieSpawn--;
            numZombieAlive++;
            return true;
        }

        return false;
    }


    /**
     * Generates a random delay number based on the current wave for use in
     * spawnZombieComplexity() method.
     *
     * @param wave Current wave number
     * @return int delay number
     */
    private int delayGenerator(int wave) {
        Random rand = new Random();

        int newWave = wave % 5;

        if (newWave != 1) {
            return rand.nextInt(newWave) + 1;
        } else {
            return delayGenerator(newWave + 1);
        }
    }


    public void decrementZombiesRemaining() {
        numZombieSpawn--;
    }

    /**
     * Decrement zombies alive by 1
     */
    public void decrementZombiesAlive() {
        numZombieAlive--;

        if (numZombieAlive == 0 && this.numZombieSpawn == 0) {
            complete = true;

        }
    }

    @Override
    public String toString() {
        return ("--------------WAVE " + Game.getCurrentWaveNumber() + "---------------") +
                ("\nNumber of zombies left : " + numZombieSpawn) +
                ("\nNumber of zombies alive : " + numZombieAlive);
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public int getNumZombieSpawn() {
        return numZombieSpawn;
    }

    public void setNumZombieSpawn(int numZombieSpawn) {
        this.numZombieSpawn = numZombieSpawn;
    }

    public void setNumZombieAlive(int numZombieAlive) {
        this.numZombieAlive = numZombieAlive;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * The current number of Zombies alive
     *
     * @return The number of zombies alive
     */
    public int getNumZombieAlive() {
        return numZombieAlive;
    }

    /**
     * Sets the number of zombies to spawn in a wave
     *
     * @param zombies The number of zombies to spawn
     */
    public void setNumZombiesSpawn(int zombies) {
        numZombieSpawn = zombies;
    }

    /**
     * @return true if wave is complete
     */
    public boolean isComplete() {
        return complete;
    }
}
