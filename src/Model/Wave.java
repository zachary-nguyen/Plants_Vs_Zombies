package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Wave implements Serializable {

    private static final long serialVersionUID = -6348065130252775123L;
    private int numZombieSpawn; // number of zombies to end wave
    private int numZombieAlive; // number of zombies to end wave
    private boolean complete;
    private static int waveNumber;
    private ArrayList<AbstractZombie> zombieSpawn;

    public Wave(int numZombieSpawn) {
        this.numZombieSpawn = numZombieSpawn;
        this.numZombieAlive = 0;
        complete = false;
        Wave.waveNumber += 1;
        this.zombieSpawn = new ArrayList<>();
    }

    public Wave(int zombies, int flagZombies, int coneZombies) {
        this.numZombieSpawn = zombies + flagZombies + coneZombies;
        this.numZombieAlive = 0;
        complete = false;
        Wave.waveNumber += 1;
        this.zombieSpawn = new ArrayList<>();
        //need to fill arrraylist with zombies
        while (zombies != 0 || flagZombies != 0 || coneZombies != 0){
            if (flagZombies != 0){
                this.zombieSpawn.add(new FlagZombie());
                flagZombies--;
            }
            if (zombies != 0){
                this.zombieSpawn.add(new Zombie());
                zombies--;
            }
            if (coneZombies != 0){
                this.zombieSpawn.add(new ConeheadZombie());
                coneZombies--;
            }
        }
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
        if (spawnProbability > 80 || spawnProbability < 10) {
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

    /**
     * Returns a zombie to add to the game
     * @return Return the new zombie to be added
     */
    public AbstractZombie getZombie(){
        AbstractZombie zombie = zombieSpawn.get(0);
        zombieSpawn.remove(0);
        return zombie;
    }

    /**
     * Generates the zombies to spawn in this wave
     * based of random probability
     */
    public void generateZombies(){
        Random rand = new Random();
        for(int i = 0; i < numZombieSpawn; i++) {
            int randNum = rand.nextInt(101);
            if (randNum <= 60) {
                zombieSpawn.add(new Zombie());
            } else if (randNum <= 80) {
                zombieSpawn.add(new FlagZombie());
            } else {
                zombieSpawn.add(new ConeheadZombie());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wave wave = (Wave) o;
        return numZombieSpawn == wave.numZombieSpawn &&
                numZombieAlive == wave.numZombieAlive &&
                complete == wave.complete &&
                Arrays.deepEquals(zombieSpawn.toArray(), wave.zombieSpawn.toArray());
    }

    /**
     * Convert wave class to xml format
     * @return String version of xml
     */
    public String toXML(){
        String toXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";
        toXml += "<Wave> \n";
        for(AbstractZombie zombie : zombieSpawn){
            if(zombie instanceof Zombie){
                zombie = (Zombie)zombie;
                toXml += zombie.toXML() + "\n";
            }else if(zombie instanceof FlagZombie){
                zombie = (FlagZombie)zombie;
                toXml += zombie.toXML() + "\n";
            }else{
                zombie = (ConeheadZombie)zombie;
                toXml += zombie.toXML() + "\n";
            }
        }
        toXml += "</Wave>";
        return toXml;
    }

    /**
     * Export the wave to XML to be used for loading a level
     * @return The xml file
     */
    public File exportToXml(String fileName, String dir){
        try {
            File file = new File(dir + "\\" +  fileName + ".xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(this.toXML());
            out.close();
            return file;
        } catch (IOException e) {
            System.out.println("Error exporting!");
            return null;
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

    public ArrayList<AbstractZombie> getZombieSpawn() {return zombieSpawn;}

    public void setZombieSpawn(ArrayList<AbstractZombie> zombieSpawn) {this.zombieSpawn = zombieSpawn; }

    /**
     * @return true if wave is complete
     */
    public boolean isComplete() {
        return complete;
    }
}
