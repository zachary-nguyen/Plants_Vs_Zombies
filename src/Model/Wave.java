package Model;

import java.util.Random;

public class Wave {
    private int numZombieSpawn; // number of zombies to end wave
    private int numZombieAlive; // number of zombies to end wave
    private boolean complete;
    public static int waveNumber;

    public Wave(int numZombieSpawn) {
        this.numZombieSpawn = numZombieSpawn;
        this.numZombieAlive = 0;
        complete = false;
        Wave.waveNumber += 1;
    }

    /**
     * Spawns a zombie along the rightmost column of the map.
     */

    public boolean spawnZombie() {
        Random rand = new Random();
        if (numZombieSpawn == 0) {
            return false;
        }

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
        return ("--------------WAVE " + Wave.waveNumber + "---------------") +
                ("\nNum zombies Spawn : " + numZombieSpawn) +
                ("\nNum zombies Alive : " + numZombieAlive);
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/


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
     * Gets the number of zombies to spawn
     *
     * @return Returns the number of zombies left to spawn
     */
    public int getNumZombiesSpawn() {
        return numZombieSpawn ;
    }


    /**
     * @return true if wave is complete
     */
    public boolean isComplete() {
        return complete;
    }
}
