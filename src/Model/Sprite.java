package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Plants vs Zombies
 * Sprite Class used to model all entities in the game
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class Sprite {

    private int health;
    private int damage;
    private int counter;
    private Image img;

    public Sprite() throws IOException {
        img = null;
        health = 0;
        damage = 0;
        counter = 0;
    }

    public Sprite(Image img, int health, int damage, int counter) {
        this.img = img;
        this.health = health;
        this.damage = damage;
        this.counter = counter;
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public Image getIcon() { return img; }

    public void setIcon(Image img) { this.img = img; }

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

    /**
     * Decrements the counter of the sprite. If the sprite is a Sun then keep the counter such as.
     *
     */
    public void decrementCounter(){
        this.setCounter(this.getCounter() - 1);
    }

}