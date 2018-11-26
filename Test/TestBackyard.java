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
    private Walnut walnut;
    private ConeheadZombie coneheadZombie;
    private FlagZombie flagZombie;
    private Repeater repeater;

    /**
     * Set up test data
     */
    @Before
    public void setUp(){
        this.backyard = new Backyard();
        this.sunflower = new Sunflower();
        this.zombie = new Zombie();
        this.peashooter = new Peashooter();
        this.map = this.backyard.getMap();
        this.sunflower2 = new Sunflower();
        this.walnut = new Walnut();
        this.coneheadZombie = new ConeheadZombie();
        this.flagZombie = new FlagZombie();
        this.repeater = new Repeater();

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


    /**
     * Test if we can remove a plant
     */
    @Test
    public void testRemovePlant() {
        this.backyard.addSprite(0, 0, this.zombie);
        this.backyard.addSprite(0, 1, this.sunflower);
        assertTrue(this.backyard.removePlant(0, 1)); // remove plant
        assertFalse(this.backyard.removePlant(0, 0)); // try removing zombie
    }

    /**
     * Test if we can collect suns
     */
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

    /**
     * Test if zombies are properly moving
     */
    @Test
    public void testUpdateBackyardZombieMoving(){
        this.backyard.setCurrentWaveAmountOfZombies(1);
        //test if zombie is moving properly
        this.backyard.addSprite(5,0, this.zombie);
        this.backyard.updateBackyard();
        assertEquals(this.zombie,this.backyard.getMap()[0][4].peek()); //verify zombie moved one index
    }

    /**
     * Test if zombies can successfully kill a plant.
     */
    @Test
    public void testUpdateBackyardZombieKillPlant(){
        this.backyard.setCurrentWaveAmountOfZombies(3);
        //add all plants and zombies
        this.backyard.addSprite(1,0,this.zombie);
        this.backyard.addSprite(0,0,this.sunflower);
        this.backyard.addSprite(0,2,this.walnut);
        this.backyard.addSprite(1,2,this.coneheadZombie);
        this.backyard.addSprite(1,3,this.flagZombie);
        this.backyard.addSprite(0,3,this.sunflower2);

        this.backyard.updateBackyard();
        //check plants health
        assertEquals(50,this.sunflower.getHealth());
        assertEquals(225,this.walnut.getHealth());
        assertEquals(75,this.sunflower2.getHealth());

        this.backyard.updateBackyard();
        //check if sunflower dies and verify health for others
        assertEquals(0,this.sunflower.getHealth());
        assertEquals(50,this.sunflower2.getHealth());
        assertEquals(150,this.walnut.getHealth());

        this.backyard.updateBackyard();
        this.backyard.updateBackyard();
        assertEquals(0,this.sunflower2.getHealth());
        assertEquals(0,this.walnut.getHealth());

        //verify plants are removed from map after dying
        assertNotSame(this.sunflower,map[0][0].peek());
        assertNull(map[0][2].peek());
        assertNull(map[0][3].peek());

    }

    /**
     * Test if plant kills zombies
     */
    @Test
    public void testUpdateBackyardPlantKillZombie(){
        this.backyard.setCurrentWaveAmountOfZombies(2);

        this.backyard.addSprite(0,0,this.peashooter);
        this.backyard.addSprite(0,1,this.repeater);

        this.backyard.addSprite(7,1,this.flagZombie);
        this.backyard.addSprite(7,0,this.zombie);
        for(int i =0; i<4;i++){
            this.backyard.updateBackyard();
        }
        //check health
        assertEquals(65,this.zombie.getHealth());
        assertEquals(25,this.flagZombie.getHealth());

        this.backyard.updateBackyard();
        //check health
        assertEquals(30,this.zombie.getHealth());
        assertEquals(0,this.flagZombie.getHealth());

        this.backyard.updateBackyard();
        //check health
        assertEquals(-5,this.zombie.getHealth());
        assertEquals(0,this.flagZombie.getHealth());

        this.backyard.updateBackyard();

        //verify zombie is removed
        assertNull(this.map[0][3].peek());
        assertNull(this.map[0][1].peek());
    }


    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestBackyard.class);
    }

}
