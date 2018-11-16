package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Plants vs Zombies
 * Sprite Class used to model all entities in the game
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class Sprite implements Comparable {

    private int health;
    private int damage;
    private int counter;
    private ImageIcon image;

    private int x;
    private int y;

    public Sprite() {
        image = null;
        health = 0;
        damage = 0;
        counter = 0;
    }

    public Sprite(String imgPath, int health, int damage, int counter) {
        setImage(imgPath);
        this.health = health;
        this.damage = damage;
        this.counter = counter;
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public ImageIcon getImage() {
        return image;
    }

    void setImage(String imagePath) {
        try {
            this.image = (new ImageIcon(ImageIO.read(new File(imagePath))
                    .getScaledInstance(60, 75, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Decrements the counter of the sprite. If the sprite is a Sun then keep the counter such as.
     */
    public void decrementCounter() {
        this.setCounter(this.getCounter() - 1);
    }

}