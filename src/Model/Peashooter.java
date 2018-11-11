package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Peashooter extends AbstractPlant {

    public Peashooter() throws IOException {
        super(ImageIO.read(new File("src/images/PEASHOOTER.png")), 100, 25, 2,100);
    }

    public boolean canShoot() {
        if (this.getCounter() == 0) {
            this.setCounter(2);
            return true;
        }
        return false;
    }

    public Bullet shootBullet() throws IOException {
        return new Bullet(getDamage(), 1);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
