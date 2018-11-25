package Model;

import java.util.Random;

public class Wave {
    private int numZombieSpawn; // number of zombies to end wave
    private int numZombieAlive; // number of zombies to end wave
    private boolean complete;
    private static int waveNumber;

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
        if (spawnProbability > 90 || spawnProbability < 10) {
            numZombieSpawn--;
            numZombieAlive++;
            return true;
        }

        return false;
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

    public AbstractZombie getZombie(){
        Random rand = new Random();
        int zombieSpawn = rand.nextInt(101);

        if(zombieSpawn <= 60){
            return new Zombie();
        }else if (zombieSpawn <= 80){
            return new FlagZombie();
        }else{
            return new ConeheadZombie();
        }
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

    public int getNumZombieSpawn() {
        return numZombieSpawn;
    }

    public void setNumZombieAlive(int numZombieAlive) {
        this.numZombieAlive = numZombieAlive;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public static int getWaveNumber() {
        return waveNumber;
    }

    public static void setWaveNumber(int waveNumber) {
        Wave.waveNumber = waveNumber;
    }

    /**
     * @return true if wave is complete
     */
    public boolean isComplete() {
        return complete;
    }
}
