package View;

import Model.AbstractZombie;
import Model.Sprite;
import Model.Zombie;

import java.util.Arrays;

public class Backyard {

    private static final int HEIGHT = 5;
    private static final int WIDTH = 18;

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
     * Method that updates all the objects in the backyard and makes them perform actions
     */
    public void updateBackyard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if(map[row][col] instanceof AbstractZombie){
                    AbstractZombie zombie = (AbstractZombie)map[row][col];
                    //Move Zombie according to speed
                    map[row][col-zombie.getSpeed()] = zombie;
                    map[row][col] = null; //Reset the tile zombie was previously on
//                }else if(){
//                    //TODO: Add sunflower treatment
//                }else if(){
//                    //TODO: Add plant treatment
                }else{
                    //do nothing
                }
            }
        }

    }

    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if(map[row][col] == null){
                    System.out.print("- ");
                }else {
                    System.out.print(map[row][col].toString() + " ");
                }
            }
            System.out.println();
        }
    }
}