package View;

import Model.Sprite;

import java.util.Arrays;

public class Backyard {

    private String[][] map;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 18;

    public Backyard() {
        map = new String[HEIGHT][WIDTH];
        for (String[] row: map)
            Arrays.fill(row, "-");
    }

    /**
     * Adds a new sprite to the map
     *
     * @param x      x coordinate for the plant
     * @param y      y coordinate for the plant
     * @param sprite Which type of plant is being added
     */
    public void addSprite(int x, int y, Sprite sprite) {
       map[x][y] = sprite.getName();
    }

    /**
     * Removes a sprite from the map
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removeSprite(int x, int y) {
        map[x][y] = null;
    }

    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for(int col = 0; col <WIDTH;col++){
                System.out.print(map[row][col]+" ");
            }
            System.out.println();
        }
    }
}