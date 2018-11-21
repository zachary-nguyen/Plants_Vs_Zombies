import Model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;

public class TestBackyard extends TestCase {

    private Backyard backyard;
    private Sunflower sunflower;
    private Sunflower sunflower2;
    private Zombie zombie;
    private Peashooter peashooter;
    private PriorityQueue<Sprite>[][] map;

    @Before
    public void setUp(){
        this.backyard = new Backyard();
        this.sunflower = new Sunflower();
        this.zombie = new Zombie();
        this.peashooter = new Peashooter();
        this.map = this.backyard.getMap();
        this.sunflower2 = new Sunflower();
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

    @Test
    public void testCollectSun(){
        //Add suns
        this.backyard.addSprite(0,0,this.sunflower);
        this.backyard.addSprite(0,1,this.sunflower2);
        this.sunflower.generateSun();
        this.sunflower2.generateSun();
        //try collecting before sun is ready

        this.backyard.collectSun(0,0);
        this.backyard.collectSun(1,0);
        assertEquals(300,this.backyard.getMoney());
        //decrement the counters for them to be ready to collect
        this.sunflower.decrementCounter();
        this.sunflower.decrementCounter();
        this.sunflower.decrementCounter();
        this.sunflower.decrementCounter();
        this.sunflower2.decrementCounter();
        this.sunflower2.decrementCounter();
        this.sunflower2.decrementCounter();
        this.sunflower2.decrementCounter();

        this.sunflower.generateSun();
        this.sunflower2.generateSun();
        this.backyard.collectSun(0,0);
        this.backyard.collectSun(1,0);
        assertEquals(350,this.backyard.getMoney()); //check if suns are successfully collected if balance goes up.
    }

    @Test
    public void testUpdateBackyardZombieMoving(){
        this.backyard.setCurrentWave(1);
        //test if zombie is moving properly
        this.backyard.addSprite(5,0, this.zombie);
        this.backyard.updateBackyard();
        assertEquals(this.zombie,this.backyard.getMap()[0][4].peek()); //verify zombie moved one index
    }

    @Test
    public void testUpdateBackyardZombieKillPlant(){
        this.backyard.setCurrentWave(1);
        this.backyard.addSprite(1,0,this.zombie);
        this.backyard.addSprite(0,0,this.sunflower);
        this.backyard.updateBackyard();
        //check plants health
        assertEquals(50,this.sunflower.getHealth());
        this.backyard.updateBackyard();
        //check if plant dies
        assertEquals(0,this.sunflower.getHealth());
        this.backyard.updateBackyard();
        this.backyard.updateBackyard();
        //see if zombie keeps walking
        assertEquals(this.zombie,this.backyard.getMap()[0][0].peek());
    }

    @Test
    public void testUpdateBackyardPlantKillZombie(){
        this.backyard.setCurrentWave(1);

        this.backyard.addSprite(0,0,this.peashooter);
        this.backyard.addSprite(7,0,this.zombie);
        for(int i =0; i<4;i++){
            this.backyard.updateBackyard();
        }
        assertEquals(65,this.zombie.getHealth());
        this.backyard.updateBackyard();
        assertEquals(30,this.zombie.getHealth());
        this.backyard.updateBackyard();
        assertEquals(-5,this.zombie.getHealth());
        this.backyard.updateBackyard();
        assertNull(this.map[0][1].peek());//verify zombie is removed
    }


    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestBackyard.class);
    }

}
