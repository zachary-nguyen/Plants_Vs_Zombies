import Model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TestAllSprites extends TestCase {

    private Bullet bullet;
    private Zombie zombie;
    private Sunflower sunflower;
    private Peashooter peashooter;
    private Walnut walnut;
    private Repeater repeater;
    private FlagZombie flagZombie;
    private ConeheadZombie coneheadZombie;

    /**
     * Set up test data
     */
    @Before
    public void setUp(){
       this.bullet = new Bullet(100,2);
       this.zombie = new Zombie();
       this.sunflower = new Sunflower();
       this.peashooter = new Peashooter();
       this.walnut = new Walnut();
       this.repeater = new Repeater();
       this.flagZombie = new FlagZombie();
       this.coneheadZombie = new ConeheadZombie();
    }

    /**
     * Test if bullets is created properly
     */
    @Test
    public void testBulletCreation(){
        assertNotNull(this.bullet);
        assertEquals(100,this.bullet.getDamage());
        assertEquals(2,this.bullet.getSpeed());
    }

    /**
     * Verify sunflower is properly created.
     */
    @Test
    public void testSunflowerCreation(){
        assertNotNull(this.sunflower);
        assertEquals(0,this.sunflower.getDamage());
        assertEquals(100,this.sunflower.getHealth());
        assertEquals(4,this.sunflower.getCounter());
        assertEquals(50,this.sunflower.getCost());

    }

    /**
     * Test zombie is created properly
     */
    @Test
    public void testZombieCreation(){
        assertNotNull(this.zombie);
        assertEquals(0,this.zombie.getCounter());
        assertEquals(50,this.zombie.getDamage());
        assertEquals(1,this.zombie.getSpeed());
        assertEquals(100,this.zombie.getHealth());
    }

    /**
     * Test peashooter is created properly
     */
    @Test
    public void testPeashooterCreation(){
        assertNotNull(this.peashooter);
        assertEquals(35,this.peashooter.getDamage());
        assertEquals(100,this.peashooter.getHealth());
        assertEquals(2,this.peashooter.getCounter());
        assertEquals(100,this.peashooter.getCost());
    }

    /**
     * Test ConeHeadZombie is created properly
     */
    @Test
    public void testConeHeadZombieCreation(){
        assertNotNull(this.coneheadZombie);
        assertEquals(0,this.coneheadZombie.getCounter());
        assertEquals(75,this.coneheadZombie.getDamage());
        assertEquals(1,this.coneheadZombie.getSpeed());
        assertEquals(200,this.coneheadZombie.getHealth());
    }

    /**
     * Test Walnut is created properly
     */
    @Test
    public void testWallnutCreation(){
        assertNotNull(this.walnut);
        assertEquals(0,this.walnut.getDamage());
        assertEquals(300,this.walnut.getHealth());
        assertEquals(0,this.walnut.getCounter());
        assertEquals(50,this.walnut.getCost());
    }

    /**
     * Test repeater is created properly
     */
    @Test
    public void testRepeaterCreation(){
        assertNotNull(this.repeater);
        assertEquals(20,this.repeater.getDamage());
        assertEquals(100,this.repeater.getHealth());
        assertEquals(3,this.repeater.getCounter());
        assertEquals(200,this.repeater.getCost());
    }

    /**
     * Test FlagZombie is created properly
     */
    @Test
    public void testFlagZombieCreation(){
        assertNotNull(this.flagZombie);
        assertEquals(0,this.flagZombie.getCounter());
        assertEquals(25,this.flagZombie.getDamage());
        assertEquals(2,this.flagZombie.getSpeed());
        assertEquals(50,this.flagZombie.getHealth());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestAllSprites.class);
    }
}
