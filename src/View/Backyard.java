package View;

import Model.Sprite;
import Model.Zombie;

import java.util.Arrays;
import java.util.Random;

public class Backyard {

    private static final int HEIGHT = 5;
    private static final int WIDTH = 18;

    private String[][] map;

    private int score;

    private int money;

    public Backyard() {
        map = new String[HEIGHT][WIDTH];
        for (String[] row : map)
            Arrays.fill(row, "-");
    }

    public int randomGenerator() {
        Random rand = new Random();
        int n = rand.nextInt(HEIGHT - 1) + 1;
        return n;
    }

    public void spawnZombie() {
        Zombie z = new Zombie();
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
            map[y][x] = sprite.getName();
        }
    }

    /**
     * Removes a sprite from the map
     *
     * @param x x coordinate of plant to remove
     * @param y y coordinate of plant to remove
     */
    public void removeSprite(int x, int y) {
        map[y][x] = "-";
    }

    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.print(map[row][col] + " ");
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