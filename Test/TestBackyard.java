import Model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.PriorityQueue;

public class TestBackyard extends TestCase {

    private Backyard backyard;
    private Sunflower sunflower;
    private Zombie zombie;
    private Peashooter peashooter;
    private PriorityQueue<Sprite>[][] map;

    @Before
    public void setUp() throws IOException {
        this.backyard = new Backyard();
        this.sunflower = new Sunflower();
        this.zombie = new Zombie();
        this.peashooter = new Peashooter();
        this.map = this.backyard.getMap();
    }

    /**
     * Test the addPlant functionality
     */
    @Test
    public void testAddPlant(){
        this.backyard.addSprite(0,1,this.zombie);
        assertTrue(this.backyard.addSprite(0,0,this.sunflower)); //check if I can add to an empty slot
        assertEquals(map[0][0].peek(),this.sunflower); //assert that the plant was successfully added to map
        assertFalse(this.backyard.addSprite(0,0,this.peashooter));//Make sure can't add where there's an existing sprite
        assertFalse(this.backyard.addSprite(100,200,this.peashooter)); //try adding out of bounds
        assertFalse(this.backyard.addSprite(0,0,null));//try adding a null sprite
        assertFalse(this.backyard.addSprite(0,1,this.sunflower)); //try adding on a zombie
    }


    @Test
    public void testRemovePlant() {
        this.backyard.addSprite(0, 0, this.zombie);
        this.backyard.addSprite(0, 1, this.sunflower);
        assertTrue(this.backyard.removePlant(0, 1)); // remove plant
        assertFalse(this.backyard.removePlant(0, 0)); // try removing zombie
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestBackyard.class);
    }

}
