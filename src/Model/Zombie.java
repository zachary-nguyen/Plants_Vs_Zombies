package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Zombie class is a model class for a basic zombie. It has no special attributes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Zombie extends AbstractZombie {

    public Zombie() throws IOException {
        super(ImageIO.read(new File("src/images/ZOMBIE.png")), 100, 25, 1, 0);
    }

}
